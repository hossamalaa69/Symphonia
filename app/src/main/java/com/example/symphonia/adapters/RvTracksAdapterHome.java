package com.example.symphonia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.data.Track;

import java.util.ArrayList;

public class RvTracksAdapterHome extends RecyclerView.Adapter<RvTracksAdapterHome.Holder> {

    private ArrayList<Track> mTracks;
    private Context context;

    // TODO : declare interface
    public OnTrackClicked onTrackClicked;

    public interface OnTrackClicked {
        void OnTrackClickedListener(ArrayList<Track> tracks, int pos);
    }

    public RvTracksAdapterHome(Context context, ArrayList<Track> mTracks) {
        this.mTracks = mTracks;
        this.context = context;
        onTrackClicked = (OnTrackClicked) context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_track, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //TODO set listener to these images
        private ImageView ivLike;
        private ImageView ivHide;
        private ImageView ivSettings;
        private ImageView ivTrackImage;
        private TextView tvTrackTitle;
        private TextView tvTrackDescription;

        public Holder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            ivHide = itemView.findViewById(R.id.iv_hide_track_item);
            ivLike = itemView.findViewById(R.id.iv_like_track_item);
            ivSettings = itemView.findViewById(R.id.iv_track_settings_item);
            ivTrackImage = itemView.findViewById(R.id.iv_track_image_item);
            tvTrackTitle = itemView.findViewById(R.id.tv_track_title_item);
            tvTrackDescription = itemView.findViewById(R.id.tv_track_description_item);


        }

        public void bind(int pos) {
            // TODO add data to views
            /*ivPlaylistImage.setImageDrawable(new BitmapDrawable(context.getResources(),
                    mPlaylists.get(pos).getmPlaylistImage()));
            */
            ivTrackImage.setImageResource(mTracks.get(pos).getmImageResources());
            tvTrackTitle.setText(mTracks.get(pos).getmTitle());
            tvTrackDescription.setText(mTracks.get(pos).getmDescription());
        }

        @Override
        public void onClick(View view) {
            onTrackClicked.OnTrackClickedListener(mTracks,
                    getAdapterPosition());

        }
    }
}
