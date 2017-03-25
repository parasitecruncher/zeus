package info.company.zeus;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by prashanth on 3/25/17.
 */

public class Intro extends Fragment {
    Button host;
    ListView party_list;
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro_frag, container, false);
        host = (Button)view.findViewById(R.id.host);
        party_list = (ListView)view.findViewById(R.id.party_listview);
        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.addHost();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        mainActivity=(MainActivity)getActivity();
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
