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

    private int mEnterTime = 2000;
    private int mExitTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //object from RelativeLayout that holds many layouts for animation
        //which is linked with background of this layout
        RelativeLayout relativeLayout = findViewById(R.id.layout);
        //get background of main layout(gradient_List)
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        //time for appearing one background by ms
        animationDrawable.setEnterFadeDuration(mEnterTime);
        //time for disappearing one background by ms
        animationDrawable.setExitFadeDuration(mExitTime);

        animationDrawable.start();
    }

    public void openListener(View view) {
        //opens welcome page as listener type
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("user", "Listener");
        startActivity(i);
    }

    public void openArtist(View view) {
        //opens welcome page as artist type
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("user", "Artist");
        startActivity(i);
    }

}
