package info.company.zeus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import info.company.zeus.Models.Scloud;

/**
 * Created by mithun on 3/25/17.
 */

class SCAdapter extends RecyclerView.Adapter<SCAdapter.MyViewHolder> {
    private ArrayList<Scloud> songlist;
    private Context context;

    SCAdapter(ArrayList<Scloud> songlist, Context context){
        this.songlist =songlist;
        this.context=context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView songname ;
        ImageView songimage;

        MyViewHolder(View view) {
            super(view);
            songname = (TextView) view.findViewById(R.id.text_row);
            songimage = (ImageView) view.findViewById(R.id.image_row);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sound_cloud_row, parent, false);

        return new MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Scloud song = songlist.get(position);
        holder.songname.setText(song.getTitle());
        getBitmapFromURL(song.artwork_url, holder.songimage);
        //holder.songimage.setImageURI(Uri.parse("https://i1.sndcdn.com/artworks-000201927280-2vsnux-large.jpg"));
    }
/*
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Host host = songlist.get(position);
        holder.partyname.setText(host.getEmail());
    }*/

    @Override
    public int getItemCount() {
        return songlist.size();
    }
    public void getBitmapFromURL(String url, final ImageView iv) {
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER,null, null);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(ir);


    }

}
