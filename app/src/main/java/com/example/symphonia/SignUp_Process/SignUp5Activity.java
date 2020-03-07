package com.example.symphonia.SignUp_Process;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.symphonia.MainActivity;
import com.example.symphonia.R;

public class SignUp5Activity extends AppCompatActivity {

    private EditText name;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up5);

        Bundle b = getIntent().getExtras();
        user = b.getString("user");

        name = (EditText) findViewById(R.id.name_input);
        name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(s.length()>=1)
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
        Intent i = new Intent(this, MainActivity.class);
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    public void enableButton() {
        Button login = (Button) findViewById(R.id.next);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    public void lockButton() {
        Button login = (Button) findViewById(R.id.next);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }
}
