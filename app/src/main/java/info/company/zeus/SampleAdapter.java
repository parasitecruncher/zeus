package info.company.zeus;

import android.app.Activity;
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
    Context ctxt=null;

    public SampleAdapter(Context ctxt, FragmentManager mgr) {
        super(mgr);
        this.ctxt=ctxt;
    }

    @Override
    public int getCount() {
        return(2);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return Playlist_frag.newInstance(0,"Playlist");

            case 1:
                return Music_frag.newInstance(1,"Music");


            default:
                return null;
        }
    }

    @Override
    public String getPageTitle(int position) {
        return "Page"+ position;
    }
}
