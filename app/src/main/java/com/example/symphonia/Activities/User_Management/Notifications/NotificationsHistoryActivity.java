package com.example.symphonia.Activities.User_Management.Notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.symphonia.Adapters.NotificationAdapter;
import com.example.symphonia.R;

import java.util.ArrayList;

import static com.example.symphonia.Helpers.App.getContext;

public class NotificationsHistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<NotificationItem> mNotificationItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_history);

        mNotificationItems = new ArrayList<>();
        addSomeItems();

        mRecyclerView = findViewById(R.id.rv_notification);
        //object of layoutManager that controls recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        NotificationAdapter notificationAdapter = new NotificationAdapter(mNotificationItems, this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(notificationAdapter);

        notificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                displayPosition(position);
            }
        });
    }

    public void displayPosition(int position){
        Toast.makeText(this, ""+position,Toast.LENGTH_SHORT).show();
    }

    private void addSomeItems(){
        mNotificationItems.add(new NotificationItem(R.drawable.amr,"Amr Diab"
                , "new user has followed you"));
        mNotificationItems.add(new NotificationItem(R.drawable.elissa,"Elissa"
                , "Artist posted new song"));
        mNotificationItems.add(new NotificationItem(R.drawable.taylor,"Taylor Swift"
                , "new user added song to your playlist"));
        mNotificationItems.add(new NotificationItem(R.drawable.amr,"Amr Diab"
                , "new user has followed you"));
        mNotificationItems.add(new NotificationItem(R.drawable.elissa,"Ellissa"
                , "Artist posted new song"));
        mNotificationItems.add(new NotificationItem(R.drawable.taylor,"Taylor Swift"
                , "new user added song to your playlist"));
        mNotificationItems.add(new NotificationItem(R.drawable.amr,"Amr Diab"
                , "new user has followed you"));
        mNotificationItems.add(new NotificationItem(R.drawable.elissa,"Ellissa"
                , "Artist posted new song"));
        mNotificationItems.add(new NotificationItem(R.drawable.taylor,"Taylor Swift"
                , "new user added song to your playlist"));

    }
}
