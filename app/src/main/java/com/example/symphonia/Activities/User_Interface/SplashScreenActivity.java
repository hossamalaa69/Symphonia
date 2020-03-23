package com.example.symphonia.Activities.User_Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.symphonia.R;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Activity that handles Splash screen animation
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class SplashScreenActivity extends AppCompatActivity {

    /**
     * splash screen time to appear
     */
    private int mSplashTime =1500;

    /**
     * Represents the initialization of activity
     @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creates an object of SplashScreen Library
        //makes it full screen
        //next step is start activity
        //with time assigned in mSplashTime variable
        //with background from file (background3)
        //with logo from file (ic_splash)
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(StartActivity.class)
                .withSplashTimeOut(mSplashTime)
                .withBackgroundResource(R.drawable.gradient3)
                .withLogo(R.mipmap.ic_splash_3);

        //creates view of splash screen
        View easySplashScreen = config.create();
        //set layout file
        setContentView(easySplashScreen);
    }
}
