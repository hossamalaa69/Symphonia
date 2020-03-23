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

import com.example.symphonia.Entities.Track;
import com.example.symphonia.R;

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
        void OnTrackClickedListener(ArrayList<Track> tracks, int pos, int prev);

        void OnLikeClickedListener(boolean selected, int pos);
        //TODO add functions for listeners
    }

    private int prev = -1;
    private View prevView = null;

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
                    if (ivHide.isSelected()) {
                        ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
                        Toast.makeText(context, R.string.return_to_playing_playlist, Toast.LENGTH_SHORT).show();
                        mTracks.get(getAdapterPosition()).setHidden(false);
                        tvTrackTitle.setTextColor(context.getResources().getColor(R.color.white));
                        tvTrackDescription.setTextColor(context.getResources().getColor(R.color.white));
                        ivHide.setSelected(false);
                    } else {
                        mTracks.get(getAdapterPosition()).setHidden(true);
                        tvTrackTitle.setTextColor(context.getResources().getColor(R.color.light_gray));
                        tvTrackDescription.setTextColor(context.getResources().getColor(R.color.light_gray));
                        ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_red_24dp);
                        Toast.makeText(context, R.string.remove_from_playing_list, Toast.LENGTH_SHORT).show();
                        ivHide.setSelected(true);
                    }
                }
            });
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onTrackClicked.OnLikeClickedListener(!ivLike.isSelected(), getAdapterPosition());
                    if (ivLike.isSelected()) {
                        ivLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        Toast.makeText(context, R.string.remove_from_liked_playlist, Toast.LENGTH_SHORT).show();
                        mTracks.get(getAdapterPosition()).setLiked(false);
                        ivHide.setSelected(false);
                    } else {
                        mTracks.get(getAdapterPosition()).setLiked(true);
                        ivLike.setImageResource(R.drawable.ic_favorite_black_24dp);
                        Toast.makeText(context, R.string.add_to_like_playlist, Toast.LENGTH_SHORT).show();
                        ivLike.setSelected(true);


                    }
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
            ivTrackImage.setImageResource(mTracks.get(pos).getmImageResources());
            tvTrackTitle.setText(mTracks.get(pos).getmTitle());
            tvTrackDescription.setText(mTracks.get(pos).getmDescription());
            Track track = mTracks.get(getAdapterPosition());
            if (track.isLiked()) {
                ivLike.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else {
                ivLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
            if (track.isHidden()) {
                tvTrackTitle.setTextColor(context.getResources().getColor(R.color.light_gray));
                ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_red_24dp);
                tvTrackDescription.setTextColor(context.getResources().getColor(R.color.light_gray));

            } else {
                ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
                tvTrackTitle.setTextColor(context.getResources().getColor(R.color.white));
                tvTrackDescription.setTextColor(context.getResources().getColor(R.color.white));

            }
        }

        @Override
        public void onClick(View view) {
            onTrackClicked.OnTrackClickedListener(mTracks,
                    getAdapterPosition(), prev);
            prev = getAdapterPosition();
            prevView = view;
            tvTrackTitle.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }
    }
}
