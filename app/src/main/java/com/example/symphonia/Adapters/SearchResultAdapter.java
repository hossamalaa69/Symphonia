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

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private ArrayList<Container> container;
    private Boolean ChooseImg;

    public SearchResultAdapter(ArrayList<Container> data,Boolean b) {
        container = data;
        ChooseImg = b;
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
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, final int position) {
        holder.MakeResult(position);
       holder.Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, container.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return container.size();
    }


    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView TV;
        private TextView TV2;
        private ImageView IV;
        private ImageView Show;
        private ImageView Close;
        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            TV=(TextView)itemView.findViewById(R.id.tv_search_list_item);
            TV2=(TextView)itemView.findViewById(R.id.tv_search_list_item_type);
            IV=(ImageView)itemView.findViewById(R.id.img_search_list_item);
            Show=(ImageView)itemView.findViewById(R.id.img_show);
            Close=(ImageView)itemView.findViewById(R.id.img_close);
        }

        public void MakeResult(int pos) {
            Container temp = container.get(pos);
            TV.setText(temp.getCat_Name());
            TV2.setText(temp.getCat_Name2());
            IV.setImageResource(temp.getImg_Res());
            if(ChooseImg){
                Show.setVisibility(View.VISIBLE);
                Close.setVisibility(View.GONE);
            }
            else {
                Show.setVisibility(View.GONE);
                Close.setVisibility(View.VISIBLE);
            }
        }
    }
}
