package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;

import java.util.ArrayList;

public class RvListArtistSearchAdapter extends RecyclerView.Adapter<RvListArtistSearchAdapter.ArtistViewHolder> {

    private ArrayList<Artist> mArtists;
    private ListItemClickListener mOnClickListener;

    public RvListArtistSearchAdapter(ArrayList<Artist> artists, ListItemClickListener mOnClickListener) {
        this.mArtists = artists;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
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
            Artist artist = mArtists.get(position);
            artistImage.setImageBitmap(artist.getImage());
            artistName.setText(artist.getArtistName());
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

}