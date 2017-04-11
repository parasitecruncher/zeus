package info.company.zeus;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

/**
 * Created by prashanth on 3/25/17.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SampleAdapter extends FragmentPagerAdapter {
    private String owner;
    private Context ctxt=null;

    public SampleAdapter(Context ctxt, FragmentManager mgr) {
        super(mgr);
        this.ctxt=ctxt;
    }

    public SampleAdapter(Context ctxt, FragmentManager mgr, String owner) {
        super(mgr);
        this.ctxt=ctxt;
        this.owner=owner;

    }

    @Override
    public int getCount() {
        return(2);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return Playlist_frag.newInstance(0,"Playlist",owner,ctxt);

            case 1:
                return Music_frag.newInstance(1,"Music");


            default:
                return null;
        }
    }

    @Override
    public String getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Playlist";
            case 1:
                return  "Music";
            case 2:
                return "Songs".toUpperCase();
        }
        return null;
    }
}
