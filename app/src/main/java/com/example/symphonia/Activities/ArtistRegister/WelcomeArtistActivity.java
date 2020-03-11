package com.example.symphonia.Activities.ArtistRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.SignUp.SignUp1Activity;
import com.example.symphonia.R;

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
