package com.example.symphonia.Activities.User_Interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Management.WelcomeActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.User;
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

        SharedPreferences sharedPref= getSharedPreferences("LoginPref", 0);
        String token = sharedPref.getString("token","");
        String id = sharedPref.getString("id","");
        String name = sharedPref.getString("name", "");
        String email = sharedPref.getString("email", "");

        boolean type = sharedPref.getBoolean("type",true);
        boolean premium = sharedPref.getBoolean("premium",true);

        if(!(token.equals("")))
        {
            Constants.currentToken = token;
            Constants.currentUser = new User(email,id,name,type,premium);

            Toast.makeText(this,Constants.currentUser.getmEmail(),Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"token: "+Constants.currentToken,Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

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
