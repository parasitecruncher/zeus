package info.company.zeus;

import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;

import info.company.zeus.Models.Track;

/**
 * Created by prashanth on 4/11/17.
 */

public class OwnerUtils {
    MediaPlayer mediaPlayer;
    ArrayList<Track> tracks;
    String CLIENT_ID;
    Playlist_frag playlist_frag;
    public OwnerUtils(String CLIENT_ID, Playlist_frag playlist_frag){
        mediaPlayer=new MediaPlayer();
        this.tracks=new ArrayList<>();
        if (playlist_frag.mainActivity.current_PlayList==null){
            playlist_frag.mainActivity.current_PlayList=new ArrayList<>();
        }
        this.tracks.addAll(playlist_frag.mainActivity.current_PlayList);
        this.CLIENT_ID=CLIENT_ID;
        this.playlist_frag =playlist_frag;
    }
    public void init() throws IOException {
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                playlist_frag.mainActivity.current_PlayList.remove(0);
                playlist_frag.mainActivity.writeplaylistchanges();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {

                try {
                    mp.stop();
                    mp.reset();
                    //mp.release();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    playlist_frag.mainActivity.current_PlayList.remove(0);

                    mp.setDataSource(playlist_frag.mainActivity.current_PlayList.get(0)
                    .getStreamURL()+"?client_id="+CLIENT_ID);
                    mp.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                playlist_frag.mainActivity.writeplaylistchanges();
            }
        });
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(playlist_frag.mainActivity.current_PlayList.get(0)
                .getStreamURL()+"?client_id="+CLIENT_ID);
        mediaPlayer.prepareAsync();
    }
}
