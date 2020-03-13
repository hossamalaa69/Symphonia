package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Entities.Container;

import java.util.ArrayList;

public class SeeAllArtistsAdapter extends RecyclerView.Adapter<SeeAllArtistsAdapter.SeeAllArtistsViewHolder> {
    private ArrayList<Container> container;

    public SeeAllArtistsAdapter(ArrayList<Container> data) {
        container = data;
    }
    @NonNull
    @Override
    public SeeAllArtistsAdapter.SeeAllArtistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.seeall_artists_genres_profiles_item, parent, false);
        SeeAllArtistsAdapter.SeeAllArtistsViewHolder VH = new SeeAllArtistsAdapter.SeeAllArtistsViewHolder(V);
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull SeeAllArtistsViewHolder holder, int position) {
        holder.MakeResult(position);
    }


    @Override
    public int getItemCount() {
        return container.size();
    }


    class SeeAllArtistsViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public SeeAllArtistsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_search_all_artists);
            imageView=itemView.findViewById(R.id.img_search_all_artists);
        }

        public void MakeResult(int pos) {
            Container temp = container.get(pos);
            textView.setText(temp.getCat_Name());
            imageView.setImageResource(temp.getImg_Res());
        }
    }
}
