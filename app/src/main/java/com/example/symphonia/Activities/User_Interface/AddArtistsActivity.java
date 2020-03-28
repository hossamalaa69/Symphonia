package com.example.symphonia.Activities.User_Interface;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.CustomOfflineDialog;
import com.example.symphonia.Helpers.CustomSkipDialog;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Adapters.GridSpacingItemDecorationAdapter;
import com.example.symphonia.Adapters.RvGridArtistsAdapter;
import com.example.symphonia.Service.ServiceController;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;
import java.util.ArrayList;

/**
 * Activity used in 2 cases:
 * When someone sign up
 * When a user click on add artist view in the library
 *
 * the user can add and search for any artist
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class AddArtistsActivity extends AppCompatActivity implements RvGridArtistsAdapter.ListItemClickListener {

    /**
     * Static final variable for opened subActivity to get the result from it
     */
    private static final int STATIC_INTEGER_VALUE = 1;
    /**
     * The id to get the result from the subActivity
     */
    private static final String SELECTED_ARTIST_ID = "SelectedArtistId";
    /**
     * Instance for requesting from mock services and Api
     */
    private ServiceController mServiceController;
    /**
     * A grid of the shown recommended artists for the current user to choose from
     */
    private ArrayList<Artist> mRecommendedArtists;
    /**
     * An adapter to control the grid of artists
     */
    private RvGridArtistsAdapter mAdapter;
    /**
     * Recyclerview to show the grid of the recommended artists
     */
    private RecyclerView mArtistsList;
    /**
     * Item decoration attached to the recyclerview to control the spacing
     * between the items of the grid
     */
    private GridSpacingItemDecorationAdapter mItemDecoration;
    /**
     * Make a grid view containing 3 items in the row
     */
    private GridLayoutManager mLayoutManager;
    /**
     * A boolean to know if the user is new and just signed up
     * or an old user that comes to add artists
     */
    private boolean isNewUser = false;
    /**
     * Done button to be clicked when the user finish adding artists
     */
    private Button mButtonDone;
    /**
     * Counter to get the number of following artists in case if
     * the user is a new one and should add at least 3 artists
     * to complete the registration
     */
    private int countFollowing = 0;
    /**
     * Keep the position of the clicked item from search activity
     */
    private int mSelectedArtistPosition = 0;


    /**
     * initialize the ui of the activity
     *
     * @param savedInstanceState for saved data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check for internet connection
        if(!isOnline()) {
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(AddArtistsActivity.this, true);
            return;
        }

        setContentView(R.layout.activity_add_artists);
        mButtonDone = (Button) findViewById(R.id.button_done);
        Bundle b = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        // determine whether the user is coming from signing up or from library
        try{
            assert b != null;
            String recvData = b.getString("newUser");
            isNewUser = true;
            mButtonDone.setVisibility(View.GONE);
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.choose_3_artists));

        } catch(Exception e) {
            isNewUser = false;
            mButtonDone.setVisibility(View.VISIBLE);
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.title_activity_add_artists));
        }

        mServiceController = ServiceController.getInstance();

        // getting the recommended artists from the server and check that the user doesn't follow them
        mRecommendedArtists = new ArrayList<>();
        ArrayList<Artist> returnedArtists = mServiceController.getRecommendedArtists
                (Constants.currentUser.isListenerType(), Constants.currentToken, 20);

        for (Artist artist : returnedArtists) {
            if(!mServiceController.isFollowing(Constants.currentUser.isListenerType(),
                    Constants.currentToken,
                    artist.getId())){

                mRecommendedArtists.add(artist);
            }
        }

        // preparing the recyclerview and its components
        mArtistsList = findViewById(R.id.rv_artists_grid);
        mItemDecoration = new GridSpacingItemDecorationAdapter(3,
                50,
                true);

        mArtistsList.addItemDecoration(mItemDecoration);
        mLayoutManager = new GridLayoutManager(this, 3);
        mArtistsList.setLayoutManager(mLayoutManager);
        mAdapter = new RvGridArtistsAdapter(mRecommendedArtists, this);
        mArtistsList.setAdapter(mAdapter);

        if(!isOnline()) {
            CustomOfflineDialog custom_dialogOffline = new CustomOfflineDialog();
            custom_dialogOffline.showDialog(AddArtistsActivity.this, true);
        }


        mButtonDone = findViewById(R.id.button_done);
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent ArtistsSearchActivityIntent = new Intent(AddArtistsActivity.this,
                        ArtistsSearchActivity.class);

                startActivityForResult(ArtistsSearchActivityIntent, STATIC_INTEGER_VALUE);
            }
        });
    }

    /**
     * Get a result from its subActivity ArtistSearchActivity
     * which returns the selected artist from the search
     *
     * @param requestCode get the request from the right subActivity
     * @param resultCode check if the user send data or not
     * @param data the id of the clicked item in artist search activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STATIC_INTEGER_VALUE) {
            if (resultCode == Activity.RESULT_OK) {

                // store the returned id and get the artist of this id
                assert data != null;
                String selectedArtistId = data.getStringExtra(SELECTED_ARTIST_ID);
                Artist selectedArtist = mServiceController.getArtist(this,
                        Constants.currentToken,
                        selectedArtistId);

                // add the artist to recommended list if it's not in it
                if(mRecommendedArtists.contains(selectedArtist)){
                    mSelectedArtistPosition = mRecommendedArtists.indexOf(selectedArtist);
                }
                else {
                    mRecommendedArtists.add(0, selectedArtist);
                }

                // follow the selected artist if it's not already followed
                if(!mServiceController.isFollowing(Constants.currentUser.isListenerType(), Constants.currentToken
                        , selectedArtist.getId())) {

                    if(isNewUser){
                        countFollowing ++;
                        if(countFollowing>=3) mButtonDone.setVisibility(View.VISIBLE);
                    }
                    mServiceController.followArtistOrUser(Constants.currentUser.isListenerType(), Constants.currentToken
                            , mRecommendedArtists.get(mSelectedArtistPosition).getId());
                }

                // get and update the list with some related artists to the chosen one
                ArrayList<Artist> relatedArtists = mServiceController.getArtistRelatedArtists(this,
                        mRecommendedArtists.get(mSelectedArtistPosition).getId());

                for(Artist artist : relatedArtists)
                {
                    if(!mServiceController.isFollowing(Constants.currentUser.isListenerType(),
                            Constants.currentToken,
                            artist.getId()))
                    {
                        if (!mRecommendedArtists.contains(artist)) {
                            mRecommendedArtists.add(mSelectedArtistPosition + 1, artist);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
                mLayoutManager.scrollToPositionWithOffset(mSelectedArtistPosition, 0);
            }
        }
    }

    /**
     * handles the click of the items in the recyclerview
     * by following or unfollowing the clicked artists
     * and showing related artists
     *
     * @param itemView the clicked view layout
     * @param clickedItemIndex the index of the clicked view in the recyclerview
     */
    @Override
    public void onListItemClick(View itemView, int clickedItemIndex) {
        View checkImage = itemView.findViewById(R.id.image_check);

        if(!mServiceController.isFollowing(Constants.currentUser.isListenerType(),
                Constants.currentToken,
                mRecommendedArtists.get(clickedItemIndex).getId())) {

            // if the user doesn't follow the artist then follow him/her and get some related artists

            if(isNewUser){
                countFollowing ++;
                if(countFollowing>=3)
                    mButtonDone.setVisibility(View.VISIBLE);
            }

            checkImage.setVisibility(View.VISIBLE);
            mServiceController.followArtistOrUser(Constants.currentUser.isListenerType(),
                    Constants.currentToken,
                    mRecommendedArtists.get(clickedItemIndex).getId());

            boolean isAdded = false;
            ArrayList<Artist> relatedArtists = mServiceController.getArtistRelatedArtists(this,
                    mRecommendedArtists.get(clickedItemIndex).getId());

            for(Artist artist : relatedArtists)
            {
                if(!mServiceController.isFollowing(Constants.currentUser.isListenerType(),
                        Constants.currentToken,
                        artist.getId()))
                {
                    if (!mRecommendedArtists.contains(artist)) {
                        mRecommendedArtists.add(clickedItemIndex + 1, artist);
                        mAdapter.notifyItemInserted(clickedItemIndex + 1);
                        isAdded = true;
                    }

                }

            }
            if(isAdded) {
                mLayoutManager.scrollToPositionWithOffset(clickedItemIndex, 0);
            }
        }
        else {

            // if the user is following the artist then unfollow him/her
            checkImage.setVisibility(View.GONE);
            mServiceController.unFollowArtistOrUser(Constants.currentUser.isListenerType(),
                    Constants.currentToken,
                    mRecommendedArtists.get(clickedItemIndex).getId());

            if(isNewUser){
                countFollowing--;
                if(countFollowing<3)
                    mButtonDone.setVisibility(View.GONE);
            }
        }
    }


    /**
     * handle the return from the activity in the two cases
     */
    @Override
    public void onBackPressed() {

        if(isNewUser){
            CustomSkipDialog custom_dialog = new CustomSkipDialog();
            custom_dialog.showDialog(this);
        }
        else
            super.onBackPressed();
    }


    /**
     * @return if there is a connection to the internet or not
     */
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }
}