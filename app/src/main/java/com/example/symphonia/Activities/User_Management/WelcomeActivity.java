package com.example.symphonia.Activities.User_Management;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Activities.User_Management.SignUp.Step1Activity;
import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

/**
 * Activity that Welcome page that user chooses
 * his action(login, sign up and connect with facebook)
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class WelcomeActivity extends AppCompatActivity {

    /**
     * Holds user type(listener or artist)
     */
    private String mType;


    private LoginButton loginButton;
    private CallbackManager callbackManager;

    /**
     * Represents the initialization of activity
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //receives user types from previous activity
        Bundle b = getIntent().getExtras();
        mType = b.getString("user");

        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String token = loginResult.getAccessToken().getToken();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken()
                        , new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                    getUserInfo(object);
                            }
                        });
            }

            @Override
            public void onCancel() {
                cancelLogin();
            }

            @Override
            public void onError(FacebookException error) {
                ErrorMessage();
            }
        });
    }

    public void getUserInfo(JSONObject object){

    }

    public void cancelLogin(){
        Toast.makeText(this,"Failed login",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void ErrorMessage(){
        Toast.makeText(this,"Error login",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void showToken(String token){
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Opens login page
     * @param view holds pressed button(Login button)
     */
    public void openLogin(View view) {
        //open activity login with user type
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("user", mType);
        startActivity(i);
    }

    /**
     * Opens sign up with first step
     * @param view holds pressed button(sign up button)
     */
    public void openSignUp1(View view) {
        //opens sign up with user type
        Intent i = new Intent(this, Step1Activity.class);
        i.putExtra("user", mType);
        startActivity(i);
    }

    public void goFacebook(View view) {

        if(!Constants.DEBUG_STATUS) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.BASE_URL + "api/v1/users/auth/facebook"));
            startActivity(intent);
        }

    }

}
