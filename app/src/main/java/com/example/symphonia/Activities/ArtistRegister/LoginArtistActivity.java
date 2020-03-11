package com.example.symphonia.Activities.ArtistRegister;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.ListenerRegister.ForgetPasswordListenerActivity;
import com.example.symphonia.R;

public class LoginArtistActivity extends AppCompatActivity {


    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_artist);

        email = findViewById(R.id.emailInput);
        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                EditText password2 = findViewById(R.id.password);
                if (password2.getText().length() >= 8 && s.length() >= 1) {
                    enableButton();
                } else
                    lockButton();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        password = findViewById(R.id.password);
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                EditText email2 = findViewById(R.id.emailInput);
                if (email2.getText().length() >= 1 && s.length() >= 8) {
                    enableButton();
                } else
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
        Button login = findViewById(R.id.login);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_red);
    }

    public void lockButton() {
        Button login = findViewById(R.id.login);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    public void openForgetPassword(View view) {
        email = findViewById(R.id.emailInput);
        Intent i = new Intent(this, ForgetPasswordListenerActivity.class);
        i.putExtra("user", email.getText().toString());
        startActivity(i);
    }
}