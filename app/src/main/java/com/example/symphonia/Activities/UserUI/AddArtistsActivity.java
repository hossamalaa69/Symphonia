package com.example.symphonia.Activities.UserUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Utils.Artist;
import com.example.symphonia.adapters.GridSpacingItemDecorationAdapter;
import com.example.symphonia.adapters.RvGridArtistsAdapter;

import java.util.ArrayList;

public class AddArtistsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artists);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Artist> artists = new ArrayList<>();

        artists.add(new Artist(R.drawable.download, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download1, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images2, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images3, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download1, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images2, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images3, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download1, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images, "Mahmoud El Esseily"));


        RecyclerView artistList = findViewById(R.id.rv_artists_grid);
        artistList.addItemDecoration(new GridSpacingItemDecorationAdapter(3, 50, true));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        artistList.setLayoutManager(layoutManager);
        RvGridArtistsAdapter adapter = new RvGridArtistsAdapter(artists);
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
