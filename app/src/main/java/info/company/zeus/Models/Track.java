package info.company.zeus.Models;

import java.util.ArrayList;

/**
 * Created by prashanth on 3/25/17.
 */
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Track {
    public String Name;
    public String Artist;
    public String Album;
    public long seek_value;
    public  ArrayList<String> upvotes;
    public ArrayList<String> downvotes;
    public String streamURL;
    public String artwork;
    public int net_score;
    public String getArtwork() {
        return artwork;
    }
    public String getStreamURL() {
        return streamURL;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public Track() {
    }

    public void addUpvote(String useremail){
        if(!upvotes.contains(useremail)) {
            upvotes.add(useremail);
            try {
                if(downvotes.contains(useremail))
                    downvotes.remove(useremail);
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }

        }
        netScore();
    }

    public void addDownvote(String useremail){
        if(!downvotes.contains(useremail)) {
            downvotes.add(useremail);
            try {
                if(upvotes.contains(useremail))
                    upvotes.remove(useremail);
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        netScore();
    }

    public Track(String name, String artwork, String streamURL) {
        Name = name;
        seek_value = -1; //value in milliseconds
        this.artwork=artwork;
        this.upvotes = new ArrayList<>();
        this.downvotes = new ArrayList<>();
        this.streamURL = streamURL;
    }

    public void init_upvote(){
        this.upvotes=new ArrayList<>();
    }
    public void init_downvote(){
        this.downvotes=new ArrayList<>();
    }

    public int netScore(){
        if (downvotes==null)
            init_downvote();
        if (upvotes==null)
            init_upvote();
        net_score= upvotes.size()-downvotes.size();
        return net_score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (Name != null ? !Name.equals(track.Name) : track.Name != null) return false;
        if (streamURL != null ? !streamURL.equals(track.streamURL) : track.streamURL != null)
            return false;
        return artwork != null ? artwork.equals(track.artwork) : track.artwork == null;

    }

    @Override
    public int hashCode() {
        int result = Name != null ? Name.hashCode() : 0;
        result = 31 * result + (streamURL != null ? streamURL.hashCode() : 0);
        result = 31 * result + (artwork != null ? artwork.hashCode() : 0);
        return result;
    }
}
