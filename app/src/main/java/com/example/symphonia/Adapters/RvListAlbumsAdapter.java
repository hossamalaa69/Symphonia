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

    private ArrayList<Album> albums;

    public RvListAlbumsAdapter(ArrayList<Album> albums) {
        this.albums = albums;
    }

    @Override
    public int getItemCount() {
        return albums.size();
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
            albumImage = itemView.findViewById(R.id.album_image);
            albumName = itemView.findViewById(R.id.album_name);
            artistName = itemView.findViewById(R.id.artist_name);

        }

        void bind(int position) {
            Album album = albums.get(position);
            albumImage.setImageBitmap(album.getAlbumImage());
            albumName.setText(album.getAlbumName());
            artistName.setText(album.getAlbumArtists().get(0).getArtistName());
        }

        @Override
        public void onClick(View v) {
        }
    }

}





