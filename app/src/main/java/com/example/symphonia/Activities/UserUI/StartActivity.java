package com.example.symphonia.Activities.UserUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.ArtistRegister.WelcomeArtistActivity;
import com.example.symphonia.Activities.ListenerRegister.WelcomeListenerActivity;
import com.example.symphonia.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void openListener(View view) {
        Intent i = new Intent(this, WelcomeListenerActivity.class);
        startActivity(i);
    }

    public void openArtist(View view) {
        Intent i = new Intent(this, WelcomeArtistActivity.class);
        startActivity(i);
    }
}
