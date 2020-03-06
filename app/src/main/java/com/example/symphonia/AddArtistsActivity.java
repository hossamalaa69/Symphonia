package com.example.symphonia;

import android.os.Bundle;

import com.example.symphonia.adapters.GridSpacingItemDecoration;
import com.example.symphonia.adapters.RvGridArtistsAdapter;
import com.example.symphonia.data.Artist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AddArtistsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        artistList.addItemDecoration(new GridSpacingItemDecoration(3, 50, true));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        artistList.setLayoutManager(layoutManager);
        RvGridArtistsAdapter adapter = new RvGridArtistsAdapter(artists);
        artistList.setAdapter(adapter);

        Button doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
