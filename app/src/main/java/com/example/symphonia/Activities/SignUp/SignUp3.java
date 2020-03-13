package com.example.symphonia.Activities.SignUp;

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

public class SignUp3 extends AppCompatActivity {

    private TextView dateValidity;
    private String user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);

        Bundle b = getIntent().getExtras();
        user = b.getString("user");

        dateValidity = findViewById(R.id.validDate);

        DatePicker datePicker = findViewById(R.id.datePicker1);
        datePicker.setMaxDate(new Date().getTime());
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (year <= 1999)
                    enableButton();
                else
                    lockButton();
            }
        });
    }

    public void openNext(View view) {
        Intent i = new Intent(this, SignUp4.class);
        i.putExtra("user", user);
        startActivity(i);
    }

    public void enableButton() {
        dateValidity.setVisibility(View.INVISIBLE);
        Button login = findViewById(R.id.next);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    public void lockButton() {
        dateValidity.setVisibility(View.VISIBLE);
        Button login = findViewById(R.id.next);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }
}
