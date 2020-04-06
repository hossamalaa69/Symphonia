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
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 *
 */
public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder> {
    private ArrayList<Container> container;

    /**
     * constructor of the adapter
     *
     * @param data Arraylist of Container which has the data of the adapter
     */
    public FollowersAdapter(ArrayList<Container> data) {
        container = data;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return SeeAllPlaylistsViewHolder
     */
    @NonNull
    @Override
    public FollowersAdapter.FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_list_item, parent, false);
        FollowersAdapter.FollowersViewHolder VH = new FollowersAdapter.FollowersViewHolder(V);
        return VH;
    }

    /**
     * Called by RecyclerView when it starts observing this Adapter.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull FollowersViewHolder holder, int position) {
        holder.makeFollowerstItem(position);
    }

    /**
     * @return return number of items in recyclerview which will be the Arraylist size
     */
    @Override
    public int getItemCount() {
        return container.size();
    }


    class FollowersViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private RoundedImageView imageView;
        private TextView textView2;
        private ImageView done;
        private ImageView follow;

        public FollowersViewHolder(@NonNull View itemView) {
            super(itemView);
            //attach views
            textView = itemView.findViewById(R.id.tv_search_list_item);
            imageView = itemView.findViewById(R.id.img_search_list_item);
            textView2 = itemView.findViewById(R.id.tv_search_list_item_type);
            follow = itemView.findViewById(R.id.img_follow);
            done = itemView.findViewById(R.id.img_you_are_following);
            follow.setVisibility(View.VISIBLE);
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    follow.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                }
            });
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    follow.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                }
            });
        }

        /**
         * set text and recources in the view of the item
         *
         * @param pos position of the item
         */
        public void makeFollowerstItem(int pos) {
            Container temp = container.get(pos);
            textView.setText(temp.getCat_Name());
            imageView.setImageBitmap(temp.getImg_Res());
            imageView.setOval(true);
            textView2.setText(temp.getCat_Name2());
        }
    }
}
