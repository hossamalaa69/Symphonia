package com.example.symphonia.Activities.User_Management.ForgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;


public class ForgetPassword extends AppCompatActivity {

    private EditText email;
    private String userName;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Bundle b = getIntent().getExtras();
        type = b.getString("type");

        try {
            userName = b.getString("user");
        }catch (NullPointerException e){
            userName = "";
        }

        email = findViewById(R.id.emailInput);
        email.setText(userName);

        if(Utils.isValidEmail(userName))
            enableButton();
        else
            lockButton();

        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (Utils.isValidEmail(s.toString()))
                    enableButton();
                else
                    lockButton();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    public void enableButton() {
        Button login = findViewById(R.id.getlink);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    public void lockButton() {
        Button login = findViewById(R.id.getlink);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    public void sendMail(View view) {
        email = findViewById(R.id.emailInput);
        Intent i = new Intent(this, EmailCheck.class);
        i.putExtra("user", email.getText().toString());
        i.putExtra("type",type);
        startActivity(i);
    }

}
