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

/**
 * Control the items of the Artists grid in AddArtistsActivity
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class RvGridArtistsAdapter extends RecyclerView.Adapter<RvGridArtistsAdapter.ArtistViewHolder> {

    /**
     * the list of the recommended artists to be shown to the user
     */
    private ArrayList<Artist> mArtists;
    /**
     * an object from the interface to handle the click on grid items
     */
    private ListItemClickListener mOnClickListener;
    /**
     * instance to request from the mock services and API
     */
    private ServiceController mServiceController;

    /**
     * Constructor to take the data and get the service controller
     *
     * @param artists recommended artists
     * @param mOnClickListener click listener for the grid items
     */
    public RvGridArtistsAdapter(ArrayList<Artist> artists, ListItemClickListener mOnClickListener) {
        this.mArtists = artists;
        this.mOnClickListener = mOnClickListener;
        mServiceController = ServiceController.getInstance();
    }

    /**
     * get the number of items in the artists array
     *
     * @return items number
     */
    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    /**
     * returns the right view holder for each item in recyclerview
     *
     * @param parent the root view
     * @param viewType useful if there is more than one view holder
     * @return view holder for the artist item
     */
    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_artist_add, parent, false);
        return new ArtistViewHolder(view);
    }

    /**
     * bind the view holder which has the turn to be shown
     *
     * @param holder item view holder
     * @param position its position in recyclerview
     */
    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        holder.bind(position);
    }


    /**
     * clear all the data in artists array
     * and create a new one
     */
    public void clear(){
        mArtists.clear();
        mArtists = new ArrayList<>();
    }

    /**
     * adding an arraylist to the existing one
     *
     * @param artists array to be added
     */
    public void addAll(ArrayList<Artist> artists){
        this.mArtists.addAll(artists);
    }

    /**
     * interface to handle click for the items
     */
    public interface ListItemClickListener{
        void onListItemClick(View v, int clickedItemIndex);
    }

    /**
     * holding the data of each item and showing them
     */
    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView artistImage;
        TextView artistName;
        View checkImage;

        /**
         * prepare the views of the item
         *
         * @param itemView view of the recyclerview item
         */
        ArtistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            artistImage = itemView.findViewById(R.id.image_artist);
            artistName = itemView.findViewById(R.id.text_artist_name);
            checkImage = itemView.findViewById(R.id.image_check);
        }

        /**
         * called when the viewholder of an item is binded
         *
         * @param position item position
         */
        void bind(int position) {
            Artist artist = mArtists.get(position);
            artistImage.setImageBitmap(artist.getImage());
            artistName.setText(artist.getArtistName());
            if(mServiceController.isFollowing(Constants.currentUser.isListenerType(), Constants.currentToken, mArtists.get(position).getId()))
                checkImage.setVisibility(View.VISIBLE);
            else
                checkImage.setVisibility(View.GONE);

        }

        /**
         * calls the interface object of the list item click
         *
         * @param v clicked item
         */
        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(v, getAdapterPosition());
        }
    }
}