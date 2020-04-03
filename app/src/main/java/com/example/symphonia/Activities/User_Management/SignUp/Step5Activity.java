package com.example.symphonia.Activities.User_Management.SignUp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Interface.AddArtistsActivity;
import com.example.symphonia.Helpers.CustomOfflineDialog;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

/**
 * Activity that handles sign up for last step when user enters his name with some validations
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class Step5Activity extends AppCompatActivity  {

    /**
     * Holds EditText for user's name input
     */
    private EditText edit_text_name;
    /**
     * holds type of user
     */
    private String mUser;
    /**
     * holds user's password
     */
    private String mPassword;
    /**
     * holds user's email
     */
    private String mEmail;
    /**
     * holds user's date of birth
     */
    private String mDOB;
    /**
     * holds user's gender
     */
    private String mGender;
    /**
     * holds user's name
     */
    private String mName;

    /**
     * Represents the initialization of activity
     @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up5);

        //makes all anchors in text views clickable
        TextView text_view_link1 = findViewById(R.id.t1);
        text_view_link1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text_view_link2 = findViewById(R.id.t2);
        text_view_link2.setMovementMethod(LinkMovementMethod.getInstance());

        //received user's data from previous activity
        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");
        mPassword = b.getString("password");
        mEmail = b.getString("email");
        mDOB = b.getString("DOB");
        mGender = b.getString("gender");

        //gets input text of user name by id
        edit_text_name = findViewById(R.id.name_input);

        //sets input text with extracted name from email entered, then enables (next button)
        edit_text_name.setText(Utils.getNameFromEmail(mEmail));
        enableButton();

        //set listeners for name input
        edit_text_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //checks if length is more than 1 char, then enables sign up button
                if (s.length() >= 1) enableButton();
                else lockButton();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        //set listeners for keyboard (Done key)
        edit_text_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //checks validity of input, then calls open next method
                    if(edit_text_name.getText().toString().length()>=1) openNext(v);
                }
                return false;
            }
        });
    }

    /**
     * opens next page of sign up
     */
    public void openNext(View view) {
        //creates object of service controller class
        ServiceController serviceController = ServiceController.getInstance();

        //checks if not online
        if(!isOnline()){
            //shows offline dialog to prevent user from continuing sign up
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(this, false);
            return;
        }

        //stores user' name and type
        mName = edit_text_name.getText().toString();
        boolean userType;
        userType = mUser.equals("Listener");

        //calls sign up function to make a new account
        serviceController.signUp(this, userType, mEmail, mPassword, mDOB, mGender, mName);

        createMail();
    }

    /**
     * enables (Next button) to go for next step of sign up
     */
    public void enableButton() {
        //gets button by id, then makes it enabled
        Button btn_login = findViewById(R.id.next);
        btn_login.setEnabled(true);
        btn_login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    /**
     * disables (Next button) to prevent user to go next step
     */
    public void lockButton() {
        //gets button by id, then makes it disabled
        Button btn_login = findViewById(R.id.next);
        btn_login.setEnabled(false);
        btn_login.setBackgroundResource(R.drawable.btn_curved_gray);
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

    public void createMail(){
        //then goes to AddArtist activity to suggest artists for user
        Intent i = new Intent(this, AddArtistsActivity.class);
        i.putExtra("newUser", "true");
        startActivity(i);
    }
}
