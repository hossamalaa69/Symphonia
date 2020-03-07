package com.example.symphonia.Artist_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.symphonia.Listener_Register.ForgetPasswordListener;
import com.example.symphonia.R;

public class LoginArtistActivity extends AppCompatActivity {


        private EditText email;
        private EditText password;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_artist);

            email = (EditText) findViewById(R.id.emailInput);
            email.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    EditText password2 = (EditText) findViewById(R.id.password);
                    if(password2.getText().length()>=8 && s.length()>=1) {
                        enableButton();
                    }
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

            password = (EditText) findViewById(R.id.password);
            password.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    EditText email2 = (EditText) findViewById(R.id.emailInput);
                    if(email2.getText().length()>=1 && s.length()>=8) {
                        enableButton();
                    }
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
            Button login = (Button) findViewById(R.id.login);
            login.setEnabled(true);
            login.setBackgroundResource(R.drawable.btn_curved_red);
        }

        public void lockButton() {
            Button login = (Button) findViewById(R.id.login);
            login.setEnabled(false);
            login.setBackgroundResource(R.drawable.btn_curved_gray);
        }

        public void openForgetPassword(View view) {
            email = (EditText) findViewById(R.id.emailInput);
            Intent i = new Intent(this, ForgetPasswordListener.class);
            i.putExtra("user", email.getText().toString());
            startActivity(i);
        }
}