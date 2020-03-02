package com.example.symphonia.ui.home;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.data.Playlist;
import com.example.symphonia.data.Track;

import java.util.ArrayList;
import java.util.Random;

public class RvTrackAdapterHome extends RecyclerView.Adapter<RvTrackAdapterHome.Holder> {

    private ArrayList<Playlist> mPlaylists;
    private Context context;

    // TODO : declare interface
    public RvTrackAdapterHome(Context context, ArrayList<Playlist> list) {
        this.mPlaylists = list;
        this.mPlaylists = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.rv_item_playlist_home,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    public class Holder extends RecyclerView.ViewHolder {


        private ImageView ivPlaylistImage;
        private TextView tvPlaylistTitle;
        private TextView tvPlaylistDescription;

        public Holder(@NonNull View itemView) {
            super(itemView);

            ivPlaylistImage= itemView.findViewById(R.id.iv_playlist_image_home);
            tvPlaylistTitle = itemView.findViewById(R.id.tv_playlist_title_home);
            tvPlaylistDescription = itemView.findViewById(R.id.tv_playlist_description_home);
        }
        // TODO delete this hard coded data
            int [] images = {R.drawable.download,R.drawable.download1,R.drawable.images,R.drawable.images2,R.drawable.images3};

        public void bind(int pos) {
            // TODO add data to views
            /*ivPlaylistImage.setImageDrawable(new BitmapDrawable(context.getResources(),
                    mPlaylists.get(pos).getmPlaylistImage()));
            */
            ivPlaylistImage.setImageResource(images[new Random().nextInt(5)]);
            tvPlaylistTitle.setText(mPlaylists.get(pos).getmPlaylistTitle());
            tvPlaylistDescription.setText(mPlaylists.get(pos).getmPlaylistDescription());
        }
    }
}
