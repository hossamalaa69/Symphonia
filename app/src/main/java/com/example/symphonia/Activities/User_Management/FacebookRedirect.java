package com.example.symphonia.Activities.User_Management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.symphonia.Activities.User_Management.ForgetPassword.ResetPassword;
import com.example.symphonia.R;

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
        Log.d("link",appLinkAction);
        Uri appLinkData = appLinkIntent.getData();
        if(appLinkData!=null){
            String data = appLinkData.getPath();
            String token =data.substring(10, data.length()-1);
            TextView textView = (TextView) findViewById(R.id.data);
            textView.setText(data);
            Toast.makeText(this,token,Toast.LENGTH_LONG).show();
        }
    }
}
