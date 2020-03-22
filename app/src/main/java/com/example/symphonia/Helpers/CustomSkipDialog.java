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

import com.example.symphonia.Activities.UserUI.MainActivity;
import com.example.symphonia.R;

public class CustomSkipDialog {
    public void showDialog(final Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_custom_skip);
        TextView text_view_header = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView text_view_paragraph = (TextView) dialog.findViewById(R.id.text_dialog2);
        Button btn_skip = (Button) dialog.findViewById(R.id.btn_skip);
        Button btn_continue = (Button) dialog.findViewById(R.id.btn_cont);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MainActivity.class);
                activity.startActivity(i);
            }
        });
        dialog.show();
    }

}