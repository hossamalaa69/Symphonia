package com.example.symphonia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.data.Track;

import java.util.ArrayList;

public class RvTracksAdapterPlayActivity extends RecyclerView.Adapter<RvTracksAdapterPlayActivity.Holder> {

    private Context context;
    private ArrayList<Track> tracks;

    public RvTracksAdapterPlayActivity(Context context, ArrayList<Track> tracks) {
        this.context = context;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rv_item_playlist_play_activty, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView trackImage;

        public Holder(@NonNull View itemView) {
            super(itemView);
            trackImage = itemView.findViewById(R.id.iv_track_image_item_play_activity);
        }

        private void bind() {
            trackImage.setImageResource(tracks.get(getAdapterPosition()).getmImageResources());
        }
    }
}
