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

public class SearchMainAdapter extends RecyclerView.Adapter<SearchMainAdapter.SearchCategoriesViewHolder> {
    private ArrayList<Container> categorySet;

    public SearchMainAdapter(ArrayList<Container> data) {
        categorySet = data;
    }

    @Override
    public int getItemCount() {
        return categorySet.size();
    }

    @NonNull
    @Override
    public SearchCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_items_layout, parent, false);
        SearchCategoriesViewHolder VH = new SearchCategoriesViewHolder(V);
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCategoriesViewHolder holder, int position) {
        holder.makeCategory(position);
    }

    class SearchCategoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public SearchCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_search_item);
            imageView = itemView.findViewById(R.id.img_search_item);
        }

        public void makeCategory(int pos) {
            Container temp = categorySet.get(pos);
            textView.setText(temp.getCat_Name());
            imageView.setImageResource(temp.getImg_Res());
        }
    }
}
