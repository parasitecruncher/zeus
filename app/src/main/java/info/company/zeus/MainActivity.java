package info.company.zeus;

import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import info.company.zeus.Models.Host;
import info.company.zeus.Models.PlaylistListener;
import info.company.zeus.Models.Track;

import static info.company.zeus.R.id.host;
import static info.company.zeus.R.id.progressBar;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Intro intro;
    Host_frag host_frag;
    public String auth_user;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference partylist_databaseReference;
    private ValueEventListener partylist_listener;
    private ArrayList<Host> partylist;
    private Party_frag party_frag;
    public ArrayList<Track> current_PlayList;
    public String Playlist_owner;



    public ArrayList<PlaylistListener> classes;
    public String mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        classes=new ArrayList<>();
        setContentView(R.layout.activity_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        auth_user=user.getEmail();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        intro=new Intro();
        host_frag=new Host_frag();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentcontainer,intro);
        partylist = new ArrayList<>();
        partylist_listener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                partylist.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    partylist.add(postSnapshot.getValue(Host.class));
                }
                try {
                    intro.hostslist.clear();
                    intro.hostslist.addAll(partylist);
                    intro.partyAdapter.notifyDataSetChanged();
                    intro.setadapter(partylist);
                }
                catch (Exception e){
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("hosts").addValueEventListener(partylist_listener);
        //init_getpartylist();
        fragmentTransaction.commit();

        firebaseDatabase = FirebaseDatabase.getInstance();
        partylist_databaseReference = FirebaseDatabase.getInstance().getReference();}

    public void addHost() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        getPlaylist();
        createHost_infirebase();
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.remove(intro);
        partylist_databaseReference.removeEventListener(partylist_listener);
        host_frag.owner=auth_user;
        fragmentTransaction.replace(R.id.fragmentcontainer,host_frag);
//        Log.d("DatabaseReference",databaseReference.getKey());
        fragmentTransaction.commit();
    }

    public void addParty(String username) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        getPlaylist();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(intro);
        partylist_databaseReference.removeEventListener(partylist_listener);
        party_frag=new Party_frag();
        party_frag.owner=username;
        read_firebase(formatter(username));
        fragmentTransaction.add(R.id.fragmentcontainer,party_frag);
        fragmentTransaction.commit();
    }
    public void getPlaylist(){
        //TODO: 3/29/17 Recieve pertaining playlist
        for(Host h : partylist){
            if(h.getEmail().equals(Playlist_owner)){
                current_PlayList=h.getPlaylist();
                //playlist_frag.Playlist_Recieved(h.getPlaylist());
                break;
            }
        }
    }

    public void writeplaylistchanges(){
        databaseReference.child("hosts").child(formatter(Playlist_owner))
                .child("playlist").setValue(current_PlayList);
    }

    public void createHost_infirebase() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Log.d("MainActivity",databaseReference.toString());
        String userId = formatter(auth.getCurrentUser().getEmail());
        String name=auth.getCurrentUser().getDisplayName();
        Host host=new Host(name,auth.getCurrentUser().getEmail());
        Boolean exists=false;
        assert userId != null;
        for(Host h : partylist){
            if(h.getEmail().equals(Playlist_owner)){
                current_PlayList=h.getPlaylist();
                exists=true;
            }
        }
        if (!exists){
            databaseReference.child("hosts").child(userId).setValue(host);
        }
        read_firebase(userId);
    }
   static String formatter(String a){
        String formatted="";
        for (char ch: a.toCharArray()) {
            if(ch=='@')
                return formatted;
            if(ch=='.'||ch=='['||ch==']'||ch=='$'||ch=='#')
                continue;
            formatted=formatted+ch;
        }
        formatted.toUpperCase();
        return formatted;
    }
    // For reading change in owners playlist
    public void read_firebase(String userID)throws NoSuchMethodException,InvocationTargetException, IllegalAccessException {
        databaseReference.child("hosts").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Host host = dataSnapshot.getValue(Host.class);
                current_PlayList=host.getPlaylist();
                Iterator itr=classes.iterator();
                for(PlaylistListener playlistListener: classes){
                    Class cls = playlistListener.getCls();
                    Object o= playlistListener.getO();
                    Method method= null;
                    try {
                        method = (cls.cast(o)).getClass().getMethod("OnPlaylistChanged",null);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    try {
                        method.invoke(cls.cast(o),null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                // Log.d("MainActivity", " email " + host.getEmail());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });
    }

    public void RegisterPlayListListeners(Class<?> cls,Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Method method=(cls.cast(o)).getClass().getMethod("OnPlaylistChanged",null);
//        method.invoke(cls.cast(o),null);
        classes.add(new PlaylistListener(cls,o));
    }
    public void DeregisterPlayListListeners(Class<?> cls,Object o){
        classes.remove(new PlaylistListener(cls,o));
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);

    }

    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vr_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i4 = new Intent(Intent.ACTION_MAIN);
        PackageManager manager = getPackageManager();
        switch (item.getItemId()) {
            case R.id.raindance:
                i4 = manager.getLaunchIntentForPackage("com.Zeus.Raindance");//apk name
                i4.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(i4);
                return true;
            case R.id.basic:
                i4 = manager.getLaunchIntentForPackage("com.stylingandroid.vizualiser");//apk name
                i4.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(i4);
                return true;
            default:
                return true;
        }

    }
}