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

public class AddArtistsActivity extends AppCompatActivity implements RvGridArtistsAdapter.ListItemClickListener {

    private static final int STATIC_INTEGER_VALUE = 1;
    ServiceController serviceController;
    ArrayList<Artist> mRecommendedArtists;
    RvGridArtistsAdapter adapter;
    RecyclerView artistList;
    GridSpacingItemDecorationAdapter mItemDecoration;
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artists);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        serviceController = ServiceController.getInstance();

        // will be modified
        mRecommendedArtists = new ArrayList<>();
        ArrayList<Artist> returnedArtists = serviceController.getRecommendedArtists(Constants.user.isListenerType(), Constants.mToken, 20);
        for (Artist artist : returnedArtists) {
            if(!serviceController.isFollowing(Constants.user.isListenerType(), Constants.mToken
                    , artist.getId()))
               mRecommendedArtists.add(artist);
        }

        artistList = findViewById(R.id.rv_artists_grid);
        mItemDecoration = new GridSpacingItemDecorationAdapter(mRecommendedArtists.size()
                ,3, 50, true);

        artistList.addItemDecoration(mItemDecoration);
        layoutManager = new GridLayoutManager(this, 3);
        artistList.setLayoutManager(layoutManager);
        adapter = new RvGridArtistsAdapter(mRecommendedArtists, this);
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
                startActivityForResult(ArtistsSearchActivityIntent, STATIC_INTEGER_VALUE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STATIC_INTEGER_VALUE) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Artist selectedArtist = (Artist) data.getSerializableExtra("SelectedArtist");
                assert selectedArtist != null;
                serviceController.followArtistOrUser(Constants.user.isListenerType(), Constants.mToken, selectedArtist.getId());
            }
        }
    }

    @Override
    public void onListItemClick(View itemView, int clickedItemIndex) {

        View checkImage = itemView.findViewById(R.id.check_image);
        if(!serviceController.isFollowing(Constants.user.isListenerType(), Constants.mToken
                , mRecommendedArtists.get(clickedItemIndex).getId())) {

            checkImage.setVisibility(View.VISIBLE);
            serviceController.followArtistOrUser(Constants.user.isListenerType(), Constants.mToken
                    , mRecommendedArtists.get(clickedItemIndex).getId());

            ArrayList<Artist> relatedArtists = serviceController.getArtistRelatedArtists(this, mRecommendedArtists.get(clickedItemIndex).getId());
            for(Artist artist : relatedArtists)
            {
                if(!serviceController.isFollowing(Constants.user.isListenerType(), Constants.mToken
                        , artist.getId()))
                    mRecommendedArtists.add(clickedItemIndex + 1, artist);
            }

            adapter.notifyDataSetChanged();
            mItemDecoration.setDataSize(mRecommendedArtists.size());
            layoutManager.scrollToPositionWithOffset(clickedItemIndex, 0);

        }
        else {
            checkImage.setVisibility(View.GONE);
            serviceController.unFollowArtistOrUser(Constants.user.isListenerType(), Constants.mToken
                    , mRecommendedArtists.get(clickedItemIndex).getId());
        }
    }
}
