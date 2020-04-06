package com.example.symphonia.Activities.User_Management.ForgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

public class EmailCheck extends AppCompatActivity {

    private String userName;
    private String type;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_check);

        Bundle b = getIntent().getExtras();
        userName = b.getString("user");
        type = b.getString("type");
        message = findViewById(R.id.message);
        message.append(" " + userName);
    }

    public void openGMail(View view) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        startActivity(intent);
    }
}
