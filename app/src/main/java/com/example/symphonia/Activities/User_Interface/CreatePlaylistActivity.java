package com.example.symphonia.Activities.User_Interface;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;

/**
 * Starts the first step of creating new playlist
 * by taking the name of the playlist from the user
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class CreatePlaylistActivity extends AppCompatActivity {

    /**
     * initialize the ui and the edit text for the user
     * to start typing the name of the playlist
     * @param savedInstanceState saved data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        final EditText editText = findViewById(R.id.playlist_name_edit_text);
        editText.requestFocus();

        // open the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        final Button createButton = findViewById(R.id.button_create);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().length() != 0)
                    createButton.setText(R.string.create_playlist_button);
                else
                    createButton.setText(R.string.skip_playlist_name);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * calls when the user go to another activity
     * it simply hides the keyboard from the screen
     * in case if the user didn't close it
     */
    @Override
    protected void onPause() {
        Utils.hideKeyboard(this, this);
        super.onPause();
    }


}
