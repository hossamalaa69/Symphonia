package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private ArrayList<Container> container;
    private Boolean chooseImg;
    private Boolean setText;
    private ListItemClickListner listner;

    public interface ListItemClickListner{
        void onItemEraseListener(int pos,int containerSize);
    }

    public SearchResultAdapter(ArrayList<Container> data,Boolean b,ListItemClickListner l) {
        container = data;
        chooseImg = b;
        setText=false;
        listner=l;
    }
    public SearchResultAdapter(ArrayList<Container> data,Boolean b,Boolean a) {
        container = data;
        chooseImg = b;
        setText=a;
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
       holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.remove(position);
                listner.onItemEraseListener(position,container.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return container.size();
    }


    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textView2;
        private ImageView imageView;
        private ImageView show;
        private ImageView close;
        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.tv_search_list_item);
            textView2=(TextView)itemView.findViewById(R.id.tv_search_list_item_type);
            imageView=(ImageView)itemView.findViewById(R.id.img_search_list_item);
            show=(ImageView)itemView.findViewById(R.id.img_show);
            close=(ImageView)itemView.findViewById(R.id.img_close);
        }

        public void MakeResult(int pos) {
            Container temp = container.get(pos);
            textView.setText(temp.getCat_Name());
            if(setText){
                textView2.setText(temp.getCat_Name2().substring(temp.getCat_Name2().indexOf(".") + 1));
            }
            else {
                textView2.setText(temp.getCat_Name2());
            }
            imageView.setImageResource(temp.getImg_Res());
            if(chooseImg){
                show.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
            }
            else {
                show.setVisibility(View.GONE);
                close.setVisibility(View.VISIBLE);
            }
        }
    }
}
