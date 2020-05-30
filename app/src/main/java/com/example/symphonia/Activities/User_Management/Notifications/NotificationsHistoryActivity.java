package com.example.symphonia.Activities.User_Management.Notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.symphonia.Adapters.NotificationAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.symphonia.Helpers.App.getContext;

public class NotificationsHistoryActivity extends AppCompatActivity implements RestApi.updateUIGetNotify {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private ArrayList<NotificationItem> mNotificationItems;
    private NotificationAdapter notificationAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_history);

        mNotificationItems = new ArrayList<>();
        mRecyclerView = findViewById(R.id.rv_notification);
        if(Constants.DEBUG_STATUS){
            addSomeItems();
        }
        else{
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.getNotificationHistory(this,Constants.currentToken);
        }

        //object of layoutManager that controls recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false);
        notificationAdapter = new NotificationAdapter(mNotificationItems, this);
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

    @Override
    public void updateUIGetNotifySuccess(JSONObject root) {

        try {
            JSONObject notifications = root.getJSONObject("notifications");
            JSONArray items = notifications.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                JSONObject item = items.getJSONObject(i);
                JSONObject data = item.getJSONObject("data");
                String dataIn = data.getString("data");
                JsonObject object = new JsonParser().parse(dataIn).getAsJsonObject();
                String SenderID = object.get("follwingUser").getAsString();

                JSONObject notification = item.getJSONObject("notification");
                String title = notification.getString("title");
                String body = notification.getString("body");
                String icon = notification.getString("icon");

                NotificationItem notificationItem = new NotificationItem(R.drawable.placeholder_user,
                        title,body);
                notificationItem.setImageUrl(icon);
                notificationItem.setSenderID(SenderID);
                mNotificationItems.add(notificationItem);
                notificationAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateUIGetNotifyFailed() {

    }
}
