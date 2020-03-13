package com.example.symphonia.Service;

import android.content.Context;

public interface APIs {

    boolean logInAsListener(Context context, String username, String password);

    boolean logInAsArtist(Context context, String username, String password);

}
