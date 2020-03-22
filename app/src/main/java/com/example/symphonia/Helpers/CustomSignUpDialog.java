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
import com.example.symphonia.R;

public class CustomSignUpDialog {

    public void showDialog(final Activity activity, final String email, final String type) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_custom_signup);
        TextView text_view_header = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView text_view_paragraph = (TextView) dialog.findViewById(R.id.text_dialog2);
        Button btn_login = (Button) dialog.findViewById(R.id.btn_dialog_login);
        Button btn_close = (Button) dialog.findViewById(R.id.btn_dialog_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, LoginActivity.class);
                i.putExtra("user", type);
                i.putExtra("email", email);
                activity.startActivity(i);
            }
        });
        dialog.show();
    }

}