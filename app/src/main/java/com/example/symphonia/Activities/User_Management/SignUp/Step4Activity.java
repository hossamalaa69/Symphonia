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

        //get data from previous activity
        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");
        mPassword = b.getString("password");
        mEmail = b.getString("email");
        mDOB = b.getString("DOB");
    }

    public void setMale(View view){
        //makes male button marked as pressed, then go for next activity
        view.setBackgroundResource(R.drawable.btn_curved_border_gray);
        mGender = "male";
        //makes female button as unpressed
        Button btn_female = (Button) findViewById(R.id.female);
        btn_female.setBackgroundResource(R.drawable.btn_curved_border);

        openNext();
    }

    public void setFemale(View view) {
        //makes female button marked as pressed, then go for next activity
        view.setBackgroundResource(R.drawable.btn_curved_border_gray);
        //makes male button as unpressed
        Button btn_male = (Button) findViewById(R.id.male);
        btn_male.setBackgroundResource(R.drawable.btn_curved_border);
        mGender = "female";

        openNext();
    }

    public void openNext() {
        //goes for next step with all user's data
        Intent i = new Intent(this, Step5Activity.class);
        i.putExtra("user", mUser);
        i.putExtra("email", mEmail);
        i.putExtra("password", mPassword);
        i.putExtra("DOB", mDOB);
        i.putExtra("gender", mGender);
        startActivity(i);
    }
}
