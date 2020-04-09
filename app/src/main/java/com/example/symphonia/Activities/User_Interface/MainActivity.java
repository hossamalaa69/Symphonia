package com.example.symphonia.Activities.User_Interface;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.RvBarAdapter;
import com.example.symphonia.Adapters.RvPlaylistsHomeAdapter;
import com.example.symphonia.Adapters.RvTracksHomeAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Profile;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.library.LibraryFragment;
import com.example.symphonia.Fragments_and_models.playlist.BottomSheetDialogSettings;
import com.example.symphonia.Fragments_and_models.playlist.BottomSheetDialogSettingsCredits;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Fragments_and_models.premium.PremiumFragment;
import com.example.symphonia.Fragments_and_models.profile.FragmentProfile;
import com.example.symphonia.Fragments_and_models.profile.ProfileFollowersFragment;
import com.example.symphonia.Fragments_and_models.profile.ProfilePlaylistsFragment;
import com.example.symphonia.Fragments_and_models.search.SearchFragment;
import com.example.symphonia.Fragments_and_models.settings.SettingsFragment;
import com.example.symphonia.Helpers.SnapHelperOneByOne;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.MediaController;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Activity that accessing tracks and playing them
 *
 * @author Khaled Ali
 * @version 1.0
 * @since 22-3-2020
 */
public class MainActivity extends AppCompatActivity implements RvPlaylistsHomeAdapter.OnPlaylistClicked
        , RvTracksHomeAdapter.OnTrackClicked
        , RestApi.updateUiPlaylists
        // ,RestApi.updateUiGetCategories
        , RestApi.updateProfileFollow
        , RestApi.updateUiProfileInSetting
        , RestApi.updateUiProfileInProfileFragment
        , RestApi.updateProfilePlaylists
        , BottomSheetDialogSettings.BottomSheetListener
        , RvBarAdapter.ItemInterface, Serializable {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvBar;
    private RvBarAdapter barAdapter;
    private ImageView ivIsFavourite;
    private ImageView ivPlayButton;
    private View playBar;
    private ImageView trackImage;
    private Toast toast;
    private PlaylistFragment playlistFragment;
    private boolean root = true;
    /**
     * holds position of item which its color needs to be reset
     */
    int prevPos;
    /**
     * this handler is responsible for delay of update playBar
     */
    Handler mHandler = new Handler();
    /**
     * instance of Media Controller
     */
    private MediaController mediaController;
    /**
     * constant indicates that track is Utils.CurrTrackInfo.paused
     */
    private final String IS_PAUSED = "isPaused";
    /**
     * navigation view of main activity
     */
    private BottomNavigationView navView;
    /**
     * layout of settings
     */
    View linearLayout;

    /**
     * this function is called when user click on like item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onLikeClicked(int pos) {
        if (!Utils.CurrPlaylist.playlist.getTracks().get(pos).isLiked() && !Utils.CurrPlaylist.playlist.getTracks().get(pos).isLocked()) {
            Utils.CurrPlaylist.playlist.getTracks().get(pos).setLiked(true);
            //TODO make request to make it liked
            if (playlistFragment != null && playlistFragment.isVisible()) {
                playlistFragment.changeLikedItemAtPos(pos, true);
            }

        } else if (!Utils.CurrPlaylist.playlist.getTracks().get(pos).isLocked()) {
            Utils.CurrPlaylist.playlist.getTracks().get(pos).setLiked(false);
            if (playlistFragment != null && playlistFragment.isVisible()) {
                playlistFragment.changeLikedItemAtPos(pos, false);
            }
            //TODO make request to make it liked
        } else {
            makeToast(MainActivity.this.getString(R.string.locked_songs));
        }
    }

    /**
     * this function is called when user click on hide item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onHideClicked(int pos) {
        if (!Utils.CurrPlaylist.playlist.getTracks().get(pos).isHidden() && !Utils.CurrPlaylist.playlist.getTracks().get(pos).isLocked()) {
            Utils.CurrPlaylist.playlist.getTracks().get(pos).setHidden(true);
            if (playlistFragment != null && playlistFragment.isVisible()) {
                playlistFragment.changeHidden(pos, true);
            }
            //TODO make request to make it hidden
        } else if (!Utils.CurrPlaylist.playlist.getTracks().get(pos).isLocked()) {
            Utils.CurrPlaylist.playlist.getTracks().get(pos).setLiked(true);
            if (playlistFragment != null && playlistFragment.isVisible()) {
                playlistFragment.changeHidden(pos, false);
            }
            //TODO make request to make it not hidden
        } else {
            makeToast(MainActivity.this.getString(R.string.locked_songs));
        }
    }

    /**
     * this function is called when user click on report item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onReportClicked(int pos) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));
        Toast toast = new Toast(MainActivity.this);
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * this function is called when user click on share item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onShareClicked(int pos) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "" + Utils.CurrPlaylist.playlist.getTracks().get(pos).getUri());
        mHandler.removeCallbacks(runnable);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * this function is called when user click on show credits item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onCreditsClicked(int pos) {
        BottomSheetDialogSettingsCredits sheet = new BottomSheetDialogSettingsCredits(pos);
        sheet.show(getSupportFragmentManager(), "credits");
    }

    /**
     * this function is called when user click on view artist item in settings of track
     *
     * @param pos position of track in current playlist
     */
    @Override
    public void onViewArtistClicked(int pos) {
        //TODO open artist in islam's work
    }
    //----------------------------------------------------------------------------

    /**
     * this function updates data after fetching categories
     */
    @Override
    public void getCategoriesSuccess() {
        /*if (navView.getSelectedItemId() == R.id.navigation_home)
            homeFragment.loadAllPlaylists();*/
    }

    @Override
    public void updateUiGetTracksOfPlaylist(PlaylistFragment playlistFragment) {
        if (playlistFragment.isVisible()) {
            playlistFragment.updateTracks();
            playlistFragment.hideProgressBar();
        }
    }

    /**
     * this function updates data after fetching popular playlists
     */
    @Override
    public void updateUiGetPopularPlaylistsSuccess() {
        if (navView.getSelectedItemId() == R.id.navigation_home)
        {homeFragment.updatePopularPlaylists(); homeFragment.hideProgressBar();}
    }

    /**
     * this function updates data after fetching random playlists
     */
    @Override
    public void updateUiGetRandomPlaylistsSuccess(HomeFragment homeFragment) {
        if (navView.getSelectedItemId() == R.id.navigation_home)
        {  homeFragment.updateRandomPlaylists();      homeFragment.hideProgressBar();}

    }

    /**
     * this function updates data after fetching recent playlists
     */
    @Override
    public void updateUiGetRecentPlaylistsSuccess(HomeFragment homeFragment) {
        if (navView.getSelectedItemId() == R.id.navigation_home)
        { homeFragment.updateRecentPlaylists();   homeFragment.hideProgressBar();}
    }

    @Override
    public void updateUiPlayTrack() {
        Log.e("main update", "call play track");
        if (!Utils.CurrTrackInfo.loading)
            playTrack();
    }

    /**
     * this function updates data after fetching make-for-you playlists
     */
    @Override
    public void updateUiGetMadeForYouPlaylistsSuccess() {
        if (navView.getSelectedItemId() == R.id.navigation_home)
            homeFragment.updateMadeForYouPlaylists();
    }

    @Override
    public void updateUiNoTracks() {
        if (playlistFragment.isVisible()) {
            playlistFragment.hideProgressBar();
        }
    }

    /**
     * this function updates data if fail to  fetch popular playlists
     */
    @Override
    public void updateUiGetPopularPlaylistsFail() {

    }

    /**
     * this function updates data if fail to  fetch random playlists
     */
    @Override
    public void updateUiGetRandomPlaylistsFail() {

    }

    /**
     * this function updates data if fail to  fetch recent playlists
     */
    @Override
    public void updateUiGetRecentPlaylistsFail() {

    }

    /**
     * this function updates data if fail to  fetch make-for-you playlists
     */
    @Override
    public void updateUiGetMadeForYouPlaylistsFail() {

    }

    /**
     * Represents the initialization of activity
     *
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mediaController = MediaController.getController();
        Bundle b = getIntent().getExtras();
        checkUserType(b);

        // initialize bottom navigation view
        initBottomNavView();

        // attach views
        attachViews();

        addListeners();

        // add tracks of playlist to play bar recycler view
        if (Utils.CurrPlaylist.playlist != null) {
            barAdapter = new RvBarAdapter(this, Utils.CurrPlaylist.playlist.getTracks());
            playBar.setVisibility(View.VISIBLE);
        } else
            barAdapter = new RvBarAdapter(this, null);

        rvBar.setAdapter(barAdapter);

        if (mediaController.isMediaPlayerPlaying()) {
            playBar.setVisibility(View.VISIBLE);
            rvBar.getLayoutManager().scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
            updatePlayBar();
        }
        toast = null;
        checkIntent(getIntent().getExtras());


    }

    /**
     * on start is called when activity was hidden and is shown again
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (playlistFragment != null && playlistFragment.isVisible() && mediaController.isMediaNotNull()) {
            playlistFragment.changeSelected(prevPos, Utils.CurrTrackInfo.TrackPosInPlaylist);
        } else if (playlistFragment != null && playlistFragment.isVisible() && !mediaController.isMediaNotNull()) {
            playlistFragment.changeSelected(prevPos, -1);
        }
        if (playlistFragment != null && playlistFragment.isVisible() && Utils.CurrTrackInfo.currPlaylistName != null) {
            for (int i = 0; i < Utils.CurrTrackInfo.currPlaylistTracks.size(); i++) {
                playlistFragment.changeHidden(i, Utils.CurrTrackInfo.currPlaylistTracks.get(i).isHidden());
                playlistFragment.changeLikedItemAtPos(i, Utils.CurrTrackInfo.currPlaylistTracks.get(i).isLiked());
            }
        }
        mHandler.post(runnable);

        MediaController.setOnCompletionListener(onCompletionListener);
        mediaController.setMediaPlayCompletionService();
        prevPos = Utils.CurrTrackInfo.prevTrackPos;
        updatePlayBar();
        if(trackImage!=null &&Utils.CurrTrackInfo.track!=null)
        if (!Constants.DEBUG_STATUS)
            Picasso.get()
                    .load(Utils.CurrTrackInfo.track.getImageUrl())
                    .fit()
                    .centerCrop()
                    .into(trackImage);
        else
            trackImage.setImageResource(Utils.CurrTrackInfo.track.getmImageResources());

        this.runOnUiThread(runnable);
        //check if user online
        if (!isOnline()) {
            connectToInternet();
        }
    }

    /**
     * this is a runnable for updating playBar ui in ui thread
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //     updatePlayBar();
            if (!Utils.CurrTrackInfo.paused ||
                    (mediaController.isMediaNotNull() && mediaController.isMediaPlayerPlaying())) {
                updatePlayBtn();
            }

            mHandler.postDelayed(this, 500);
        }
    };

    /**
     * this function shows playBar
     */
    public void showPlayBar() {
        barAdapter.setTracks(Utils.CurrTrackInfo.currPlaylistTracks);
        playBar.setVisibility(View.VISIBLE);
    }

    /**
     * this function creates toast to show a message to user
     *
     * @param message takes message to be shown in toast
     */
    private void makeToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();

    }

    /**
     * this function is called when playBar is recycled
     *
     * @param pos position of current track
     */
    @Override
    public void OnItemSwitchedListener(int pos) {
        Utils.CurrTrackInfo.paused = false;
        if (playlistFragment != null && playlistFragment.isVisible())
            playlistFragment.changeSelected(Utils.CurrTrackInfo.TrackPosInPlaylist, pos);
        Utils.setTrackInfo(0, pos, Utils.CurrTrackInfo.currPlaylistTracks);
        startTrack();
        updatePlayBar();
    }

    /**
     * this function updates playBar with current playing track's info..
     */
    public void updatePlayBar() {

        if (rvBar != null) {
            if (Utils.CurrTrackInfo.TrackPosInPlaylist != -1)
                rvBar.getLayoutManager().scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
            if (mediaController.isMediaPlayerPlaying() && !Utils.CurrTrackInfo.paused) {
                ivPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
            } else {
                ivPlayButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            }
        }
        if (ivIsFavourite != null && Utils.CurrTrackInfo.track != null) {
            if (Utils.CurrTrackInfo.track.isLiked() && Utils.CurrPlaylist.playlist.getmPlaylistTitle().matches(Utils.CurrTrackInfo.currPlaylistName)) {
                ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else if (Utils.CurrPlaylist.playlist.getmPlaylistTitle().matches(Utils.CurrTrackInfo.currPlaylistName)) {
                ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            }
        }

        if (trackImage != null && Utils.CurrTrackInfo.track != null&&Constants.DEBUG_STATUS) {
            trackImage.setImageResource(Utils.CurrTrackInfo.track.getmImageResources());
        }
    }


    /**
     * this function play media player with track stored in Current Playing
     */
    public void playTrack() {
        Log.e("main", "play");
        Utils.CurrTrackInfo.paused = false;
        Intent intent = new Intent(this, MediaController.class);
        intent.setAction(MediaController.ACTION_PLAY);
        startService(intent);
        updatePlayBtn();
        MediaController.setOnCompletionListener(onCompletionListener);
    }

    /**
     * listener called when track is clicked
     *
     * @param tracks list of tracks of current playlist
     * @param pos    position of track in current playlist
     * @param prev   previous position of track in current playlist
     */
    @Override
    public void OnTrackClickedListener(final ArrayList<Track> tracks, final int pos, int prev) {

        if (tracks.get(pos).isLocked() && !Constants.currentUser.isPremuim()) {
            makeToast(getString(R.string.track_is_locked));
            return;
        }

        playBar.setVisibility(View.VISIBLE);

        // keep tracking previous track
        if (Utils.CurrTrackInfo.TrackPosInPlaylist != -1) {
            Utils.CurrTrackInfo.prevTrackPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
            prevPos = Utils.CurrTrackInfo.prevTrackPos;
        }

        Utils.setTrackInfo(0, pos, Utils.CurrPlaylist.playlist.getTracks());
        if (barAdapter.getTracks() == null) {
            barAdapter.setTracks(Utils.CurrPlaylist.playlist.getTracks());
            barAdapter.notifyDataSetChanged();
        }
        // update data of bar

        if (!Constants.DEBUG_STATUS)
            Picasso.get()
                    .load(Utils.CurrTrackInfo.track.getImageUrl())
                    .fit()
                    .centerCrop()
                    .into(trackImage);
        else
            trackImage.setImageResource(Utils.CurrTrackInfo.track.getmImageResources());
        rvBar.getLayoutManager().scrollToPosition(pos);
        ivPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);


        Utils.setTrackInfo(0, pos, tracks);
        if (playlistFragment.isVisible()) {
            playlistFragment.changeSelected(prevPos, pos);
        }

        if (tracks.get(pos).isLiked()) {
            ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        Log.e("main", "track clicked");
        startTrack();
    }

    public void startTrack() {
        Utils.CurrTrackInfo.paused = false;
        if (Constants.DEBUG_STATUS)
            playTrack();
        else {
            Utils.CurrTrackInfo.loading = true;
            if (mediaController.isMediaPlayerPlaying())
                mediaController.releaseMedia();
            ServiceController.getInstance().playTrack(this, null, null, null, null);
        }
    }


    /**
     * this function adds listener to views in main activity
     */
    private void addListeners() {

        ivIsFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.CurrTrackInfo.currPlaylistTracks.get(Utils.CurrTrackInfo.TrackPosInPlaylist).isLiked()) {
                    Utils.CurrTrackInfo.currPlaylistTracks.get(Utils.CurrTrackInfo.TrackPosInPlaylist).setLiked(false);
                    ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                    makeToast(getString(R.string.remove_from_liked_playlist));
                } else {
                    makeToast(getString(R.string.add_to_like_playlist));

                    ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Utils.CurrTrackInfo.currPlaylistTracks.get(Utils.CurrTrackInfo.TrackPosInPlaylist).setLiked(true);

                }
                if (Utils.CurrPlaylist.playlist.getmPlaylistTitle().matches(Utils.CurrTrackInfo.currPlaylistName) && playlistFragment != null)
                    playlistFragment.changeLikedItemAtPos(Utils.CurrTrackInfo.TrackPosInPlaylist
                            , Utils.CurrTrackInfo.currPlaylistTracks.get(Utils.CurrTrackInfo.TrackPosInPlaylist).isLiked());
            }
        });


        playBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra(IS_PAUSED, Utils.CurrTrackInfo.paused);
                prevPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
                startActivity(intent);
            }
        });
        ivPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaController.isMediaPlayerPlaying()) {
                    ivPlayButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    Utils.CurrTrackInfo.paused = true;
                    mediaController.pauseMedia();
                } else {
                    ivPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
                    Utils.CurrTrackInfo.paused = false;
                    if (mediaController.isMediaNotNull())
                        mediaController.resumeMedia();
                    else {
                        startTrack();
                        if (playlistFragment.isVisible()) {
                            playlistFragment.changeSelected(-1, Utils.CurrTrackInfo.TrackPosInPlaylist);
                        }
                    }

                }
            }
        });

    }

    /**
     * this function updates playButton to playing
     */
    private void updatePlayBtn() {
        ivPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
    }

    /**
     * this is a listener to media player after completion
     */
    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Utils.CurrTrackInfo.prevTrackPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
            prevPos = Utils.CurrTrackInfo.prevTrackPos;
            for (int i = Utils.CurrTrackInfo.TrackPosInPlaylist + 1; i < Utils.CurrTrackInfo.currPlaylistTracks.size(); i++) {
                if (!Utils.CurrTrackInfo.currPlaylistTracks.get(i).isHidden()
                        && !(Utils.CurrTrackInfo.currPlaylistTracks.get(i).isLocked() && !Constants.currentUser.isPremuim())) {
                    Utils.CurrTrackInfo.TrackPosInPlaylist = i;
                    Utils.setTrackInfo(0, Utils.CurrTrackInfo.TrackPosInPlaylist, Utils.CurrTrackInfo.currPlaylistTracks);
                    rvBar.getLayoutManager().scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
                    startTrack();
                    if (playlistFragment != null && playlistFragment.isVisible()) {
                        playlistFragment.changeSelected(prevPos, Utils.CurrTrackInfo.TrackPosInPlaylist);
                    }
                    updatePlayBar();
                    return;
                }
            }
            Utils.CurrTrackInfo.paused = true;
            mediaController.releaseMedia();
            if (playlistFragment != null && playlistFragment.isVisible()) {
                playlistFragment.changeSelected(Utils.CurrTrackInfo.TrackPosInPlaylist, -1);
            }
            updatePlayBar();
        }
    };


    /**
     * listener called when track is selected to be favourite
     *
     * @param selected is track selected
     * @param pos      position of track
     */
    @Override
    public void OnLikeClickedListener(boolean selected, int pos) {
        if (Utils.CurrTrackInfo.currPlaylistTracks == null) {
            return;
        }
        if (Utils.CurrTrackInfo.currPlaylistTracks.get(pos) == Utils.CurrTrackInfo.track) {
            if (selected && pos == Utils.CurrTrackInfo.TrackPosInPlaylist) {
                ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else {
                ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
        }
    }


    /**
     * listener called when playlist is clicked
     *
     * @param playlist playlist that is clicked
     */
    @Override
    public void OnPlaylistClickedListener(Playlist playlist) {
        Utils.CurrPlaylist.playlist = playlist;
        playlistFragment = new PlaylistFragment();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.nav_host_fragment, playlistFragment
                , this.getString(R.string.playlist_fragment))
                .addToBackStack(null)
                .commit();
    }

    /**
     * this function attach data to views in mainActivity
     */
    private void attachViews() {
        rvBar = findViewById(R.id.rv_bar_main_activity);
        // add recycler to snap to control scroll one item at a time
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvBar);
        playBar = findViewById(R.id.layout_playing_bar);

        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvBar.setLayoutManager(layoutManager);
        rvBar.setHasFixedSize(true);

        trackImage = playBar.findViewById(R.id.iv_track_image_bar);
        ivPlayButton = playBar.findViewById(R.id.iv_play_track_bar);
        ivIsFavourite = playBar.findViewById(R.id.iv_like_track_bar);
    }

    /**
     * this function is called when setting is clicked
     *
     * @param pos position of track
     */
    @Override
    public void showTrackSettingFragment(int pos) {

        BottomSheetDialogSettings settings = new BottomSheetDialogSettings(pos, this);
        settings.show(getSupportFragmentManager(), "settings");
      /*  setDataOfTrackSettings(pos);
        setSettingListeners(pos);*/
    }


    /**
     * this function is called when back arrow button is clicked
     */
    @Override
    public void onBackPressed() {
        if (playlistFragment != null && playlistFragment.isVisible()) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        if (root) {
            finishAffinity();
        }
        super.onBackPressed();

    }

    /**
     * onClickListener called when user click on playBar
     *
     * @param tracks tracks in current playlist
     * @param pos    position of current playing track
     */
    @Override
    public void OnItemClickedListener(ArrayList<Track> tracks, int pos) {
        prevPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * this holds instance of home fragment
     */
    private HomeFragment homeFragment;

    private SearchFragment searchFragment;
    private LibraryFragment libraryFragment;
    private PremiumFragment premiumFragment;

    /**
     * this function initialize BottomNavigationView
     */
    private void initBottomNavView() {
        navView = findViewById(R.id.nav_view);

        // menu should be considered as top level destinations.
        NavHostFragment navHostFragment = NavHostFragment.create(R.navigation.mobile_navigation);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, navHostFragment)
                .setPrimaryNavigationFragment(navHostFragment)
                .commit();
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        libraryFragment = new LibraryFragment();
        premiumFragment = new PremiumFragment();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, homeFragment, "home")
                                .commit();
                        return true;
                    case R.id.navigation_library:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, libraryFragment, "library")
                                .commit();
                        return true;
                    case R.id.navigation_search:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, searchFragment, "search")
                                .commit();
                        return true;
                    case R.id.navigation_premium:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, premiumFragment, "premium")
                                .commit();
                        return true;
                }
                return false;
            }
        });


    }

    /**
     * shows an AlertDialog to go to WIFI settings
     */
    private void connectToInternet() {

        // build title and message views for AlertDialog
        TextView title = new TextView(this);
        title.setText(R.string.network);
        title.setTextColor(getResources().getColor(android.R.color.white));
        title.setTextSize(24/*set test size to 24sp */);
        title.setPadding((int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding));
        TextView body = new TextView(this);
        body.setText(R.string.you_are_not_online);
        body.setTextSize(18/*set test size to 18sp */);
        body.setTextColor(getResources().getColor(android.R.color.white));
        body.setPadding((int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding),
                (int) getResources().getDimension(R.dimen.title_padding));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(getString(R.string.connect), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, getString(R.string.only_local_data_show), Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCustomTitle(title);
        dialog.setView(body);
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.dark_gray);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(this.getResources().getColor(android.R.color.white));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(this.getResources().getColor(android.R.color.white));
    }

    /**
     * check if user is online
     *
     * @return true if user online otherwise return false
     */
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    public void checkUserType(Bundle b) {

        if (Constants.DEBUG_STATUS) {
            SharedPreferences sharedPref = getSharedPreferences("LoginPref", 0);
            String email = sharedPref.getString("email", "");
            boolean type = sharedPref.getBoolean("type", true);
            String x;
            try{
                x = b.getString("newuser");
            }catch (NullPointerException e) {
                ServiceController serviceController = ServiceController.getInstance();
                boolean logged = serviceController.logIn(this, email, "12345678", type);
                if (!logged)
                    serviceController.logIn(this, "user1@symphonia.com"
                            , "12345678", true);
            }
        }

/*        if (Constants.currentUser.isListenerType())
            Toast.makeText(this, "Listener", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Artist", Toast.LENGTH_SHORT).show();

        if (Constants.currentUser.isPremuim())
            Toast.makeText(this, "Premium", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Not Premium", Toast.LENGTH_SHORT).show();*/
    }

    public void checkIntent(Bundle b) {
        try {
            String received = b.getString("go_to");
            if (received.equals("premium")) {
                BottomNavigationView navView = findViewById(R.id.nav_view);
                navView.setSelectedItemId(R.id.navigation_premium);
            }
        } catch (Exception e) {
            return;
        }
    }


    @Override
    public void getCurrentProfilePlaylists(ArrayList<Container> playlists, FragmentProfile fragmentProfile) {
        fragmentProfile.updatePlaylists(playlists);
    }

    @Override
    public void getCurrentUserFollowingNumber(String f, FragmentProfile fragmentProfile) {
        fragmentProfile.updateUiFollowing(f);
    }

    @Override
    public void getCurrentProfile(Profile profile, SettingsFragment settingsFragment) {
        settingsFragment.updateUiProfile(profile);
    }

    /*@Override
    public void getCurrentUserFollowing(ArrayList<Container> f, FragmentProfile fragmentProfile) {
        fragmentProfile.updateUiFollowing(f);
    }

    @Override
    public void getCurrentUserFollowers(ArrayList<Profile> f, FragmentProfile fragmentProfile) {
        //fragmentProfile.updateUiFollowers(f);
    }*/

    @Override
    public void getCurrentUserFollowersNumber(String num, FragmentProfile fragmentProfile) {
        fragmentProfile.updateUiFollowers(num);
    }

    @Override
    public void getAllUserPlaylists(ArrayList<Container> p, ProfilePlaylistsFragment profilePlaylistsFragment) {
        profilePlaylistsFragment.updatePlaylists(p);
    }

    @Override
    public void getUserFollowers(ArrayList<Container> f, ProfileFollowersFragment profileFollowersFragment) {
        profileFollowersFragment.updateUiFollowers(f);
    }

    @Override
    public void getUserFollowing(ArrayList<Container> f, ProfileFollowersFragment profileFollowersFragment) {
        profileFollowersFragment.updateUiFollowing(f);
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    /*@Override
    public void getCategoriesSuccess(ArrayList<Category> c) {
        searchFragment.UpdateUiGetCategories(c);
    }*/
}
