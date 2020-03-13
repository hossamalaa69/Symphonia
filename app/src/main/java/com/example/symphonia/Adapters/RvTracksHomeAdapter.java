package com.example.symphonia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Entities.Track;

import java.util.ArrayList;

public class RvTracksHomeAdapter extends RecyclerView.Adapter<RvTracksHomeAdapter.Holder> {

    private ArrayList<Track> mTracks;
    private Context context;


    private OnTrackClicked onTrackClicked;

    public RvTracksHomeAdapter(Context context, ArrayList<Track> mTracks) {
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

    public interface OnTrackClicked {
        void OnTrackClickedListener(ArrayList<Track> tracks, int pos);
        //TODO add functions for listeners
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
            setListeners();

        }

        private void setListeners() {
            ivHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO handle hiding track, call func of interface
                    Toast.makeText(context, "track hidden ", Toast.LENGTH_SHORT).show();
                }
            });
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO handle adding to playlist, call func of interface
                    Toast.makeText(context, "added to liked playlist ", Toast.LENGTH_SHORT).show();
                }
            });
            ivSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO handle open settings track, call func of interface
                    Toast.makeText(context, "open settings ", Toast.LENGTH_SHORT).show();
                }
            });

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
