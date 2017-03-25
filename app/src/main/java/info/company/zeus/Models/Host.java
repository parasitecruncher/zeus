package info.company.zeus.Models;

import java.util.ArrayList;

/**
 * Created by prashanth on 3/25/17.
 */
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties

public class Host {
    public String name;
    public String email;
    public ArrayList<Track> playlist;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Track> getPlaylist() {
        return playlist;
    }

    public void addTrack(Track track){
        if(!playlist.contains(track)){
            playlist.add(track);
        }
    }

    public void removeTrack(Track track){
        if(playlist.contains(track)){
            playlist.remove(track);
        }
    }

    // Default constructor required for calls to
    // DataSnapshot.getValue(Host.class)
    public Host() {
    }

    public Host(String name, String email) {
        if(name==null)
            name="null";
        this.name = name;
        this.email = email;
        playlist=new ArrayList<>();
    }
}
