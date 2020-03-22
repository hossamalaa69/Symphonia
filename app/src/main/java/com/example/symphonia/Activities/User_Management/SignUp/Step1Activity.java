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

import com.example.symphonia.Helpers.CustomOfflineDialog;
import com.example.symphonia.Helpers.CustomSignUpDialog;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

public class Step1Activity extends AppCompatActivity {

    private EditText mEmail;
    private String mUser;
    private boolean mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        //gets data from previous activity
        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");

        //checks user type
        if(mUser.equals("Listener")) mType = true;
        else mType = false;

        //sets listeners for email input
        mEmail = findViewById(R.id.emailInput);
        mEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //checks if valid email form, then enable button
                if (Utils.isValidEmail(s.toString())) enableButton();
                else lockButton();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        //sets listener for keyboard if pressed done
        mEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    if(Utils.isValidEmail(mEmail.getText().toString())) openNext(v);
                }
                return false;
            }
        });
    }

    public void openNext(View view) {
        //creates object of serviceController
        ServiceController serviceController = ServiceController.getInstance();

        //checks if online or not
        if(!isOnline()){
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(this, false);
            return;
        }

        //stores if email is signed before
        boolean isAvailable =
                serviceController.checkEmailAvailability(this, mEmail.getText().toString(), mType);

        //if email is not signed before, then go to next step with user data
        if(isAvailable)
        {
            Intent i = new Intent(this, Step2Activity.class);
            i.putExtra("user", mUser);
            i.putExtra("email", mEmail.getText().toString());
            startActivity(i);
        } else {
            //shows dialog that informs that email is signed before
            CustomSignUpDialog custom_dialog = new CustomSignUpDialog();
            custom_dialog.showDialog(this, mEmail.getText().toString(), mUser);
        }
    }

    public void enableButton() {
        //gets button by id, then makes it enabled
        Button login = findViewById(R.id.next);
        login.setEnabled(true);
        login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    public void lockButton() {
        //gets button by id, then makes it disabled
        Button login = findViewById(R.id.next);
        login.setEnabled(false);
        login.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    public boolean isOnline() {
        //accesses connection service of mobile
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            //return true if internet is connected
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }
}
