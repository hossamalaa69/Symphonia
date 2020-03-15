package com.example.symphonia.Activities.UserUI;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.symphonia.Activities.ArtistRegister.WelcomeArtistActivity;
import com.example.symphonia.Activities.ListenerRegister.WelcomeListenerActivity;
import com.example.symphonia.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
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
