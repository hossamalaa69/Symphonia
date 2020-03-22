package com.example.symphonia.Activities.User_Management;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.symphonia.Activities.User_Management.ListenerPages.ForgetPasswordListenerActivity;
import com.example.symphonia.Activities.UserUI.MainActivity;
import com.example.symphonia.Helpers.CustomOfflineDialog;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;


public class LoginActivity extends AppCompatActivity {

    private TextView errorInput;
    private EditText email;
    private EditText password;
    private String type;
    boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        errorInput = (TextView) findViewById(R.id.error_text);

        Bundle b = getIntent().getExtras();
        type = b.getString("user");

        email = findViewById(R.id.emailInput);

        try{
            String mEmail = b.getString("email");
            email.setText(mEmail);
        } catch(Exception e) {
             email.setText("");
        }

        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                EditText password2 = findViewById(R.id.password);
                if (password2.getText().length() >= 8 && Utils.isValidEmail(s.toString())) {
                    enableButton();
                    isValid=true;
                } else{
                        lockButton();
                        isValid=false;
                      }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                errorInput.setVisibility(View.INVISIBLE);
            }
        });

        password = findViewById(R.id.password);
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                EditText email2 = findViewById(R.id.emailInput);
                if (Utils.isValidEmail(email2.getText().toString()) && s.length() >= 8) {
                    enableButton();
                    isValid=true;
                } else{
                        lockButton();
                        isValid=false;
                    }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                errorInput.setVisibility(View.INVISIBLE);
            }
        });


            password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    if(isValid)
                        openHome(v);
                }
                return false;
            }
        });
    }

    public void enableButton() {
        Button login = findViewById(R.id.login);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    public void lockButton() {
        Button login = findViewById(R.id.login);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    public void openHome(View view) {

        ServiceController serviceController = ServiceController.getInstance();

        if(!isOnline()){
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(this, false);
            return;
        }

        if(type.equals("Listener")) {
            if (serviceController.logIn(this, email.getText().toString(),
                    password.getText().toString(), true)) {

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                errorInput.setVisibility(View.VISIBLE);
                errorInput.setText(R.string.wrong_password_or_email);
            }
        }

        else if(type.equals("Artist")){
            if(serviceController.logIn(this, email.getText().toString(),
                    password.getText().toString(), false)) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
            else {
                errorInput.setVisibility(View.VISIBLE);
                errorInput.setText(R.string.wrong_password_or_email);
            }
        }
    }

    public void openForget(View view) {
        email = findViewById(R.id.emailInput);
        Intent i = new Intent(this, ForgetPasswordListenerActivity.class);
        i.putExtra("user", email.getText().toString());
        startActivity(i);
    }


    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

}
