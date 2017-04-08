package info.company.zeus;

/**
 * Created by prashanth on 25-03-2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.company.zeus.Models.Track;


/**
 * Created by lenovo on 25-03-2017.
 */


public class Playlist_frag extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    RecyclerView playlist;
    Playlist_Adapter playlist_adapter;
    ArrayList<Track> tracks;
    String owner;
    MainActivity mainActivity;
    LinearLayoutManager mLayoutManager;


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
        tracks=new ArrayList<>();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        View view = inflater.inflate(R.layout.party_playlist_list_fragment, container, false);
        playlist=(RecyclerView)view.findViewById(R.id.party_playlist);
        tracks.add(new Track(owner,"test","test","test"));
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        playlist.setLayoutManager(mLayoutManager);
        playlist_adapter=new Playlist_Adapter(tracks);
        playlist.setAdapter(playlist_adapter);
        //mainActivity.getPlaylist(this);
        return view;
    }
    public void Playlist_Recieved(ArrayList<Track> tracks){
        this.tracks.clear();
        this.tracks.addAll(tracks);
        playlist.setAdapter(playlist_adapter);
        playlist_adapter.notifyDataSetChanged();
    }
}
