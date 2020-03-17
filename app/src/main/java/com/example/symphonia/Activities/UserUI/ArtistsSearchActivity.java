package com.example.symphonia.Activities.UserUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.symphonia.R;

import java.lang.reflect.Field;

public class ArtistsSearchActivity extends AppCompatActivity {

    private View cardSearchBar;
    private RelativeLayout searchBarFocused;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_search);

        searchEditText = findViewById(R.id.search_edit_text);
        cardSearchBar = findViewById(R.id.search_bar);
        searchBarFocused = findViewById(R.id.search_bar_focused);

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //search here
                    return true;
                }
                return false;
            }
        });

        searchEditText.requestFocus();

        final ImageButton clearIcon = findViewById(R.id.clear);
        clearIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
            }
        });

        final ImageButton arrowBackIcon = findViewById(R.id.arrow_back);
        arrowBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearIcon.callOnClick();
                searchEditText.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                //Find the currently focused view, so we can grab the correct window token from it.
                View view = ArtistsSearchActivity.this.getCurrentFocus();
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = new View(ArtistsSearchActivity.this);
                }
                assert imm != null;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                searchBarFocused.setVisibility(View.GONE);
                cardSearchBar.setVisibility(View.VISIBLE);
            }
        });

        cardSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardSearchBar.setVisibility(View.GONE);
                searchBarFocused.setVisibility(View.VISIBLE);

                searchEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK)
                {
                    if(event.getAction() == KeyEvent.ACTION_DOWN)
                        arrowBackIcon.callOnClick();
                    return true;
                }
                return false;
            }
        });

        
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty())
                    clearIcon.setVisibility(View.GONE);
                else
                    clearIcon.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}
