package com.example.symphonia.Activities.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

public class SignUp4 extends AppCompatActivity {

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);

        Bundle b = getIntent().getExtras();
        user = b.getString("user");
    }

    public void openNext(View view) {
        Intent i = new Intent(this, SignUp5.class);
        i.putExtra("user", user);
        startActivity(i);
    }
}
