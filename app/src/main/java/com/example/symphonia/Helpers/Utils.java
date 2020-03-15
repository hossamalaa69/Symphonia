package com.example.symphonia.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Utils {

    /**
     * check if string is email form or not
     *
     * @param target: input string
     * @return boolean
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static Bitmap convertToBitmap(int mImageResourceId)
    {
        return BitmapFactory.decodeResource(App.getContext().getResources(), mImageResourceId);
    }

    public int add2(int x, int y){
        return x+y;
    }


}
