package com.example.symphonia.adapters;

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
import com.example.symphonia.Utils.Artist;

import java.util.ArrayList;

public class RvListArtistsAdapter extends RecyclerView.Adapter<RvListArtistsAdapter.ArtistViewHolder> {

    private ArrayList<Artist> artists;
    private Context context;

    public RvListArtistsAdapter(ArrayList<Artist> artists, Context context) {
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_artist_list_item, parent, false);
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
            artistImage.setImageResource(artist.getImageResourceId());
            artistName.setText(artist.getArtistName());

        }

        @Override
        public void onClick(View v) {
            if(getAdapterPosition() == artists.size() - 1)
            {
                Intent addArtistsIntent = new Intent(context, AddArtistsActivity.class);
                context.startActivity(addArtistsIntent);
            }
        }
    }


}