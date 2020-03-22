package com.example.symphonia.Activities.User_Management.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

public class Step2Activity extends AppCompatActivity {

    private EditText mPassword;
    private String mUser;
    private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        //get data from previous activity
        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");
        mEmail = b.getString("email");

        //get password input text by id, then set listeners for changing text
        mPassword = findViewById(R.id.password);
        mPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //checks if text is more then 7 chars, then enable next button
                if (s.length() >= 8) enableButton();
                else lockButton();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        //set listeners for done button in keyboard
        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //checks if text is more then 7 chars, then call next step function
                    if(mPassword.getText().toString().length()>7) openNext(v);
                }
                return false;
            }
        });
    }

    public void openNext(View view) {
        //got to next step of sign up with user's data
        Intent i = new Intent(this, Step3Activity.class);
        i.putExtra("user", mUser);
        i.putExtra("email", mEmail);
        i.putExtra("password", mPassword.getText().toString());
        startActivity(i);
    }

    public void enableButton() {
        //gets (Next button) by id then makes it enabled
        Button login = findViewById(R.id.next);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    public void lockButton() {
        //gets (Next button) by id then makes it disabled
        Button login = findViewById(R.id.next);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }
}
