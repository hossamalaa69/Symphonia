package com.example.symphonia.Activities.User_Management.SignUp;

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

import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.Custom_Dialog_Offline;
import com.example.symphonia.Helpers.Custom_Dialog_SignUp;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

public class SignUp1 extends AppCompatActivity {

    private EditText email;
    private String user;
    private boolean type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);


        Bundle b = getIntent().getExtras();
        user = b.getString("user");


        if(user.equals("Listener"))
            type = true;
        else
            type = false;

        // Toast.makeText(this, user, Toast.LENGTH_SHORT).show();

        email = findViewById(R.id.emailInput);
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

        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    if(Utils.isValidEmail(email.getText().toString()))
                        openNext(v);
                }
                return false;
            }
        });
    }




    public void openNext(View view) {
        ServiceController serviceController = ServiceController.getInstance();

        if(!isOnline()){
            Custom_Dialog_Offline custom_dialogOffline = new Custom_Dialog_Offline();
            custom_dialogOffline.showDialog(this);
            return;
        }

        boolean isAvailable = serviceController.checkEmailAvailability(this, email.getText().toString(),type);

        if(isAvailable)
        {
            Intent i = new Intent(this, SignUp2.class);
            i.putExtra("user", user);
            i.putExtra("email",email.getText().toString());
            startActivity(i);
        } else {
            Custom_Dialog_SignUp custom_dialog = new Custom_Dialog_SignUp();
            custom_dialog.showDialog(this, email.getText().toString(), user);
        }
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

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

}
