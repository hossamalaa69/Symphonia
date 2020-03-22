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

import com.example.symphonia.Activities.UserUI.AddArtistsActivity;
import com.example.symphonia.Activities.UserUI.MainActivity;
import com.example.symphonia.Activities.User_Management.LoginActivity;
import com.example.symphonia.R;

public class Custom_Dialog_Skip {
    public void showDialog(final Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_skip);
        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView text2 = (TextView) dialog.findViewById(R.id.text_dialog2);
        Button dialogButton1 = (Button) dialog.findViewById(R.id.btn_skip);
        Button dialogButton2 = (Button) dialog.findViewById(R.id.btn_cont);

        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MainActivity.class);
                activity.startActivity(i);
            }
        });

        dialog.show();
    }

}