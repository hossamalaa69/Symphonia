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

        //sets dialog activity to be shown in
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //sets background with layout file
        dialog.setContentView(R.layout.dialog_custom_offline);

        //gets text views and button by ids in layout file to be shown
        TextView text_view_header = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView text_view_paragraph = (TextView) dialog.findViewById(R.id.text_dialog2);
        Button btn_dialog = (Button) dialog.findViewById(R.id.btn_dialog);

        //sets listener for button
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if activity is AddArtist, then finish this activity
                if(mIsArtist){
                    dialog.dismiss();
                    activity.finish();
                }
                //if another activity, just hide the dialog
                else dialog.dismiss();
            }
        });
        dialog.show();
    }
}