package info.company.zeus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by prashanth on 25-03-2017.
 */


public class Music_frag extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static Music_frag newInstance(int page, String title) {
        Music_frag music_frag = new Music_frag();
        Bundle args = new Bundle();
        music_frag.setArguments(args);
        args.putInt("Page", page);
        args.putString("Title", title);
        return music_frag;
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
        View view = inflater.inflate(R.layout.music_list_fragment, container, false);

        return view;
    }
}
