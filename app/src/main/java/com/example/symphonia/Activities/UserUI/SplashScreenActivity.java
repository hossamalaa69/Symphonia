package com.example.symphonia.Activities.UserUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.symphonia.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(StartActivity.class)
                .withSplashTimeOut(1500)
                .withBackgroundResource(R.drawable.background3)
                .withLogo(R.mipmap.ic_splash);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
