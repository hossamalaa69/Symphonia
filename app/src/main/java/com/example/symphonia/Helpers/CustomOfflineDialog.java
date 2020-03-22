package com.example.symphonia.Helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.symphonia.Activities.User_Management.LoginActivity;
import com.example.symphonia.Fragments_and_models.library.LibraryArtistsFragment;
import com.example.symphonia.R;

public class CustomOfflineDialog {

    private  boolean isAddArtist = false;

    public void showDialog(final Activity activity, boolean isArtist){
        isAddArtist = isArtist;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_offline);
        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView text2 = (TextView) dialog.findViewById(R.id.text_dialog2);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAddArtist){
                    dialog.dismiss();
                    activity.finish();
                }
                else
                    dialog.dismiss();
            }
        });
        dialog.show();
    }
}