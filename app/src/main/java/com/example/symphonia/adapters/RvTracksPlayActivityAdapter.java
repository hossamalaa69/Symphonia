package com.example.symphonia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Utils.Track;

import java.util.ArrayList;

public class RvTracksPlayActivityAdapter extends RecyclerView.Adapter<RvTracksPlayActivityAdapter.Holder> {

    private Context context;
    private ArrayList<Track> tracks;
    private int prevPos = -1;       //hold pos of view that is recycled
    private int nextPos = -1;      //hold pos of view that is visible  now
    private OnItemSwitched onItemSwitched;

    public RvTracksPlayActivityAdapter(Context context, ArrayList<Track> tracks) {
        this.context = context;
        onItemSwitched = (OnItemSwitched) context;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rv_item_playlist_play_activty, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull Holder holder) {
        super.onViewDetachedFromWindow(holder);

        // get pos of the detached view
        prevPos = holder.getAdapterPosition();

        // check if new view is visible and the other one that was visible isn't now
        if (nextPos - prevPos == 1 || prevPos - nextPos == 1) {
            onItemSwitched.OnItemSwitchedListener(nextPos);
        }

    }

    @Override
    public void onViewAttachedToWindow(@NonNull Holder holder) {
        super.onViewAttachedToWindow(holder);

        // get position of attached view to update to its data
        nextPos = holder.getAdapterPosition();

    }

    @Override
    public void onViewRecycled(@NonNull Holder holder) {
        super.onViewRecycled(holder);
        //   Toast.makeText(context,""+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public interface OnItemSwitched {
        void OnItemSwitchedListener(int pos);
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView trackImage;

        public Holder(@NonNull View itemView) {
            super(itemView);
            trackImage = itemView.findViewById(R.id.iv_track_image_item_play_activity);
        }

        private void bind() {
            trackImage.setImageResource(tracks.get(getAdapterPosition()).getmImageResources());
        }
    }
}
