package info.company.zeus;

import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import info.company.zeus.Models.Host;
import info.company.zeus.Models.Track;

import static info.company.zeus.R.id.host;
import static info.company.zeus.R.id.progressBar;

public class MainActivity extends AppCompatActivity{
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Intro intro;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(getString(R.string.app_name));
//        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentcontainer,intro);
        fragmentTransaction.commit();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("hosts");

        read_firebase(formatter(auth.getCurrentUser().getEmail()));
    }

    public void addHost(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(intro);
        fragmentTransaction.add(R.id.fragmentcontainer,new Host_frag());
        createHost_infirebase();
        fragmentTransaction.commit();
    }

    public void createHost_infirebase(){

        Log.d("MainActivity",databaseReference.toString());
        String userId = formatter(auth.getCurrentUser().getEmail());
        String name=auth.getCurrentUser().getDisplayName();
        Host host=new Host(name,auth.getCurrentUser().getEmail());
        Track track=new Track("Miracles","Coldplay","Single","http://dl.far30music.com/Music/English/Coldplay%20-%20Miracles%20[128].mp3");
        Track track1=new Track("Outside","Calvin Harris","Single","http://dl.far30music.com/Music/English/Calvin%20Harris%20Ft.%20Ellie%20Goulding%20-%20Outside.mp3");
        track.addUpvote("prashanthrockit");
        host.addTrack(track);
        host.addTrack(track1);
        assert userId != null;
        databaseReference.child(userId).setValue(host);
        read_firebase(userId);
    }
    String formatter(String a){
        String formatted="";
        for (char ch: a.toCharArray()) {
            if(ch=='@')
                return formatted;
            formatted=formatted+ch;
        }
        return formatted;
    }

    public void read_firebase(String userID){
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Host host = dataSnapshot.getValue(Host.class);

                Log.d("MainActivity", " email " + host.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });
    }

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
}