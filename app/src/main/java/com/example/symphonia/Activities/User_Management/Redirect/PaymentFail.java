package com.example.symphonia.Activities.User_Management.Redirect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Helpers.AdDialog;
import com.example.symphonia.R;

public class PaymentFail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        handleIntent();
    }
    private void handleIntent() {
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("go_to","premium" );
        startActivity(i);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
    }
}
