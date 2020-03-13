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

public class SeeAllPlaylistsAdapter extends RecyclerView.Adapter<SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder> {
    private ArrayList<Container> container;

    public SeeAllPlaylistsAdapter(ArrayList<Container> data) {
        container = data;
    }
    @NonNull
    @Override
    public SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.seeall_playlists_item, parent, false);
        SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder VH = new SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder(V);
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder holder, int position) {
        holder.makeResult(position);
    }


    @Override
    public int getItemCount() {
        return container.size();
    }


    class SeeAllPlaylistsViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public SeeAllPlaylistsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_search_all_playlists);
            imageView=itemView.findViewById(R.id.img_search_all_playlists);
        }

        public void makeResult(int pos) {
            Container temp = container.get(pos);
            textView.setText(temp.getCat_Name());
            imageView.setImageResource(temp.getImg_Res());
        }
    }
}
