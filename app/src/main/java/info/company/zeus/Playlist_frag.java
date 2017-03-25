package info.company.zeus;

/**
 * Created by prashanth on 25-03-2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by lenovo on 25-03-2017.
 */


public class Playlist_frag extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static Playlist_frag newInstance(int page, String title) {
        Playlist_frag fragmentFirst = new Playlist_frag();
        Bundle args = new Bundle();
        args.putInt("Page", page);
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
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.party_playlist_list_fragment, container, false);

        return view;
    }
}
