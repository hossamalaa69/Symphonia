package com.example.symphonia.Activities.User_Management;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Management.SignUp.Step1Activity;
import com.example.symphonia.Constants;
import com.example.symphonia.R;

/**
 * Activity that Welcome page that user chooses
 * his action(login, sign up and connect with facebook)
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class WelcomeActivity extends AppCompatActivity {

    /**
     * Holds user type(listener or artist)
     */
    private String mType;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //receives user types from previous activity
        Bundle b = getIntent().getExtras();
        mType = b.getString("user");

    }

    /**
     * Opens login page
     * @param view holds pressed button(Login button)
     */
    public void openLogin(View view) {
        //open activity login with user type
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("user", mType);
        startActivity(i);
    }

    /**
     * Opens sign up with first step
     * @param view holds pressed button(sign up button)
     */
    public void openSignUp1(View view) {
        //opens sign up with user type
        Intent i = new Intent(this, Step1Activity.class);
        i.putExtra("user", mType);
        startActivity(i);
    }

    public void goFacebook(View view) {

        if(!Constants.DEBUG_STATUS) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.BASE_URL + "api/v1/users/auth/facebook"));
            startActivity(intent);
        }

    }

}
