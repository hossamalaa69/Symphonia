package com.example.symphonia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.symphonia.Artist_Register.WelcomeArtistActivity;
import com.example.symphonia.Listener_Register.WelcomeListenerActivity;

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
