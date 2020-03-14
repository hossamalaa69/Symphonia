package com.example.symphonia.Service;

import android.content.Context;

import static com.example.symphonia.Constants.debug;

public class ServiceController {

    private final APIs mSupplier;

    private static final ServiceController restClient = new ServiceController();


    private ServiceController() {
        if (debug) {
            mSupplier = new MockService();
        }
    }

    public static ServiceController getInstance() {
        return restClient;
    }

    public boolean logIn(Context context, String username, String password, boolean mType) {
        return mSupplier.logIn(context, username, password, mType);
    }

}
