package com.example.symphonia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Utils.Container;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private ArrayList<Container> container;

    public SearchResultAdapter(ArrayList<Container> data) {
        container = data;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_list_item, parent, false);
        SearchResultViewHolder VH = new SearchResultViewHolder(V);
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.MakeResult(position);
    }

    @Override
    public int getItemCount() {
        return container.size();
    }


    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView TV;
        private TextView TV2;
        private ImageView IV;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            TV = itemView.findViewById(R.id.tv_search_list_item);
            TV2 = itemView.findViewById(R.id.tv_search_list_item_type);
            IV = itemView.findViewById(R.id.img_search_list_item);
        }

        public void MakeResult(int pos) {
            Container temp = container.get(pos);
            TV.setText(temp.getCat_Name());
            TV2.setText(temp.getCat_Name2());
            IV.setImageResource(temp.getImg_Res());
        }
    }
}
