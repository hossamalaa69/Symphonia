package com.example.symphonia.Activities.User_Management.Redirect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.AdDialog;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

public class PaymentActivity extends AppCompatActivity implements RestApi.updateUIPromotePremium
        ,RestApi.updateUICheckPremium {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        try{
            //get data from previous activity
            Bundle b = getIntent().getExtras();
            String received = b.getString("request");
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.promotePremium(this,null,Constants.currentToken);
        }catch (NullPointerException e){
            handleIntent();
        }
    }
    private void handleIntent() {
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        if(appLinkData!=null){
            String newToken = appLinkData.getLastPathSegment();
            ServiceController serviceController = ServiceController.getInstance();

        } else{
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent();
    }


    @Override
    public void updateUIPromotePremiumSuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("go_to","premium" );
        startActivity(i);
    }

    @Override
    public void updateUIPromotePremiumFailed() {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("go_to","premium" );
        startActivity(i);
    }

    @Override
    public void updateUICheckPremiumSuccess() {
        Toast.makeText(this, "Congratulations, you're premium user now",Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("go_to","premium" );
        startActivity(i);
    }

    @Override
    public void updateUICheckPremiumFailed() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("go_to","premium" );
        startActivity(i);
    }
}