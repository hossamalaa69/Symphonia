package com.example.symphonia.Activities.User_Management.Redirect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.User;
import com.example.symphonia.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class FacebookRedirect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_redirect);
        handleIntent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
    }

    private void handleIntent() {
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        if(appLinkData!=null){

            String q2 = appLinkData.getQuery();
            String obj="";
            try {
                String token = appLinkData.getLastPathSegment();
                assert q2 != null;
                obj = q2.substring(5);
                JsonObject convertedObject;
                convertedObject = new Gson().fromJson(obj, JsonObject.class);
                String id = convertedObject.get("_id").getAsString();
                String imageUrl = convertedObject.get("imageFacebookUrl").getAsString();
                String email = convertedObject.get("email").getAsString();
                String name = convertedObject.get("name").getAsString();
                String type = convertedObject.get("type").getAsString();
                boolean mType = true;
                boolean premium = false;
                if(type.equals("user-premium")){
                    type = "user";
                    premium=true;
                }else if(type.equals("artist")){
                    mType=false;
                    premium=true;
                }

                Constants.currentToken=token;
                Constants.currentUser = new User(email, id, name, mType, premium);
                Constants.currentUser.setUserType(type);
                Constants.currentUser.setImageUrl(imageUrl);

                // Creates object of SharedPreferences.
                SharedPreferences sharedPref= getSharedPreferences("LoginPref", 0);
                //new Editor
                SharedPreferences.Editor editor= sharedPref.edit();
                //put user's params if REST API mode
                editor.putString("token", Constants.currentToken);
                editor.putString("name", Constants.currentUser.getmName());
                editor.putString("email", Constants.currentUser.getmEmail());
                editor.putString("id",Constants.currentUser.get_id());
                editor.putBoolean("type", Constants.currentUser.isListenerType());
                editor.putBoolean("premium", Constants.currentUser.isPremuim());
                editor.putString("image",Constants.currentUser.getImageUrl());
                editor.apply();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);

            }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this,getString(R.string.error),Toast.LENGTH_SHORT).show();
             }
        }
        else{
            Toast.makeText(this,getString(R.string.failed_login),Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, StartActivity.class);
            startActivity(i);
        }
    }
}
