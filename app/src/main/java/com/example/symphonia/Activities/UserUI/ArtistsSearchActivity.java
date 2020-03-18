package com.example.symphonia.Activities.UserUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.symphonia.Adapters.RvListArtistSearchAdapter;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

public class ArtistsSearchActivity extends AppCompatActivity implements RvListArtistSearchAdapter.ListItemClickListener{

    private View cardSearchBar;
    private RelativeLayout searchBarFocused;
    private EditText searchEditText;
    private RecyclerView artistsList;
    private TextView emptyState;
    private TextView notFound1;
    private TextView notFound2;

    private RvListArtistSearchAdapter adapter;
    private ArrayList<Artist> searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_search);

        final ServiceController serviceController = ServiceController.getInstance();

        searchEditText = findViewById(R.id.search_edit_text);
        cardSearchBar = findViewById(R.id.search_bar);
        searchBarFocused = findViewById(R.id.search_bar_focused);
        emptyState = findViewById(R.id.artist_search_empty_state);
        notFound1 = findViewById(R.id.not_found_state_1);
        notFound2 = findViewById(R.id.not_found_state_2);

        searchResult = new ArrayList<>();
        artistsList = findViewById(R.id.rv_artists_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        artistsList.setLayoutManager(layoutManager);
        adapter = new RvListArtistSearchAdapter(searchResult, this);
        artistsList.setAdapter(adapter);

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
                Utils.hideKeyboard(ArtistsSearchActivity.this, ArtistsSearchActivity.this);

                artistsList.setVisibility(View.GONE);
                searchBarFocused.setVisibility(View.GONE);
                emptyState.setVisibility(View.VISIBLE);
                cardSearchBar.setVisibility(View.VISIBLE);
                notFound1.setVisibility(View.GONE);
                notFound2.setVisibility(View.GONE);
            }
        });

        cardSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardSearchBar.setVisibility(View.GONE);
                searchBarFocused.setVisibility(View.VISIBLE);
                artistsList.setVisibility(View.VISIBLE);
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
                if (s.toString().isEmpty()) {
                    emptyState.setVisibility(View.VISIBLE);
                    clearIcon.setVisibility(View.GONE);
                    notFound1.setVisibility(View.GONE);
                    notFound2.setVisibility(View.GONE);
                    adapter.clear();
                    adapter.notifyDataSetChanged();

                } else {
                    emptyState.setVisibility(View.GONE);
                    clearIcon.setVisibility(View.VISIBLE);

                    searchResult = serviceController.searchArtist(ArtistsSearchActivity.this, s.toString());
                    adapter.clear();
                    adapter.addAll(searchResult);
                    adapter.notifyDataSetChanged();
                    if(searchResult.isEmpty()) {
                        notFound1.setText(getString(R.string.not_found_state_1) + " \"" + s + "\"");
                        notFound1.setVisibility(View.VISIBLE);
                        notFound2.setVisibility(View.VISIBLE);
                    }
                    else {
                        notFound1.setVisibility(View.GONE);
                        notFound2.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent resultIntent = new Intent();
        Artist artist = searchResult.get(clickedItemIndex);
        resultIntent.putExtra("SelectedArtist", artist);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
