package com.example.symphonia.Helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Fragments_and_models.premium.PremiumFragment;
import com.example.symphonia.R;

public class AdDialog {

    /**
     * function that shows and initializes dialog
     * @param activity activity that calls this dialog
     */
    public void showDialog(final Activity activity){

        //sets dialog activity to be shown in
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //sets background with layout file
        dialog.setContentView(R.layout.dialog_custom_ad);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        Button btn_promote = (Button) dialog.findViewById(R.id.promote_premium);
        Button btn_dismiss = (Button) dialog.findViewById(R.id.dismiss_ad);


        //sets listener for button promote
        btn_promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MainActivity.class);
                i.putExtra("go_to","premium" );
                activity.startActivity(i);
            }
        });

        //sets listener for button promote
        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }


}
