package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

public class RvGridArtistsAdapter extends RecyclerView.Adapter<RvGridArtistsAdapter.ArtistViewHolder> {

    private ArrayList<Artist> artists;
    private ListItemClickListener mOnClickListener;
    private ServiceController serviceController;

    public RvGridArtistsAdapter(ArrayList<Artist> artists, ListItemClickListener mOnClickListener) {
        this.artists = artists;
        this.mOnClickListener = mOnClickListener;
        serviceController = ServiceController.getInstance();
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_artist_add, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        holder.bind(position);
    }

    public interface ListItemClickListener{
        void onListItemClick(View v, int clickedItemIndex);
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView artistImage;
        TextView artistName;
        View checkImage;

        ArtistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            artistImage = itemView.findViewById(R.id.artist_image);
            artistName = itemView.findViewById(R.id.artist_name);
            checkImage = itemView.findViewById(R.id.check_image);
        }

        void bind(int position) {
            Artist artist = artists.get(position);
            artistImage.setImageBitmap(artist.getImage());
            artistName.setText(artist.getArtistName());
            if(serviceController.isFollowing(Constants.user.isListenerType(), Constants.mToken, artists.get(position).getId()))
                checkImage.setVisibility(View.VISIBLE);
            else
                checkImage.setVisibility(View.GONE);

        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(v, getAdapterPosition());
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