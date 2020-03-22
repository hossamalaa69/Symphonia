package com.example.symphonia.Activities.User_Management.SignUp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

import java.util.Date;

public class Step3Activity extends AppCompatActivity {

    private TextView mDateValidity;
    private String mUser;
    private String mPassword;
    private String mEmail;
    private DatePicker mDatePicker;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);

        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");
        mEmail = b.getString("email");
        mPassword = b.getString("password");

        mDateValidity = findViewById(R.id.validDate);

        mDatePicker = findViewById(R.id.datePicker1);
        mDatePicker.setMaxDate(new Date().getTime());
        mDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (year <= 1999) enableButton();
                else lockButton();
            }
        });
    }

    private void openNext(View view) {
        Intent i = new Intent(this, Step4Activity.class);
        i.putExtra("user", mUser);
        i.putExtra("email", mEmail);
        i.putExtra("password", mPassword);
        String dob = "";
        dob +=""+ mDatePicker.getDayOfMonth()+'/';
        dob +=""+ mDatePicker.getMonth()+1+'/';
        dob +=""+ mDatePicker.getYear();
        i.putExtra("DOB", dob);
        startActivity(i);
    }

    private void enableButton() {
        mDateValidity.setVisibility(View.INVISIBLE);
        Button btn_login = findViewById(R.id.next);
        btn_login.setEnabled(true);
        btn_login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    private void lockButton() {
        mDateValidity.setVisibility(View.VISIBLE);
        Button btn_login = findViewById(R.id.next);
        btn_login.setEnabled(false);
        btn_login.setBackgroundResource(R.drawable.btn_curved_gray);
    }
}
