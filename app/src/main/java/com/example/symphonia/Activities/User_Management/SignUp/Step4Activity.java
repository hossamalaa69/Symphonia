package com.example.symphonia.Activities.User_Management.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

public class Step4Activity extends AppCompatActivity {

    private String mUser;
    private String mPassword;
    private String mEmail;
    private String mDOB;
    private String mGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);

        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");
        mPassword = b.getString("password");
        mEmail = b.getString("email");
        mDOB = b.getString("DOB");
    }

    private void setMale(View view){
        view.setBackgroundResource(R.drawable.btn_curved_border_gray);
        mGender = "male";
        Button btn_female = (Button) findViewById(R.id.female);
        btn_female.setBackgroundResource(R.drawable.btn_curved_border);
        openNext();
    }

    private void setFemale(View view) {
        view.setBackgroundResource(R.drawable.btn_curved_border_gray);
        Button btn_male = (Button) findViewById(R.id.male);
        btn_male.setBackgroundResource(R.drawable.btn_curved_border);
        mGender = "female";
        openNext();
    }

    private void openNext() {
        Intent i = new Intent(this, Step5Activity.class);
        i.putExtra("user", mUser);
        i.putExtra("email", mEmail);
        i.putExtra("password", mPassword);
        i.putExtra("DOB", mDOB);
        i.putExtra("gender", mGender);
        startActivity(i);
    }
}
