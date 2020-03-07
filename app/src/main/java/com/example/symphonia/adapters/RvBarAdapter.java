package com.example.symphonia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.data.Track;

import java.util.ArrayList;

public class RvBarAdapter extends RecyclerView.Adapter<RvBarAdapter.BarHolder> {
    private Context context;
    private ArrayList<Track> tracks;
    private int prevPos;
    private int nextPos;

    private ItemInterface itemInterface;

    public RvBarAdapter(Context context, ArrayList<Track> tracks) {
        this.context = context;
        this.tracks = tracks;
        itemInterface = (ItemInterface) context;
    }

    @NonNull
    @Override
    public BarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_bar, parent, false);
        return new BarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BarHolder holder) {
        super.onViewDetachedFromWindow(holder);

        // get pos of the detached view
        prevPos = holder.getAdapterPosition();

        // check if new view is visible and the other one that was visible isn't now
        if (nextPos - prevPos == 1 || prevPos - nextPos == 1) {
            itemInterface.OnItemSwitchedListener(nextPos);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BarHolder holder) {
        super.onViewAttachedToWindow(holder);

        // get position of attached view to update to its data
        nextPos = holder.getAdapterPosition();

    }

    public interface ItemInterface {
        void OnItemSwitchedListener(int pos);
        void OnItemClickedListener(ArrayList<Track> tracks, int adapterPosition);
    }

    public class BarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trackDetails;

        public BarHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            trackDetails = itemView.findViewById(R.id.tv_track_details_bar);
        }

        @Override
        public void onClick(View view) {
            itemInterface.OnItemClickedListener(tracks,getAdapterPosition());
        }

        public void bind() {
            trackDetails.setText(tracks.get(getAdapterPosition()).getmTitle()
                    .concat(tracks.get(getAdapterPosition()).getmDescription()));
        }
    }
}
