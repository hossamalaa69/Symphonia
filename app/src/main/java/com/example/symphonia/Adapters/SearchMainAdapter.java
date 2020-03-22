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

/**
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class SearchMainAdapter extends RecyclerView.Adapter<SearchMainAdapter.SearchCategoriesViewHolder>  {
    private ArrayList<Container> categorySet;
    private CatListItemClickListner listItemClickListner;
    public interface CatListItemClickListner{
        void onListItemClickListner(Container c);
    }

    /**
     *
     * @param data Arraylist of Container which has the data of category or genre
     * @param l context
     */
    public SearchMainAdapter(ArrayList<Container> data,CatListItemClickListner l) {
        categorySet = data;
        listItemClickListner=l;
    }

    /**
     *
     * @return return number of items in recyclerview which will be the genre Arraylist size
     */
    @Override
    public int getItemCount() {
        return categorySet.size();
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return SearchCategoriesViewHolder
     */
    @NonNull
    @Override
    public SearchCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_items_layout, parent, false);
        SearchCategoriesViewHolder VH = new SearchCategoriesViewHolder(V);
        return VH;
    }

    /**
     *Called by RecyclerView when it starts observing this Adapter.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull SearchCategoriesViewHolder holder, int position) {
        holder.makeCategory(position);
    }

    class SearchCategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView;
        private ImageView imageView;

        /**
         * @param itemView view
         */
        public SearchCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            //add click listener
            itemView.setOnClickListener(this);
            //attach views
            textView = itemView.findViewById(R.id.tv_search_item);
            imageView = itemView.findViewById(R.id.img_search_item);
        }

        /**
         * set text and recources in the view of the item
         * @param pos position of the item
         */
        public void makeCategory(int pos) {
            Container temp = categorySet.get(pos);
            textView.setText(temp.getCat_Name());
            imageView.setImageResource(temp.getImg_Res());
        }

        /**
         * handle click on item
         * @param v view
         */
        @Override
        public void onClick(View v) {
            listItemClickListner.onListItemClickListner(categorySet.get(getAdapterPosition()));
        }
    }
}
