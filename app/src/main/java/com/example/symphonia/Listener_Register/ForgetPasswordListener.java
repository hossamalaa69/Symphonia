package com.example.symphonia.Listener_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.symphonia.MainActivity;
import com.example.symphonia.R;

public class ForgetPasswordListener extends AppCompatActivity {

    private EditText email;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_listener);

        Bundle b = getIntent().getExtras();
        user = b.getString("user");

        email = (EditText) findViewById(R.id.emailInput);
        email.setText(user);

        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(isValidEmail(s.toString()))
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
        Button login = (Button) findViewById(R.id.getlink);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    public void lockButton() {
        Button login = (Button) findViewById(R.id.getlink);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    public void sendMail(View view) {
        email = (EditText) findViewById(R.id.emailInput);
        Intent i = new Intent(this, EmailCheckListener.class);
        i.putExtra("user", email.getText().toString());
        startActivity(i);
    }

    /**
     * check if string is ic_email form or not
     * @param target: input string
     * @return boolean
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
