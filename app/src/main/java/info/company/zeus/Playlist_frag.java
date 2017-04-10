package info.company.zeus;

/**
 * Created by prashanth on 25-03-2017.
 */

import android.content.Context;
import android.icu.text.MessagePattern;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import info.company.zeus.Models.Track;


/**
 * Created by lenovo on 25-03-2017.
 */


public class Playlist_frag extends Fragment implements PL_listener {
    // Store instance variables
    private String title;
    private int page;
    RecyclerView playlist;
    Playlist_Adapter playlist_adapter;
    public ArrayList<Track> tracks;
    String owner;
    MainActivity mainActivity;
    LinearLayoutManager mLayoutManager;

    public Playlist_frag(){

    }
    public Playlist_frag(Context ctxt) {

        this.mainActivity=(MainActivity)ctxt;
    }

    // newInstance constructor for creating fragment with arguments
    public static Playlist_frag newInstance(int page, String title, String owner, Context ctxt) {
        Playlist_frag fragmentFirst = new Playlist_frag(ctxt);
        Bundle args = new Bundle();
        args.putInt("Page", page);
        fragmentFirst.owner=owner;
        args.putString("Title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        if (tracks==null)
            tracks=new ArrayList<>();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        View view = inflater.inflate(R.layout.party_playlist_list_fragment, container, false);
        playlist=(RecyclerView)view.findViewById(R.id.party_playlist);
        try {
            tracks=mainActivity.current_PlayList;
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            mainActivity.RegisterPlayListListeners(this.getClass(),this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (tracks==null) {
            tracks=new ArrayList<>();
        }
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        playlist.setLayoutManager(mLayoutManager);
        playlist_adapter=new Playlist_Adapter(tracks, (MainActivity) getContext());
        playlist.setAdapter(playlist_adapter);
        //mainActivity.getPlaylist(this);
        return view;
    }

    @Override
    public void OnPlaylistChanged() {
        ArrayList<Track> test=mainActivity.current_PlayList;
        Log.d("Playlist_frag","The playlist has been updated");
        if ((FirebaseAuth.getInstance().getCurrentUser().getEmail()).
                equals(mainActivity.Playlist_owner)) {
            Collections.sort(mainActivity.current_PlayList, new Comparator<Track>() {
                @Override
                public int compare(Track o1, Track o2) {
                    if (o1.downvotes==null)
                        o1.init_downvote();
                    if (o1.upvotes==null)
                        o1.init_upvote();
                    if (o2.downvotes==null)
                        o2.init_downvote();
                    if (o2.upvotes==null)
                        o2.init_upvote();
                    int obj1=o1.upvotes.size()-o1.downvotes.size();
                    int obj2=o2.upvotes.size()-o2.downvotes.size();
                    if(obj1<obj2)
                        return 1;
                    return 0;
                }
            });
            Log.d("Playlist_frag", "This is the Host");
        }
        for (int i=0;i<mainActivity.current_PlayList.size();i++){
            if (!(test.get(i).getStreamURL()
                    .equals(mainActivity.current_PlayList.get(i).getStreamURL()))){
                mainActivity.writeplaylistchanges();
            }
        }

        //    mainActivity.writeplaylistchanges();
        this.tracks.clear();
        this.tracks.addAll(mainActivity.current_PlayList);
        playlist_adapter.notifyDataSetChanged();
    }
}
