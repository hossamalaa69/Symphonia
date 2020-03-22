package com.example.symphonia.Activities.UserUI;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Management.WelcomeActivity;
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

    private void openListener(View view) {
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("user", "Listener");
        startActivity(i);
    }

    private void openArtist(View view) {
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("user", "Artist");
        startActivity(i);
    }

}
