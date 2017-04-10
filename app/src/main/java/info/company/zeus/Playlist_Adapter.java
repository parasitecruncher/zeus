package info.company.zeus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import info.company.zeus.Models.Host;
import info.company.zeus.Models.Track;

/**
 * Created by prashanth on 3/26/17.
 */

class Playlist_Adapter extends RecyclerView.Adapter<Playlist_Adapter.MyViewHolder> {
    private ArrayList<Track> tracks;
    private FirebaseAuth auth;
    public MainActivity mainActivity;


    public Playlist_Adapter(ArrayList<Track> hosts, MainActivity mainActivity){
        this.tracks=hosts;
        this.mainActivity=mainActivity;
    }

    public Playlist_Adapter(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView trackname;
        ImageView artwork;
        Button upvote;
        Button downvote;
        RelativeLayout relativeLayout;
        MyViewHolder(View view) {
            super(view);
            trackname = (TextView) view.findViewById(R.id.text_row);
            artwork = (ImageView) view.findViewById(R.id.image_row);
            upvote = (Button) view.findViewById(R.id.upvote);
            downvote = (Button) view.findViewById(R.id.downvote);
            relativeLayout=(RelativeLayout)view.findViewById(R.id.playlist_row_click_container);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Track track = tracks.get(position);
        holder.trackname.setText(track.Name);
        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (track.upvotes==null)
                    track.init_upvote();
                if (track.upvotes==null)
                    track.init_upvote();
                track.addUpvote(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                mainActivity.current_PlayList.remove(position);
                mainActivity.current_PlayList.add(position,track);
                mainActivity.writeplaylistchanges();
            }
        });
        holder.downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (track.downvotes==null)
                    track.init_downvote();
                track.addDownvote(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                mainActivity.current_PlayList.remove(position);
                mainActivity.current_PlayList.add(position,track);
                mainActivity.writeplaylistchanges();
            }
        });
        if (track.getArtwork()!=null)
            getBitmapFromURL(track.getArtwork(), holder.artwork);

    }


    @Override
    public int getItemCount() {
        return tracks.size();
    }
    private void getBitmapFromURL(String url, final ImageView iv) {
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER,null, null);
        RequestQueue queue = Volley.newRequestQueue(mainActivity);
        queue.add(ir);


    }

}
