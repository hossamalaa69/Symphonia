package com.example.symphonia.Adapters;

import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Management.Notifications.NotificationItem;
import com.example.symphonia.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {

    private ArrayList<NotificationItem> mNotifications;
    private OnItemClickListener mListener;
    private Context mContext;

    public NotificationAdapter(ArrayList<NotificationItem> mItems, Context mContext) {
        this.mNotifications = mItems;
        this.mContext = mContext;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //sets layout for each item in recycler
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_notification, parent, false);

        return new Holder(view, mListener);
    }

    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        CircleImageView profile_img;
        TextView text1;
        TextView text2;

        public Holder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            profile_img = itemView.findViewById(R.id.profile_img);
            text1 = itemView.findViewById(R.id.main_text);
            text2 = itemView.findViewById(R.id.description_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(int position) {
            //gets each feature from array, then sets it in text views
            NotificationItem notificationItem = mNotifications.get(position);
            profile_img.setImageResource(notificationItem.getImageResource());
            text1.setText(notificationItem.getText1());
            text2.setText(notificationItem.getText2());
        }

    }




}

