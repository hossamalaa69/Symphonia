package com.example.symphonia.Helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.symphonia.R;
import com.google.android.material.snackbar.Snackbar;

import static com.example.symphonia.Helpers.Utils.convertDpToPixel;

public class SnackbarHelper {

    public static void configSnackbar(Context context, Snackbar snack, int drawable, int textColor) {
        setRoundBorders(context, snack, drawable);
        setTextColor(snack, textColor);
    }

    private static void setRoundBorders(Context context, Snackbar snackbar, int drawable) {
        snackbar.getView().setBackground(ContextCompat.getDrawable(context, drawable));
    }

    private static void setTextColor(Snackbar snackbar, int color){
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(color);
    }
}