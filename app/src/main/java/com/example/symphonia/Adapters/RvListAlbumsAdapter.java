package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.R;

import java.util.ArrayList;

public class RvListAlbumsAdapter extends RecyclerView.Adapter<RvListAlbumsAdapter.AlbumViewHolder> {

    private ArrayList<Album> mAlbums;
    private ListItemClickListener mOnClickListener;
    public RvListAlbumsAdapter(ArrayList<Album> albums, ListItemClickListener mOnClickListener) {
        this.mAlbums = albums;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_album_list_item, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.bind(position);
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView albumImage;
        TextView albumName;
        TextView artistName;

        AlbumViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            albumImage = itemView.findViewById(R.id.image_album);
            albumName = itemView.findViewById(R.id.text_album_name);
            artistName = itemView.findViewById(R.id.text_artist_name);

        }

        void bind(int position) {
            Album album = mAlbums.get(position);
            albumImage.setImageBitmap(album.getAlbumImage());
            albumName.setText(album.getAlbumName());
            artistName.setText(album.getAlbumArtists().get(0).getArtistName());
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(v, getAdapterPosition());
        }
    }

    public interface ListItemClickListener{
        void onListItemClick(View v, int clickedItemIndex);
    }

}





