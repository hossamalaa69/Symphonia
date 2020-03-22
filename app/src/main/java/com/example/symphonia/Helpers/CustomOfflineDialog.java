package com.example.symphonia.Helpers;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.symphonia.R;

public class CustomOfflineDialog {

    private  boolean mIsArtist = false;

    public void showDialog(final Activity activity, boolean isArtist){
        mIsArtist = isArtist;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_custom_offline);
        TextView text_view_header = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView text_view_paragraph = (TextView) dialog.findViewById(R.id.text_dialog2);
        Button btn_dialog = (Button) dialog.findViewById(R.id.btn_dialog);
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsArtist){
                    dialog.dismiss();
                    activity.finish();
                }
                else dialog.dismiss();
            }
        });
        dialog.show();
    }
}