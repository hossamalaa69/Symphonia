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

    private ArrayList<Artist> mArtists;
    private ListItemClickListener mOnClickListener;
    private ServiceController mServiceController;

    public RvGridArtistsAdapter(ArrayList<Artist> artists, ListItemClickListener mOnClickListener) {
        this.mArtists = artists;
        this.mOnClickListener = mOnClickListener;
        mServiceController = ServiceController.getInstance();
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
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



    public void clear(){
        mArtists.clear();
        mArtists = new ArrayList<>();
    }

    public void addAll(ArrayList<Artist> artists){
        this.mArtists.addAll(artists);
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
            artistImage = itemView.findViewById(R.id.image_artist);
            artistName = itemView.findViewById(R.id.text_artist_name);
            checkImage = itemView.findViewById(R.id.image_check);
        }

        void bind(int position) {
            Artist artist = mArtists.get(position);
            artistImage.setImageBitmap(artist.getImage());
            artistName.setText(artist.getArtistName());
            if(mServiceController.isFollowing(Constants.currentUser.isListenerType(), Constants.currentToken, mArtists.get(position).getId()))
                checkImage.setVisibility(View.VISIBLE);
            else
                checkImage.setVisibility(View.GONE);

        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(v, getAdapterPosition());
        }
    }
}