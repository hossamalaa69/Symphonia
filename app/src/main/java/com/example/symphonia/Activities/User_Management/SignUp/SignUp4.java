package com.example.symphonia.Activities.User_Management.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

public class SignUp4 extends AppCompatActivity {

    private String user;
    private String password;
    private String email;
    private String dob;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);

        Bundle b = getIntent().getExtras();
        user = b.getString("user");
        password = b.getString("password");
        email = b.getString("email");
        dob = b.getString("DOB");
    }

    public void setMale(View view){
        view.setBackgroundResource(R.drawable.btn_curved_border_gray);
        gender = "male";
        Button female = (Button) findViewById(R.id.female);
        female.setBackgroundResource(R.drawable.btn_curved_border);
        openNext();
    }
    public void setFemale(View view) {
        view.setBackgroundResource(R.drawable.btn_curved_border_gray);
        Button male = (Button) findViewById(R.id.male);
        male.setBackgroundResource(R.drawable.btn_curved_border);
        gender = "female";
        openNext();
    }

    public void openNext() {
        Intent i = new Intent(this, SignUp5.class);
        i.putExtra("user", user);
        i.putExtra("email", email);
        i.putExtra("password", password);
        i.putExtra("DOB", dob);
        i.putExtra("gender", gender);
        startActivity(i);
    }
}
