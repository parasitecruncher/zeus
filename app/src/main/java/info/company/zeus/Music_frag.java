package info.company.zeus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ypyproductions.utils.DBLog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import info.company.zeus.Models.Host;
import info.company.zeus.Models.Scloud;
import info.company.zeus.johnlam.soundcloud.SoundCloudAPI;
import info.company.zeus.johnlam.soundcloud.object.TrackObject;

/**
 * Created by mithun on 25-03-2017.
 */


public class Music_frag extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private String TAG="Music_frag";
    // Store instance variables based on arguments passed
    RecyclerView song_list;
    Music_frag thisobject;
    MainActivity mainActivity;
    public SCAdapter scAdapter;
    ArrayList<Host> hostslist;
    LinearLayoutManager mLayoutManager;
    String keyword="";
    ArrayList<Scloud> mListNewTrackObjects;
    private SearchView searchtext;
    final String SOUNDCLOUND_CLIENT_ID = "P6Ns4c5xsgsVureyV3rkYMOjS01m7UGC";
    final String SOUNDCLOUND_CLIENT_SECRET = "q0pp4IC71AvDJYUg54uAO7WxU1dOgOAZ";
    MediaPlayer mediaPlayer;
    OwnerUtils ownerUtils;

    public void responseReceived(ArrayList<Scloud> Scloudobj){
        mListNewTrackObjects.clear();
        mListNewTrackObjects.addAll(Scloudobj);
        scAdapter.notifyDataSetChanged();
        song_list.setAdapter(scAdapter);
        scAdapter.notifyDataSetChanged();
        Log.d(TAG, Scloudobj.size()+"");
    }

    // newInstance constructor for creating fragment with arguments
    public static Music_frag newInstance(int page, String title) {
        Music_frag music_frag = new Music_frag();
        Bundle args = new Bundle();
        music_frag.setArguments(args);
        args.putInt("Page", page);
        args.putString("Title", title);
        return music_frag;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        thisobject=this;
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ///////////////////////////////////////////////***************SOUND CLOUD************************///////////////////////////////////////////////////////////////////////////////////////

        final SoundCloudAPI mSoundCloud = new SoundCloudAPI(SOUNDCLOUND_CLIENT_ID, SOUNDCLOUND_CLIENT_SECRET,getActivity());
        mListNewTrackObjects =new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getActivity());
        scAdapter = new SCAdapter(mListNewTrackObjects,getContext());

        //mSoundCloud.getListTrackObjectsByQuery(keyword, 0, 100,Music_frag.class,this);
        if (mListNewTrackObjects != null && mListNewTrackObjects.size() > 0) {
            DBLog.d("Response","Not null");
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        View view = inflater.inflate(R.layout.music_list_fragment, container, false);
        song_list = (RecyclerView) view.findViewById(R.id.song_listview);
        searchtext = (SearchView) view.findViewById(R.id.SongSearch);
        searchtext.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSoundCloud.getListTrackObjectsByQuery(query, 0, 10,Music_frag.class,thisobject);
                Toast.makeText(getActivity(),query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        hostslist =new ArrayList<>();
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        song_list.setLayoutManager(mLayoutManager);

        song_list.setAdapter(scAdapter);
        //ListView musicList = (ListView)view.findViewById(R.id.music_list);
        ///////////////////////////////////////////////////////////////////////////////////Insert here///////////////////////////////////////////////////////////////////
        return view;
    }
    public void setadapter(ArrayList<Host> songlist){
        scAdapter.notifyDataSetChanged();
        song_list.setAdapter(scAdapter);

    }

}
