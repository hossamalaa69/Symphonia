package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RvTracksPreviewAdapter extends RecyclerView.Adapter<RvTracksPreviewAdapter.TrackPreviewViewHolder> {

    /**
     * list of user's saved albums
     */
    private ArrayList<Track> mTracks;
    /**
     * an object from the interface to handle the click on list items
     */
    private ListItemClickListener mOnClickListener;


    public RvTracksPreviewAdapter(ArrayList<Track> mTracks, ListItemClickListener mOnClickListener) {
        this.mTracks = mTracks;
        this.mOnClickListener = mOnClickListener;
    }

    /**
     * @return the number of items in the recyclerview
     */
    @Override
    public int getItemCount() {
        return mTracks.size();
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
    public TrackPreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_track_preview_item, parent, false);
        return new TrackPreviewViewHolder(view);
    }

    /**
     * bind the view holder which has the turn to be shown
     *
     * @param holder item view holder
     * @param position its position in recyclerview
     */
    @Override
    public void onBindViewHolder(TrackPreviewViewHolder holder, int position) {
        holder.bind(position);
    }

    /**
     * holding the data of each item and showing them
     */
    public class TrackPreviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView trackImage;
        TextView trackName;
        TextView artistName;

        /**
         * prepare the views of the item and set the clicklistener
         *
         * @param itemView view of the recyclerview item
         */
        TrackPreviewViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Utils.cancelTouchAnimation(itemView);
            trackImage = itemView.findViewById(R.id.image_album);
            trackName = itemView.findViewById(R.id.text_album_name);
            artistName = itemView.findViewById(R.id.text_artist_name);

        }

        /**
         * called when the viewholder of an item is binded
         *
         * @param position item position
         */
        void bind(int position) {
            Track track = mTracks.get(position);
            Picasso.get()
                    .load(track.getImageUrl())
                    .placeholder(R.drawable.placeholder_playlist)
                    .into(trackImage);
            trackName.setText(track.getmTitle());
            artistName.setText(track.getmArtist());
        }

        /**
         * calls the interface object of the list item click
         *
         * @param v clicked item
         */
        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }

    }

    /**
     * Interface to handle click for the items
     */
    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }


}






