package com.example.symphonia.Activities.UserUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Adapters.GridSpacingItemDecorationAdapter;
import com.example.symphonia.Adapters.RvGridArtistsAdapter;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

public class AddArtistsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artists);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ServiceController serviceController = ServiceController.getInstance();
        ArrayList<Artist> mRecommendedArtists = serviceController.getRecommendedArtists(Constants.user.isListenerType(), Constants.mToken, 20);


        RecyclerView artistList = findViewById(R.id.rv_artists_grid);
        artistList.addItemDecoration(new GridSpacingItemDecorationAdapter(mRecommendedArtists.size(),3, 50, true));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        artistList.setLayoutManager(layoutManager);
        RvGridArtistsAdapter adapter = new RvGridArtistsAdapter(mRecommendedArtists);
        artistList.setAdapter(adapter);

        Button doneButton = findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View searchBar = findViewById(R.id.search_bar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ArtistsSearchActivityIntent = new Intent(AddArtistsActivity.this, ArtistsSearchActivity.class);
                startActivity(ArtistsSearchActivityIntent);
            }
        });
    }
}
