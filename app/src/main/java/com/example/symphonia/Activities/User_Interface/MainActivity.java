package com.example.symphonia.Activities.User_Interface;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
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
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.library.LibraryFragment;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Fragments_and_models.premium.PremiumFragment;
import com.example.symphonia.Fragments_and_models.search.SearchFragment;
import com.example.symphonia.Helpers.SnapHelperOneByOne;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RvPlaylistsHomeAdapter.OnPlaylistClicked
        , RvTracksHomeAdapter.OnTrackClicked
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkUserType();

        // initialize bottom navigation view
        initBottomNavView();

        // attach views
        attachViews();

        if (Utils.MediaPlayerInfo.isMediaPlayerPlaying()) {
            playBar.setVisibility(View.VISIBLE);
            rvBar.scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
            updatePlayBar();
        }
        Utils.MediaPlayerInfo.onCompletionListener = onCompletionListener;
        toast = null;

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

    @Override
    public void OnItemSwitchedListener(int pos) {
        setTrackInfo(0, pos, Utils.CurrTrackInfo.currPlaylistTracks);
        playTrack();
        updatePlayBar();
    }


    /**
     * this function updates playBar with current playing track's info..
     */
    private void updatePlayBar() {
        rvBar.scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
        if (Utils.MediaPlayerInfo.isMediaPlayerPlaying()) {
            ivPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);

        } else {
            ivPlayButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
        trackImage.setImageResource(Utils.CurrTrackInfo.track.getmImageResources());
    }

    /**
     * this function play media player with track stored in Current Playing
     */
    private void playTrack() {
        Utils.MediaPlayerInfo.playTrack(this);
    }

    @Override
    public void OnTrackClickedListener(final ArrayList<Track> tracks, final int pos, int prev) {
        playBar.setVisibility(View.VISIBLE);
        if (Utils.CurrTrackInfo.TrackPosInPlaylist != -1)
            Utils.CurrTrackInfo.prevTrackPos = Utils.CurrTrackInfo.TrackPosInPlaylist;

        if (playlistFragment.isVisible()) {
            playlistFragment.changeSelected(Utils.CurrTrackInfo.prevTrackPos, pos);
        }
        // update data of bar
        trackImage.setImageResource(tracks.get(pos).getmImageResources());
        rvBar.getLayoutManager().scrollToPosition(pos);
        ivPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
        ivPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.MediaPlayerInfo.isMediaPlayerPlaying()) {
                    ivPlayButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    Utils.MediaPlayerInfo.pauseTrack();

                } else {
                    ivPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
                    Utils.MediaPlayerInfo.resumeTrack();
                }
            }
        });
        setTrackInfo(0, pos, tracks);
        if (tracks.get(pos).isLiked()) {
            ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        ivIsFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tracks.get(pos).isLiked()) {
                    tracks.get(pos).setLiked(false);
                    ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    makeToast(getString(R.string.remove_from_liked_playlist));
                } else {
                    makeToast(getString(R.string.add_to_like_playlist));

                    ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    tracks.get(pos).setLiked(true);

                }
                playlistFragment.changeLikedItemAtPos(Utils.CurrTrackInfo.TrackPosInPlaylist
                        , tracks.get(Utils.CurrTrackInfo.TrackPosInPlaylist).isLiked());
            }
        });
        playBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra(getString(R.string.playlist_send_to_playActivtiy_intent), tracks);
                intent.putExtra(getString(R.string.curr_playing_track_play_acitivity_intent), pos);
                startActivity(intent);
            }
        });
        playTrack();
    }

    /**
     * this is a listener to media player after completion
     */
    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Utils.CurrTrackInfo.prevTrackPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
            for (int i = Utils.CurrTrackInfo.TrackPosInPlaylist + 1; i < Utils.CurrTrackInfo.currPlaylistTracks.size(); i++) {
                if (!Utils.CurrTrackInfo.currPlaylistTracks.get(i).isHidden()) {
                    Utils.CurrTrackInfo.TrackPosInPlaylist = i;
                    rvBar.scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);

                    if (Utils.CurrTrackInfo.currPlaylistTracks.get(Utils.CurrTrackInfo.TrackPosInPlaylist).isLiked()) {
                        ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    } else {
                        ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }
                    playTrack();
                    if (playlistFragment.isVisible()) {
                        playlistFragment.changeSelected(Utils.CurrTrackInfo.prevTrackPos, Utils.CurrTrackInfo.TrackPosInPlaylist);
                    }
                    return;
                }
            }
            Utils.MediaPlayerInfo.clearMediaPlayer();
            if (playlistFragment.isVisible()) {
                playlistFragment.changeSelected(Utils.CurrTrackInfo.TrackPosInPlaylist, -1);
            }
            updatePlayBar();
        }
    };

    @Override
    public void OnLikeClickedListener(boolean selected, int pos) {

        if (selected && pos == Utils.CurrTrackInfo.TrackPosInPlaylist) {
            ivIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            ivIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }
    }

    /**
     * this function takes info on track and set it to Utils.CurrTrackInfo
     *
     * @param currPlayingPos
     * @param TrackPosInPlaylist
     * @param currPlaylistTracks
     */
    private void setTrackInfo(int currPlayingPos, int TrackPosInPlaylist, ArrayList<Track> currPlaylistTracks) {
        Utils.CurrTrackInfo.currPlayingPos = currPlayingPos;
        Utils.CurrTrackInfo.currPlaylistName = currPlaylistTracks.get(TrackPosInPlaylist).getPlaylistName();
        Utils.CurrTrackInfo.track = currPlaylistTracks.get(TrackPosInPlaylist);
        Utils.CurrTrackInfo.TrackPosInPlaylist = TrackPosInPlaylist;
        Utils.CurrTrackInfo.currPlaylistTracks = currPlaylistTracks;
    }


    @Override
    public void OnPlaylistClickedListener(Playlist playlist) {
        playlistFragment = new PlaylistFragment(playlist);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.nav_host_fragment, playlistFragment
                , this.getString(R.string.playlist_fragment))
                .addToBackStack(null)
                .commit();

        // add tracks of playlist to play bar recycler view
        barAdapter = new RvBarAdapter(this, playlist.getTracks());

        rvBar.setAdapter(barAdapter);

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

    @Override
    public void OnItemClickedListener(ArrayList<Track> tracks, int pos) {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
    }

    /**
     * this function initialize BottomNavigationView
     */
    private void initBottomNavView() {
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // menu should be considered as top level destinations.
        NavHostFragment navHostFragment = NavHostFragment.create(R.navigation.mobile_navigation);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, navHostFragment)
                .setPrimaryNavigationFragment(navHostFragment)
                .commit();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new HomeFragment())
                                .commit();
                        return true;
                    case R.id.navigation_library:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new LibraryFragment())
                                .commit();
                        return true;
                    case R.id.navigation_search:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new SearchFragment())
                                .commit();
                        return true;
                    case R.id.navigation_premium:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, new PremiumFragment())
                                .commit();
                        return true;
                }
                return false;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        //check if user online
        if (!isOnline()) {
            connectToInternet();
        } else {
            //TODO load data from internet here and send it to fragments
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public void checkUserType() {
        if(Constants.currentUser.isListenerType())
            Toast.makeText(this, "Listener", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Artist", Toast.LENGTH_SHORT).show();

        //ServiceController serviceController = ServiceController.getInstance();
        //serviceController.logIn(this, "user1@symphonia.com", "12345678", true);
    }


}
