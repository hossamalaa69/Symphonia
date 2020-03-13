package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.User;

public class MockService implements APIs {

    @Override
    public boolean logInAsListener(Context context, String username, String password) {

        if ( (username.equals("user1") || username.equals("user@symphonia.com")) && password.equals("12345678") ) {
            Constants.mToken = "token1";
            Constants.user=new User(username);
            return true;
        }
        return false;
    }

    @Override
    public boolean logInAsArtist(Context context, String username, String password) {
        if ( (username.equals("Artist1") || username.equals("artist@symphonia.com")) && password.equals("123456789") ) {
            Constants.mToken = "token2";
            Constants.user=new User(username);
            return true;
        }
        return false;
    }
}
