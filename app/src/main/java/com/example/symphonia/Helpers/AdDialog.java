package com.example.symphonia.Helpers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.R;

public class AdDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_custom_ad);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        setFinishOnTouchOutside(false);

        Button btn_promote = (Button) findViewById(R.id.promote_premium);
        final Button btn_dismiss = (Button) findViewById(R.id.dismiss_ad);


        //sets listener for button promote
        btn_promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdDialog.this, MainActivity.class);
                i.putExtra("go_to","premium" );
                startActivity(i);
            }
        });

        //sets listener for button promote
        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                btn_dismiss.setText(getResources().getString(R.string.dismiss)
                        +"("+ l/1000 + ")");
            }
            @Override
            public void onFinish() {
                btn_dismiss.setText(getResources().getString(R.string.dismiss));
                btn_dismiss.setEnabled(true);
                btn_dismiss.setBackgroundResource(R.drawable.btn_transparent);
            }
        }.start();

    }

    @Override
    public void onBackPressed(){ }
}
