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

import com.example.symphonia.Activities.UserUI.AddArtistsActivity;
import com.example.symphonia.Helpers.CustomOfflineDialog;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

public class Step5Activity extends AppCompatActivity  {

    private EditText edit_text_name;
    private String mUser;
    private String mPassword;
    private String mEmail;
    private String mDOB;
    private String mGender;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up5);

        TextView text_view_link1 = findViewById(R.id.t1);
        text_view_link1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView text_view_link2 = findViewById(R.id.t2);
        text_view_link2.setMovementMethod(LinkMovementMethod.getInstance());

        Bundle b = getIntent().getExtras();
        mUser = b.getString("user");
        mPassword = b.getString("password");
        mEmail = b.getString("email");
        mDOB = b.getString("DOB");
        mGender = b.getString("gender");

        edit_text_name = findViewById(R.id.name_input);
        edit_text_name.setText(Utils.getNameFromEmail(mEmail));
        enableButton();

        edit_text_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
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

        edit_text_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    if(edit_text_name.getText().toString().length()>=1) openNext(v);
                }
                return false;
            }
        });
    }

    private void openNext(View view) {
        ServiceController serviceController = ServiceController.getInstance();
        if(!isOnline()){
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(this, false);
            return;
        }

        mName = edit_text_name.getText().toString();
        boolean userType;
        userType = mUser.equals("Listener");

        serviceController.signUp(this, userType, mEmail, mPassword, mDOB, mGender, mName);

        Intent i = new Intent(this, AddArtistsActivity.class);
        i.putExtra("newUser", "true");
        startActivity(i);
    }

    private void enableButton() {
        Button btn_login = findViewById(R.id.next);
        btn_login.setEnabled(true);
        btn_login.setBackgroundResource(R.drawable.btn_curved_white);
    }

    private void lockButton() {
        Button btn_login = findViewById(R.id.next);
        btn_login.setEnabled(false);
        btn_login.setBackgroundResource(R.drawable.btn_curved_gray);
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
