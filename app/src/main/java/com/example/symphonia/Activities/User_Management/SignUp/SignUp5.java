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
import com.example.symphonia.Activities.UserUI.MainActivity;
import com.example.symphonia.Helpers.Custom_Dialog_Offline;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

public class SignUp5 extends AppCompatActivity  {

    private EditText name;
    private String user;
    private String password;
    private String email;
    private String dob;
    private String gender;
    private String mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up5);

        TextView t1 = findViewById(R.id.t1);
        t1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t2 = findViewById(R.id.t2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        Bundle b = getIntent().getExtras();
        user = b.getString("user");
        password = b.getString("password");
        email = b.getString("email");
        dob = b.getString("DOB");
        gender = b.getString("gender");


        name = findViewById(R.id.name_input);

        name.setText(Utils.getNameFromEmail(email));
        enableButton();

        name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1)
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

        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    if(name.getText().toString().length()>=1)
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

        mName = name.getText().toString();
        boolean mType;
        mType = user.equals("Listener");

        serviceController.signUp(this, mType, email, password, dob, gender, mName);

        Intent i = new Intent(this, AddArtistsActivity.class);
        i.putExtra("newUser", "true");
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

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

}
