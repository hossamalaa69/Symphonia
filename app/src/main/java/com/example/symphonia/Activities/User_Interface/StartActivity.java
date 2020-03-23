package com.example.symphonia.Activities.User_Interface;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Management.WelcomeActivity;
import com.example.symphonia.R;

/**
 * Activity that handles Start page with animations
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class StartActivity extends AppCompatActivity {

    /**
     * time to enter fade of animation
     */
    private int mEnterTime = 2000;
    /**
     * time to exit fade of animation
     */
    private int mExitTime = 2000;

    /**
     * Represents the initialization of activity
     @param savedInstanceState represents received data from other activities
     */
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

    /**
     * opens welcome page as user
     * @param view holds clicked button
     */
    public void openListener(View view) {
        //opens welcome page as listener type
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("user", "Listener");
        startActivity(i);
    }

    /**
     * opens welcome page as artist
     * @param view holds clicked button
     */
    public void openArtist(View view) {
        //opens welcome page as artist type
        Intent i = new Intent(this, WelcomeActivity.class);
        i.putExtra("user", "Artist");
        startActivity(i);
    }

}
