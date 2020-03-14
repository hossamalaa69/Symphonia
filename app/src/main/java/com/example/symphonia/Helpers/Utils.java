package com.example.symphonia.Helpers;

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


    public int add2(int x, int y){
        return x+y;
    }


}
