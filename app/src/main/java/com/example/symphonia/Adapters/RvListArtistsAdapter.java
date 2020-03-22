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

import com.example.symphonia.Activities.User_Interface.AddArtistsActivity;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;

import java.util.ArrayList;

public class RvListArtistsAdapter extends RecyclerView.Adapter<RvListArtistsAdapter.ArtistViewHolder> {

    private ArrayList<Artist> mArtists;
    private Context mContext;

    public RvListArtistsAdapter(ArrayList<Artist> artists, Context context) {
        this.mArtists = artists;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mArtists.size() + 1;
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

    public void clear(){
        mArtists.clear();
        mArtists = new ArrayList<>();
    }

    public void addAll(ArrayList<Artist> artists){
        this.mArtists.addAll(artists);
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView artistImage;
        TextView artistName;

        ArtistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            artistImage = (ImageView)itemView.findViewById(R.id.image_artist);
            artistName = (TextView) itemView.findViewById(R.id.text_artist_name);
        }
        void bind(int position) {
            if(position == mArtists.size()) {
                artistImage.setImageResource(R.drawable.add_image);
                artistName.setText(R.string.add_artists);
            }
            else{
                Artist artist = mArtists.get(position);
                artistImage.setImageBitmap(artist.getImage());
                artistName.setText(artist.getArtistName());
            }


        }

        @Override
        public void onClick(View v) {
            if(getAdapterPosition() == mArtists.size())
            {
                Intent addArtistsIntent = new Intent(mContext, AddArtistsActivity.class);
                mContext.startActivity(addArtistsIntent);
            }
        }
    }

}