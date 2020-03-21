package com.example.symphonia.Activities.UserUI;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.Custom_Dialog_Offline;
import com.example.symphonia.Helpers.Custom_Dialog_SignUp;
import com.example.symphonia.Helpers.Custom_Dialog_Skip;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Adapters.GridSpacingItemDecorationAdapter;
import com.example.symphonia.Adapters.RvGridArtistsAdapter;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class AddArtistsActivity extends AppCompatActivity implements RvGridArtistsAdapter.ListItemClickListener {

    private static final int STATIC_INTEGER_VALUE = 1;
    ServiceController serviceController;
    ArrayList<Artist> mRecommendedArtists;
    RvGridArtistsAdapter adapter;
    RecyclerView artistList;
    GridSpacingItemDecorationAdapter mItemDecoration;
    GridLayoutManager layoutManager;

    private boolean isNewUser = false;
    private Button doneButton;
    private int countFollowing = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isOnline()) {
            if(!isOnline()){
                Custom_Dialog_Offline custom_dialogOffline = new Custom_Dialog_Offline();
                custom_dialogOffline.showDialog(this);
                finish();
            }
        }

        setContentView(R.layout.activity_add_artists);


        doneButton = (Button) findViewById(R.id.done_button);
        Bundle b = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        //collapsingToolbarLayout.setTitleEnabled(true);

        try{
            String recvData = b.getString("newUser");
            isNewUser = true;
            doneButton.setVisibility(View.INVISIBLE);
           //collapsingToolbarLayout.setTitle(getResources().getString(R.string.choose_3_artists));
        } catch(Exception e) {
            isNewUser = false;
            doneButton.setVisibility(View.VISIBLE);
           //collapsingToolbarLayout.setTitle(getResources().getString(R.string.title_activity_add_artists));
        }



        serviceController = ServiceController.getInstance();

        // will be modified
        mRecommendedArtists = new ArrayList<>();
        ArrayList<Artist> returnedArtists = serviceController
                .getRecommendedArtists(Constants.user.isListenerType(), Constants.mToken, 20);

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

        doneButton = findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if new user is using this page
                if(isNewUser){
                    Intent i = new Intent(AddArtistsActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else
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

            ////////////////////
            //increase count
            if(isNewUser){
                countFollowing ++;
                if(countFollowing>=3)
                    doneButton.setVisibility(View.VISIBLE);
            }
            //////////////////////////////////////

            boolean isAdded = false;
            ArrayList<Artist> relatedArtists = serviceController.getArtistRelatedArtists(this, mRecommendedArtists.get(clickedItemIndex).getId());
            for(Artist artist : relatedArtists)
            {
                if(!serviceController.isFollowing(Constants.user.isListenerType(), Constants.mToken
                        , artist.getId()))
                {
                    if (!mRecommendedArtists.contains(artist)) {
                        mRecommendedArtists.add(clickedItemIndex + 1, artist);
                        adapter.notifyItemInserted(clickedItemIndex + 1);
                        artistList.removeItemDecoration(mItemDecoration);
                        mItemDecoration.setDataSize(mRecommendedArtists.size());
                        artistList.addItemDecoration(mItemDecoration);
                        isAdded = true;
                    }

                }

            }
            if(isAdded) {
                layoutManager.scrollToPositionWithOffset(clickedItemIndex, 0);
            }
        }
        else {
            checkImage.setVisibility(View.GONE);
            serviceController.unFollowArtistOrUser(Constants.user.isListenerType(), Constants.mToken
                    , mRecommendedArtists.get(clickedItemIndex).getId());

            ////////////////// if new user //////////////
            if(isNewUser){
                countFollowing--;
                if(countFollowing<3)
                    doneButton.setVisibility(View.INVISIBLE);
            }
        }
    }


    @Override
    public void onBackPressed() {

        if(isNewUser){
            Custom_Dialog_Skip custom_dialog = new Custom_Dialog_Skip();
            custom_dialog.showDialog(this);
            return;
        }
        else
            super.onBackPressed();

    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }
}