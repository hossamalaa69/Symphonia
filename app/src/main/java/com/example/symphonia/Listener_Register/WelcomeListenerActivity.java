package com.example.symphonia.Listener_Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

public class WelcomeListenerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener_welcome);
    }

    public void openLogin(View view) {
        Intent i = new Intent(this, LoginListenerActivity.class);
        startActivity(i);
    }

    public void openSignUp1(View view) {
        Intent i = new Intent(this, SignUp1Activity.class);
        startActivity(i);
    }
}
