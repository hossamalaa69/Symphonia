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

    private TextView text_view_errorInput;
    private EditText edit_text_email;
    private EditText edit_text_password;
    private String mType;
    private boolean mIsValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text_view_errorInput = (TextView) findViewById(R.id.error_text);

        Bundle b = getIntent().getExtras();
        mType = b.getString("user");

        edit_text_email = findViewById(R.id.emailInput);

        try{
            String mEmail = b.getString("email");
            edit_text_email.setText(mEmail);
        } catch(Exception e) {
             edit_text_email.setText("");
        }

        edit_text_email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                EditText password2 = findViewById(R.id.password);
                if (password2.getText().length() >= 8 && Utils.isValidEmail(s.toString())) {
                    enableButton();
                    mIsValid =true;
                } else{
                        lockButton();
                        mIsValid =false;
                      }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                text_view_errorInput.setVisibility(View.INVISIBLE);
            }
        });

        edit_text_password = findViewById(R.id.password);
        edit_text_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                EditText email2 = findViewById(R.id.emailInput);
                if (Utils.isValidEmail(email2.getText().toString()) && s.length() >= 8) {
                    enableButton();
                    mIsValid =true;
                } else{
                        lockButton();
                        mIsValid =false;
                    }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                text_view_errorInput.setVisibility(View.INVISIBLE);
            }
        });

         edit_text_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
             if(actionId== EditorInfo.IME_ACTION_DONE){
                 if(mIsValid)
                     openHome(v);
                }
             return false;
            }
        });

    }

    private void enableButton() {
        Button btn_login = findViewById(R.id.login);
        btn_login.setEnabled(true);
        btn_login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    private void lockButton() {
        Button btn_login = findViewById(R.id.login);
        btn_login.setEnabled(false);
        btn_login.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    private void openHome(View view) {
        ServiceController serviceController = ServiceController.getInstance();
        if(!isOnline()){
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(this, false);
            return;
        }

        if(mType.equals("Listener")) {
            if (serviceController.logIn(this, edit_text_email.getText().toString(),
                    edit_text_password.getText().toString(), true)) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                text_view_errorInput.setVisibility(View.VISIBLE);
                text_view_errorInput.setText(R.string.wrong_password_or_email);
            }
        } else if(mType.equals("Artist")){
            if(serviceController.logIn(this, edit_text_email.getText().toString(),
                    edit_text_password.getText().toString(), false)) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                text_view_errorInput.setVisibility(View.VISIBLE);
                text_view_errorInput.setText(R.string.wrong_password_or_email);
            }
        }
    }

    private void openForget(View view) {
        edit_text_email = findViewById(R.id.emailInput);
        Intent i = new Intent(this, ForgetPasswordListenerActivity.class);
        i.putExtra("user", edit_text_email.getText().toString());
        startActivity(i);
    }

    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

}
