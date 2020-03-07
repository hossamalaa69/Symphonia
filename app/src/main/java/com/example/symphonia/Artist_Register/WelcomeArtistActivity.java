package com.example.symphonia.Artist_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.symphonia.R;
import com.example.symphonia.SignUp_Process.SignUp1Activity;

public class WelcomeArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_welcome);
    }

    public void openLogin(View view) {
        Intent i = new Intent(this, LoginArtistActivity.class);
        startActivity(i);
    }

    public void openSignUp(View view) {
        Intent i = new Intent(this, SignUp1Activity.class);
        i.putExtra("user", "Artist");
        startActivity(i);
    }
}
