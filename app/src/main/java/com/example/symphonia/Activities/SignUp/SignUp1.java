package com.example.symphonia.Activities.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

public class SignUp1 extends AppCompatActivity {

    private EditText email;
    private String user;

    /**
     * check if string is email form or not
     *
     * @param target: input string
     * @return boolean
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        Bundle b = getIntent().getExtras();
        user = b.getString("user");

        // Toast.makeText(this, user, Toast.LENGTH_SHORT).show();

        email = findViewById(R.id.emailInput);
        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (isValidEmail(s.toString()))
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

    public void openNext(View view) {
        Intent i = new Intent(this, SignUp2.class);
        i.putExtra("user", user);
        startActivity(i);
    }

    public void enableButton() {
        Button login = findViewById(R.id.next);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    public void lockButton() {
        Button login = findViewById(R.id.next);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }
}
