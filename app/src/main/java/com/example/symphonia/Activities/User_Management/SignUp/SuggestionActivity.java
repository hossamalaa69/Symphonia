package com.example.symphonia.Activities.User_Management.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.symphonia.Activities.UserUI.MainActivity;
import com.example.symphonia.R;

public class SuggestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artists);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
