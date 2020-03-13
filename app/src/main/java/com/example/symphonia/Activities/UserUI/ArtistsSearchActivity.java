package com.example.symphonia.Activities.UserUI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.symphonia.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.lang.reflect.Field;

public class ArtistsSearchActivity extends AppCompatActivity {

    MaterialSearchBar searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_search);
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.openSearch();
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(enabled)
                {
                    searchBar.setBackgroundColor(getResources().getColor(R.color.artists_search_bar_background));
                }
                else
                {
                    searchBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        try {
            final Field placeHolder = searchBar.getClass().getDeclaredField("placeHolder");
            placeHolder.setAccessible(true);
            final TextView textView = (TextView) placeHolder.get(searchBar);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
