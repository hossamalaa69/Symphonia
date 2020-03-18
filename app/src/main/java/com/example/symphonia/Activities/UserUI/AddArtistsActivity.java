package com.example.symphonia.Activities.UserUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
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

    private static final int STATIC_INTEGER_VALUE = 1;
    private Artist selectedArtist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artists);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ServiceController serviceController = ServiceController.getInstance();
        ArrayList<Artist> mRecommendedArtists = serviceController.getRecommendedArtists(Constants.user.isListenerType(), Constants.mToken, 20);
        final ArrayList<Artist> selectedArtists = new ArrayList<>();

        RecyclerView artistList = findViewById(R.id.rv_artists_grid);
        artistList.addItemDecoration(new GridSpacingItemDecorationAdapter(mRecommendedArtists.size(),3, 50, true));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        artistList.setLayoutManager(layoutManager);
        RvGridArtistsAdapter adapter = new RvGridArtistsAdapter(mRecommendedArtists, selectedArtists);
        artistList.setAdapter(adapter);

        Button doneButton = findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Artist artist: selectedArtists) {
                    serviceController.followArtistOrUser(Constants.user.isListenerType(), Constants.mToken, artist.getId());
                }
                finish();
            }
        });

        View searchBar = findViewById(R.id.search_bar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ArtistsSearchActivityIntent = new Intent(AddArtistsActivity.this, ArtistsSearchActivity.class);
                startActivityForResult(ArtistsSearchActivityIntent, STATIC_INTEGER_VALUE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case STATIC_INTEGER_VALUE:{
                if (resultCode == Activity.RESULT_OK) {
                    assert data != null;
                    selectedArtist = (Artist)data.getSerializableExtra("SelectedArtist");
                }
                break;
            }

        }
    }
}
