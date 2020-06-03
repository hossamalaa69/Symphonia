package com.example.symphonia.Activities.User_Interface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AddAlbum extends AppCompatActivity {
    private TextView save;
    private ImageView albumImg;
    private EditText name;
    private EditText copyRights;
    private String copyRightsType;
    private String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);


        //attach views
        save=findViewById(R.id.save);
        name=findViewById(R.id.edit_text_album);
        copyRights=findViewById(R.id.copyrights);
        albumImg=findViewById(R.id.album_img);
        //handle clicks

        albumImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RestApi restApi=new RestApi();
                    BitmapDrawable drawable=(BitmapDrawable)albumImg.getDrawable();
                    Bitmap bitmap=drawable.getBitmap();
                    try {
                        image= Utils.getStringImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("image", image);
                    intent.putExtra("albumType", "album");
                    intent.putExtra("copyRights",copyRights.getText().toString());
                    intent.putExtra("copyRightsType",copyRightsType);
                    setResult(RESULT_OK, intent);
                    finish();
            }
        });
    }
    //////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                albumImg.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_p:
                if (checked)
                    copyRightsType="p";
                    break;
            case R.id.radio_c:
                if (checked)
                    copyRightsType="c";
                    break;
        }
    }
}
