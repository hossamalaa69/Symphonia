package com.example.symphonia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_welcome);
    }

    public void openLogin(View view) {
        Intent i = new Intent(this, LoginArtistActivity.class);
        startActivity(i);
    }

}
