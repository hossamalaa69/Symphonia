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

import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Activities.User_Management.ListenerPages.ForgetPasswordListenerActivity;
import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.CustomOfflineDialog;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

/**
 * Activity that handles login for any type of users with some validations
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity implements RestApi.updateUiLogin {

    @Override
    public void updateUiLoginSuccess() {
        successLogin();
    }

    @Override
    public void updateUiLoginFail() {
        failedLogin();
    }

    /**
     * Holds textView that shows user that combination of input is wrong
     */
    private TextView text_view_errorInput;
    /**
     * Holds editText that contains email input
     */
    private EditText edit_text_email;
    /**
     * Holds editText that contains password input
     */
    private EditText edit_text_password;
    /**
     * holds type of user (Listener of artist)
     */
    private String mType;
    /**
     * holds if data is valid or not
     */
    private boolean mIsValid;

    /**
     * Represents the initialization of activity
     @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btn_login = findViewById(R.id.login);
        btn_login.setText(getResources().getString(R.string.log_in));
        //get text view by id, which shows if password or email or both are wrong
        text_view_errorInput = (TextView) findViewById(R.id.error_text);

        //gets user type from previous activity
        Bundle b = getIntent().getExtras();
        mType = b.getString("user");

        //gets email input text by id
        edit_text_email = findViewById(R.id.emailInput);

        //checks if it comes from welcome activity(without email)
        // or from sign up activity (with email)
        try{
            //if it's from sign up activity, then sets input with received email
            String mEmail = b.getString("email");
            edit_text_email.setText(mEmail);
        } catch(Exception e) {
             //if it's from welcome activity, then makes it empty
             edit_text_email.setText("");
        }

        //set listeners for email input for changing text
        edit_text_email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //gets password inputText by id
                EditText password2 = findViewById(R.id.password);
                //checks if email is valid and password is more than 7 chars
                if (password2.getText().length() >= 8 && Utils.isValidEmail(s.toString())) {
                    enableButton();
                    mIsValid =true;
                } else {
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

        //gets password inputText by id
        edit_text_password = findViewById(R.id.password);
        //set listeners for password input for changing text
        edit_text_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //gets email input text by id
                EditText email2 = findViewById(R.id.emailInput);
                //checks if email is valid and password is more than 7 chars
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

        //sets listener for password to check keyboard keys
         edit_text_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
             if(actionId== EditorInfo.IME_ACTION_DONE){
                 //if key done is pressed and inputs are valid, then go next
                 if(mIsValid)
                     openHome(v);
                }
             return false;
            }
        });

    }

    /**
     * enables (Login button) to go for home page
     */
    public void enableButton() {
        //gets button by id, then makes it enabled
        Button btn_login = findViewById(R.id.login);
        btn_login.setEnabled(true);
        btn_login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    /**
     * disables (Login button) to prevent user to go login
     */
    public void lockButton() {
        //gets button by id, then makes it locked
        Button btn_login = findViewById(R.id.login);
        btn_login.setEnabled(false);
        btn_login.setBackgroundResource(R.drawable.btn_curved_gray);
    }

    /**
     * opens home page if pressed with some validations
     * @param view holds pressed button(Login)
     */
    public void openHome(View view) {
        //creates object of service controller class
        ServiceController serviceController = ServiceController.getInstance();

        //checks if not online
        if(!isOnline()){
            //shows offline dialog to prevent user from continuing sign up
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(this, false);
            return;
        }

        Button btn_login = findViewById(R.id.login);
        btn_login.setText(getResources().getString(R.string.logging_in));
        //boolean variable to get user type
        boolean userType = mType.equals("Listener");
        //calls function login with the valid data from inputs
        if(Constants.DEBUG_STATUS){
            if (serviceController.logIn(this, edit_text_email.getText().toString(),
                        edit_text_password.getText().toString(), userType)) {
                //if email and password are right, then go to home page
                successLogin();
            } else {
                //if email and password are wrong, then make error textView
                failedLogin();
            }
        }
        else
            serviceController.logIn(this,edit_text_email.getText().toString()
                    ,edit_text_password.getText().toString(),userType);
    }

    /**
     * Opens to user page of forget password, sends his entered email to it
     * @param view holds pressed button(forget password button)
     */
    public void openForget(View view) {
        //gets email from input text, then send it forget password activity
        edit_text_email = findViewById(R.id.emailInput);
        Intent i = new Intent(this, ForgetPasswordListenerActivity.class);
        i.putExtra("user", edit_text_email.getText().toString());
        startActivity(i);
    }

    /**
     * Checks if internet is connected
     * @return returns true if internet is connected
     */
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

    public void successLogin(){

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void failedLogin(){
        Button btn_login = findViewById(R.id.login);
        btn_login.setText(getResources().getString(R.string.log_in));
        //visible to inform that user's input are invalid
        text_view_errorInput.setVisibility(View.VISIBLE);
        text_view_errorInput.setText(R.string.wrong_password_or_email);
    }

}
