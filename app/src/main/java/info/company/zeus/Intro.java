package info.company.zeus;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import info.company.zeus.Models.Host;

/**
 * Created by prashanth on 3/25/17.
 */

public class Intro extends Fragment {
    Button hostButton;
    RecyclerView party_list;
    MainActivity mainActivity;
    public PartyAdapter partyAdapter;
    ArrayList<Host> hostslist;
    LinearLayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro_frag, container, false);
        hostButton = (Button)view.findViewById(R.id.host);
        party_list = (RecyclerView) view.findViewById(R.id.party_listview);
        hostslist =new ArrayList<>();


        mLayoutManager = new LinearLayoutManager(getActivity());
        partyAdapter = new PartyAdapter(hostslist, (MainActivity) getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        party_list.setLayoutManager(mLayoutManager);
        party_list.setAdapter(partyAdapter);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.Playlist_owner=mainActivity.auth_user;
                try {
                    mainActivity.addHost();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }
    public void setadapter(ArrayList<Host> partylist){
        partyAdapter.notifyDataSetChanged();
        party_list.setAdapter(partyAdapter);

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
