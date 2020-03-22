package com.example.symphonia.Activities.UserUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.symphonia.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    private int mSplashTime =1500;

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
                .withBackgroundResource(R.drawable.background3)
                .withLogo(R.mipmap.ic_splash);

        //creates view of splash screen
        View easySplashScreen = config.create();
        //set layout file
        setContentView(easySplashScreen);
    }
}
