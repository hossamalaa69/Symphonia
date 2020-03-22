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

public class AddArtistsActivity extends AppCompatActivity implements RvGridArtistsAdapter.ListItemClickListener {

    private static final int STATIC_INTEGER_VALUE = 1;
    private static final String SELECTED_ARTIST_ID = "SelectedArtistId";
    private ServiceController mServiceController;
    private ArrayList<Artist> mRecommendedArtists;
    private RvGridArtistsAdapter mAdapter;
    private RecyclerView mArtistsList;
    private GridSpacingItemDecorationAdapter mItemDecoration;
    private GridLayoutManager mLayoutManager;
    private boolean isNewUser = false;
    private Button mButtonDone;
    private int countFollowing = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        mArtistsList = findViewById(R.id.rv_artists_grid);
        mItemDecoration = new GridSpacingItemDecorationAdapter(mRecommendedArtists.size(),
                3,
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STATIC_INTEGER_VALUE) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                String selectedArtistId = data.getStringExtra(SELECTED_ARTIST_ID);
                Artist selectedArtist = mServiceController.getArtist(this,
                        Constants.currentToken,
                        selectedArtistId);

                int position = 0;
                if(mRecommendedArtists.contains(selectedArtist)){
                    position = mRecommendedArtists.indexOf(selectedArtist);
                }
                else {
                    mRecommendedArtists.add(0, selectedArtist);
                }

                View view = mArtistsList.findViewHolderForAdapterPosition(position).itemView;
                View checkImage = view.findViewById(R.id.image_check);
                checkImage.setVisibility(View.VISIBLE);

                if(!mServiceController.isFollowing(Constants.currentUser.isListenerType(), Constants.currentToken
                        , selectedArtist.getId())) {

                    if(isNewUser){
                        countFollowing ++;
                        if(countFollowing>=3)
                            mButtonDone.setVisibility(View.VISIBLE);
                    }
                    mServiceController.followArtistOrUser(Constants.currentUser.isListenerType(), Constants.currentToken
                            , mRecommendedArtists.get(position).getId());
                }

                ArrayList<Artist> relatedArtists = mServiceController.getArtistRelatedArtists(this,
                        mRecommendedArtists.get(position).getId());

                for(Artist artist : relatedArtists)
                {
                    if(!mServiceController.isFollowing(Constants.currentUser.isListenerType(),
                            Constants.currentToken,
                            artist.getId()))
                    {
                        if (!mRecommendedArtists.contains(artist)) {
                            mRecommendedArtists.add(position + 1, artist);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
                mLayoutManager.scrollToPositionWithOffset(position, 0);
            }
        }
    }

    @Override
    public void onListItemClick(View itemView, int clickedItemIndex) {
        View checkImage = itemView.findViewById(R.id.image_check);

        if(!mServiceController.isFollowing(Constants.currentUser.isListenerType(),
                Constants.currentToken,
                mRecommendedArtists.get(clickedItemIndex).getId())) {

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
            checkImage.setVisibility(View.GONE);
            mServiceController.unFollowArtistOrUser(Constants.currentUser.isListenerType(),
                    Constants.currentToken,
                    mRecommendedArtists.get(clickedItemIndex).getId());

            ////////////////// if new user //////////////
            if(isNewUser){
                countFollowing--;
                if(countFollowing<3)
                    mButtonDone.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onBackPressed() {

        if(isNewUser){
            CustomSkipDialog custom_dialog = new CustomSkipDialog();
            custom_dialog.showDialog(this);
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