package com.example.symphonia.Activities.User_Management.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.symphonia.Activities.User_Interface.AddArtistsActivity;
import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

public class ApplyArtist extends AppCompatActivity implements RestApi.updateUIApplyArtist {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_artist);
        // ATTENTION: This was auto-generated to handle app links.
        handleIntent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
    }

    private void handleIntent() {
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if(appLinkData!=null){
            String newToken = appLinkData.getLastPathSegment();
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.applyArtist(this, newToken);
        } else{
            Intent i = new Intent(this, StartActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void updateUIApplyArtistSuccess() {
        // Creates object of SharedPreferences.
        SharedPreferences sharedPref= getSharedPreferences("LoginPref", 0);
        //new Editor
        SharedPreferences.Editor editor= sharedPref.edit();
        //set user's params if REST API mode

        editor.putString("token", Constants.currentToken);
        editor.putString("name", Constants.currentUser.getmName());
        editor.putString("email", Constants.currentUser.getmEmail());
        editor.putString("id",Constants.currentUser.get_id());
        editor.putBoolean("type", Constants.currentUser.isListenerType());
        editor.putBoolean("premium", Constants.currentUser.isPremuim());
        editor.putString("image",Constants.currentUser.getImageUrl());

        //commits edits
        editor.apply();

        //sends to AddArtist activity to suggest artists for user
        Intent i = new Intent(this, AddArtistsActivity.class);
        i.putExtra("newUser", "true");
        startActivity(i);
    }

    @Override
    public void updateUIApplyArtistFailed() {
        Intent i = new Intent(this, StartActivity.class);
        startActivity(i);
    }
}
