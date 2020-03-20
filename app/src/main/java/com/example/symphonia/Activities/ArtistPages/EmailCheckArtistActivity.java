package com.example.symphonia.Activities.ArtistPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

public class EmailCheckArtistActivity extends AppCompatActivity {

    private String user;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_check_listener);

        Bundle b = getIntent().getExtras();
        user = b.getString("user");

        message = findViewById(R.id.message);
        message.append(" " + user);
    }

    public void openGmail(View view) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        startActivity(intent);
    }
}
