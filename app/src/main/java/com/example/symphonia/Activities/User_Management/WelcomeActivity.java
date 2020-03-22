package com.example.symphonia.Activities.User_Management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Management.SignUp.Step1Activity;
import com.example.symphonia.R;

public class WelcomeActivity extends AppCompatActivity {

    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //receives user types from previous activity
        Bundle b = getIntent().getExtras();
        mType = b.getString("user");

    }

    public void openLogin(View view) {
        //open activity login with user type
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("user", mType);
        startActivity(i);
    }

    public void openSignUp1(View view) {
        //opens sign up with user type
        Intent i = new Intent(this, Step1Activity.class);
        i.putExtra("user", mType);
        startActivity(i);
    }
}
