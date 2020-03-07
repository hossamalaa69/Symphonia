package com.example.symphonia.Artist_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.symphonia.R;

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
