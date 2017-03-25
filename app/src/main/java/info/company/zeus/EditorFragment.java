package info.company.zeus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by prashanth on 3/25/17.
 */
public class EditorFragment extends Fragment {
    private static final String KEY_POSITION="position";

    static EditorFragment newInstance(int position) {
        EditorFragment frag=new EditorFragment();
        Bundle args=new Bundle();

        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return(frag);
    }

    static String getTitle(Context ctxt, int position) {
        return(String.format(ctxt.getString(R.string.hint), position + 1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        int position=getArguments().getInt(KEY_POSITION, -1);
        if(position==1){
            view=inflater.inflate(R.layout.music_list_fragment, container, false);

        }
        else
            view=inflater.inflate(R.layout.party_playlist_list_fragment, container, false);

        return(view);
    }
}

