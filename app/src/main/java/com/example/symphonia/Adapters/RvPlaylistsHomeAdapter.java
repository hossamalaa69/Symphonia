package com.example.symphonia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.R;

import java.util.ArrayList;

public class RvPlaylistsHomeAdapter extends RecyclerView.Adapter<RvPlaylistsHomeAdapter.Holder> {

    public OnPlaylistClicked onPlaylistClicked;
    private ArrayList<Playlist> mPlaylists;
    private Context context;

    public RvPlaylistsHomeAdapter(Context context, ArrayList<Playlist> list) {
        this.mPlaylists = list;
        this.context = context;
        onPlaylistClicked = (OnPlaylistClicked) context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_playlist_home, parent, false);
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

    public interface OnPlaylistClicked {
        void OnPlaylistClickedListener(Playlist playlist);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {


        // TODO delete this hard coded data
        int[] images = {R.drawable.download, R.drawable.download1, R.drawable.images, R.drawable.images2, R.drawable.images3};
        private ImageView ivPlaylistImage;
        private TextView tvPlaylistTitle;
        private TextView tvPlaylistDescription;

        public Holder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            ivPlaylistImage = itemView.findViewById(R.id.iv_playlist_image_home);
            tvPlaylistTitle = itemView.findViewById(R.id.tv_playlist_title_home);
            tvPlaylistDescription = itemView.findViewById(R.id.tv_playlist_description_home);

        }

        public void bind(int pos) {
            if (mPlaylists.get(pos).getmPlaylistImage() == null) {
                ivPlaylistImage.setImageResource(R.drawable.no_image);
            }
            ivPlaylistImage.setImageBitmap(
                    mPlaylists.get(pos).getmPlaylistImage());
            if (mPlaylists.get(pos).getmPlaylistTitle() == null) {
                tvPlaylistTitle.setVisibility(View.GONE);
            }

            tvPlaylistTitle.setText(mPlaylists.get(pos).getmPlaylistTitle());
            if (mPlaylists.get(pos).getmPlaylistDescription() == null) {
                tvPlaylistDescription.setVisibility(View.GONE);
            } else {
                tvPlaylistDescription.setText(mPlaylists.get(pos).getmPlaylistDescription());
            }
        }

        @Override
        public void onClick(View view) {
            onPlaylistClicked.OnPlaylistClickedListener(
                    mPlaylists.get(getAdapterPosition()));
        }
    }
}
