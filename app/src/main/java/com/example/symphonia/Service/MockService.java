package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.User;

public class MockService implements APIs {

    @Override
    public boolean logIn(Context context, String username, String password, boolean mType) {

        if ( (username.equals("user1") || username.equals("user@symphonia.com")) && password.equals("12345678") ) {
            Constants.mToken = "token1";
            Constants.user=new User(username, mType);
            return true;
        }
        return false;
    }

}
