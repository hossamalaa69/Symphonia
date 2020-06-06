package com.example.symphonia.Activities.User_Management.Notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.NotificationAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Fragments_and_models.profile.FragmentProfile;
import com.example.symphonia.Fragments_and_models.profile.ProfilePlaylistsFragment;
import com.example.symphonia.Entities.Context;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Helpers.Utils;
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
        mProgressBar = findViewById(R.id.progress);

        if(Constants.DEBUG_STATUS){
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            addSomeItems();
        }
        else{
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
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
        if(Constants.DEBUG_STATUS)
            Toast.makeText(this, ""+position,Toast.LENGTH_SHORT).show();
        else {

            //TODO:Mohamoud
            if(mNotificationItems.get(position).getText1().equals("Following User")) {
                Toast.makeText(this, "Follower ID:" + mNotificationItems.get(position).getSenderID()
                        , Toast.LENGTH_SHORT).show();
                String profileID = mNotificationItems.get(position).getSenderID();
                /*
                TODO:open profile page which it's ID is stored in profileID variable
                */
                Intent i = new Intent(this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("id",profileID );
                i.putExtras(b);
                startActivity(i);
            }

            //TODO:Mahmoud
            else if(mNotificationItems.get(position).getText1().equals("Like Playlist")) {
                Toast.makeText(this, "User ID:" + mNotificationItems.get(position).getSenderID()
                        , Toast.LENGTH_SHORT).show();
                String profileID = mNotificationItems.get(position).getSenderID();
                /*
                TODO:open profile page which it's ID is stored in profileID variable
                 */

                Intent i = new Intent(this, MainActivity.class);
                Bundle b = new Bundle();
                b.putString("id",profileID );
                i.putExtras(b);
                startActivity(i);

            }

            //TODO:Khalid
            else if(mNotificationItems.get(position).getText1().equals("PlayList Updated")) {
                Toast.makeText(this, "Playlist ID:" + mNotificationItems.get(position).getSenderID()
                        , Toast.LENGTH_SHORT).show();
                String playlistID = mNotificationItems.get(position).getSenderID();
                RestApi.UpdatePlaylist listener = new RestApi.UpdatePlaylist() {
                    @Override
                    public void updatePlaylist(Playlist playlist) {
                            Intent i = new Intent(NotificationsHistoryActivity.this,MainActivity.class);
                            i.putExtra("playlist_fragment", "playlist_fragment");
                            Utils.displayedContext = new Context();
                            Utils.displayedContext.setContext(playlist);
                            startActivity(i);
                    }
                };
                ServiceController.getInstance().getPlaylist(listener,playlistID);
                /*
                 TODO:open playlist page which it's ID is stored in playlistID variable
                 */
            }
            //TODO:Islam
            else {
                Toast.makeText(this, "Album ID:" + mNotificationItems.get(position).getSenderID()
                        , Toast.LENGTH_SHORT).show();
                String albumID = mNotificationItems.get(position).getSenderID();
                /*
                 TODO:open album page which it's ID is stored in albumID variable
                 */
            }
        }

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
        ArrayList<NotificationItem> notificationItemArrayList = new ArrayList<>();
        try {
            JSONObject notifications = root.getJSONObject("notifications");
            JSONArray items = notifications.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                JSONObject item = items.getJSONObject(i);
                JSONObject data = item.getJSONObject("data");
                String dataIn = data.getString("data");
                JsonObject object = new JsonParser().parse(dataIn).getAsJsonObject();
                String SenderID = object.get("from").getAsString();

                JSONObject notification = item.getJSONObject("notification");
                String title = notification.getString("title");
                String body = notification.getString("body");
                String icon = "";
                try {
                     icon = notification.getString("icon");
                }catch (Exception e){
                    icon = "https://thesymphonia.ddns.net/api/v1/images/users/default.png";
                }
                NotificationItem notificationItem = new NotificationItem(R.drawable.placeholder_user,
                        title,body);
                notificationItem.setImageUrl(icon);
                notificationItem.setSenderID(SenderID);
                notificationItemArrayList.add(notificationItem);
            }
            for(int i=notificationItemArrayList.size()-1;i>=0;i--){
                mNotificationItems.add(notificationItemArrayList.get(i));
            }
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            notificationAdapter.notifyDataSetChanged();

        } catch (JSONException e){
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }

    }

    @Override
    public void updateUIGetNotifyFailed() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

}
