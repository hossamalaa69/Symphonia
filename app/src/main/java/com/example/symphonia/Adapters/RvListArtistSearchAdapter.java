package com.example.symphonia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.UserUI.AddArtistsActivity;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;

import java.util.ArrayList;

public class RvListArtistSearchAdapter extends RecyclerView.Adapter<RvListArtistSearchAdapter.ArtistViewHolder> {

    private ArrayList<Artist> artists;
    private Context context;

    public RvListArtistSearchAdapter(ArrayList<Artist> artists, Context context) {
        this.artists = artists;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_artist_search_list_item, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        holder.bind(position);
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView artistImage;
        TextView artistName;

        ArtistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            artistImage = (ImageView)itemView.findViewById(R.id.artist_image);
            artistName = (TextView) itemView.findViewById(R.id.artist_name);
        }
        void bind(int position) {
            Artist artist = artists.get(position);
            artistImage.setImageBitmap(artist.getImage());
            artistName.setText(artist.getArtistName());
        }

        @Override
        public void onClick(View v) {
        }
    }

    public void clear(){
        artists.clear();
        artists = new ArrayList<>();
    }

    public void addAll(ArrayList<Artist> artists){
        this.artists.addAll(artists);
    }


}