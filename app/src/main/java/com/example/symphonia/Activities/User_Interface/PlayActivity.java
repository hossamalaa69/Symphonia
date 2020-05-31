package com.example.symphonia.Activities.User_Interface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.RvTracksPlayActivityAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Helpers.AdDialog;
import com.example.symphonia.Helpers.SnapHelperOneByOne;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.MediaController;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Activity that accessing tracks and playing them
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class PlayActivity extends AppCompatActivity implements Serializable, RvTracksPlayActivityAdapter.OnItemSwitched
    , RestApi.updateUiPlaylists{

    ArrayList<Track> tracks;
    RecyclerView rvTracks;
    RecyclerView.LayoutManager layoutManager;
    RvTracksPlayActivityAdapter adapterPlayActivity;
    TextView trackTitle;
    TextView trackArtist;
    TextView playlistTitle;
    SeekBar seekBar;
    TextView seeKBarRemain;
    TextView seekBarCurr;
    ImageButton closeActivity;
    ImageButton trackSettings;
    FrameLayout frameLayout;
    ImageButton nextBtn;
    ImageButton prevBtn;
    ImageButton hideBtn;
    ImageButton likeBtn;
    private Drawable trackBackgroun;
    private Handler mHandler = new Handler();
    private MediaController mediaController;
    ImageView playBtn;
    ImageView pauseBtn;
    private final String IS_PAUSED = "isPaused";
    /**
     * holds position of current track
     */
    int trackPos;
    private ProgressBar progressBar;

    @Override
    public void getCategoriesSuccess() {

    }

    @Override
    public void getCurrPlayingTrackSuccess(String id) {
        int pos = Utils.getPosInPlaying(id);
        if (Constants.DEBUG_STATUS || pos>-1) {
            Utils.currTrack = Utils.playPlaylist.getTracks().get(pos);
            playTrack();
        }
        else
            ServiceController.getInstance().getTrack(PlayActivity.this,id);
    }

    @Override
    public void updateUiNoTracks(PlaylistFragment playlistFragment) {

    }

    @Override
    public void updateUiGetTracksOfPlaylist(PlaylistFragment playlistFragment) {

    }

    @Override
    public void updateUiGetPopularPlaylistsSuccess() {

    }

    @Override
    public void updateUiGetPopularPlaylistsFail() {

    }

    @Override
    public void updateUiGetRandomPlaylistsSuccess(HomeFragment homeFragment) {

    }

    @Override
    public void updateUiGetRandomPlaylistsFail() {

    }

    @Override
    public void updateUiGetRecentPlaylistsSuccess(HomeFragment homeFragment) {

    }

    @Override
    public void updateUiGetRecentPlaylistsFail() {

    }

    @Override
    public void updateUiGetMadeForYouPlaylistsSuccess() {

    }

    @Override
    public void updateUiGetMadeForYouPlaylistsFail() {

    }

    @Override
    public void updateUiPlayTrack() {
        ServiceController serviceController = ServiceController.getInstance();
        serviceController.getQueue(PlayActivity.this);
    }

    @Override
    public void getTrackSuccess() {
        startTrack();
    }

    private void startTrack() {
        Utils.currContextId = Utils.currTrack.getPlayListId();
        if (mediaController.isMediaPlayerPlaying())
            mediaController.releaseMedia();
        ServiceController.getInstance().playTrack(this);
    }

    @Override
    public void updateUiGetQueue() {
        Log.e("main update", "call play track");
        if (!Utils.CurrTrackInfo.loading)
            playTrack();
    }

    @Override
    public void getTrackOfQueue() {

    }

    /**
     * this function is called when track is switched
     *
     * @param pos position of current track
     */
    @Override
    public void OnItemSwitchedListener(int pos) {
        int prev = Utils.getPosInPlaying(Utils.playPlaylist.getTracks().get(pos).getId());
        if(pos - prev > 0) playNextTrack();
        else playPrevTrack();
        if (Utils.playPlaylist.getTracks().get(pos).isHidden()) {
            hideBtn.setImageResource(R.drawable.ic_do_not_disturb_on_red_24dp);
        } else
            hideBtn.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
        if (Utils.playPlaylist.getTracks().get(pos).isLiked())
            likeBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
        else
            likeBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        Utils.setTrackInfo(0, pos, tracks);
        trackPos = pos;
    }

    private void playPrevTrack() {
        ServiceController.getInstance().playPrev(this);
    }

    /**
     * this function start a service to play audio
     */
    private void playTrack() {
        resetSeekBar();
        Intent intent = new Intent(this, MediaController.class);
        intent.setAction(MediaController.ACTION_PLAY);
        Log.e("PlayActivity", "play track     " + i++);
        Utils.CurrTrackInfo.paused = false;
        frameLayout.removeAllViews();
        frameLayout.addView(progressBar);
        startService(intent);
    }

    @Override
    public void updateUicheckSaved(PlaylistFragment playlistFragment) {

    }

    /**
     * this function resets data of seek bar
     */
    private void resetSeekBar() {
        mediaController.releaseMedia();
        seekBar.setProgress(0);
        seekBarCurr.setText(String.valueOf(0));
        seeKBarRemain.setText(String.valueOf(0));
    }

    /**
     * this function updates play button
     */
    private void updatePlayBtn() {
        frameLayout.removeAllViews();
        frameLayout.addView(pauseBtn);
        Utils.CurrTrackInfo.paused = false;
    }

    /**
     * Represents the initialization of activity
     *
     * @param savedInstanceState represents received data from other activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        checkAds();
        Utils.CurrTrackInfo.paused = getIntent().getBooleanExtra(IS_PAUSED, false);
        mediaController = MediaController.getController();
        attachViews();
        addListeners();
        readTrackData();
        trackBackgroun = getResources().getDrawable(R.drawable.background);
        updateScreen();
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvTracks.setLayoutManager(layoutManager);
        adapterPlayActivity = new RvTracksPlayActivityAdapter(this, tracks);
        rvTracks.setAdapter(adapterPlayActivity);
        rvTracks.setHasFixedSize(true);
        layoutManager.scrollToPosition(trackPos);
        // add the recycler view to the snapHelper
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvTracks);

        MediaController.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNextTrack();
            }
        });
        mediaController.setMediaPlayCompletionService();

        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mediaController.isMediaNotNull() && mediaController.isMediaPlayerPlaying()) {
                    seekBar.setMax(Utils.CurrTrackInfo.track.getmDuration() / 1000);
                    int mCurrentPosition = mediaController.getCurrentPosition();
                    seekBar.setProgress(mCurrentPosition / 1000);
                    seekBarCurr.setText((mCurrentPosition / 60000+":"+(mCurrentPosition / 1000)%60));
                    seeKBarRemain.setText("-"+ (Utils.currTrack.getmDuration() - mCurrentPosition)/60000
                    +":"+((Utils.currTrack.getmDuration() - mCurrentPosition)/1000)%60);
                } else if (!mediaController.isMediaNotNull()) {
                    seekBar.setMax(0);
                    seekBar.setProgress(0);
                    seekBarCurr.setText(String.valueOf(0));
                    seeKBarRemain.setText(String.valueOf(0));
                }

                if (mediaController.isMediaNotNull() && mediaController.isMediaPlayerPlaying()) {
                   updatePlayBtn();
                }
                mHandler.postDelayed(this, 1000);
            }
        });
    }

    /**
     * this function gets the next not hidden track
     */
    private void playNextTrack() {
        Log.e("PlayActivity", "play next track     " + i++);
        ServiceController.getInstance().playNext(this);
/*
        if (Utils.CurrTrackInfo.TrackPosInPlaylist < Utils.CurrTrackInfo.currPlaylistTracks.size() - 1) {
            for (int i = Utils.CurrTrackInfo.TrackPosInPlaylist + 1; i < Utils.CurrTrackInfo.currPlaylistTracks.size(); i++) {
                trackPos = i;
                if (!Utils.CurrTrackInfo.currPlaylistTracks.get(i).isHidden() && !Utils.CurrTrackInfo.currPlaylistTracks.get(i).isLocked()
                        && !Constants.currentUser.isPremuim()) {
                    break;
                }
            }

            if (trackPos > Utils.CurrTrackInfo.currPlaylistTracks.size() - 1) {
                frameLayout.removeAllViews();
                frameLayout.addView(playBtn);
                Utils.CurrTrackInfo.paused = true;
                seekBar.setProgress(0);
                seekBarCurr.setText(String.valueOf(0));
                seeKBarRemain.setText(String.valueOf(Utils.CurrTrackInfo.track.getmDuration() / 1000));
                mediaController.releaseMedia();
                return;
            }
            Utils.CurrTrackInfo.TrackPosInPlaylist = trackPos;
            layoutManager.scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
            Utils.setTrackInfo(0, Utils.CurrTrackInfo.TrackPosInPlaylist, tracks);
            updateScreen();
            playTrack();
            Utils.CurrTrackInfo.paused = false;
            return;
        } else {
            frameLayout.removeAllViews();
            frameLayout.addView(playBtn);
            Utils.CurrTrackInfo.paused = true;
            seekBar.setProgress(0);
            seekBarCurr.setText(String.valueOf(0));
            seeKBarRemain.setText(String.valueOf(Utils.CurrTrackInfo.track.getmDuration() / 1000));
            mediaController.releaseMedia();
        }*/

        updateScreen();
    }

    /**
     * add listeners to views (playBtn , nextBtn , prevBtn , likeBtn ,hideBtn )
     */
    private void addListeners() {
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaController.isMediaNotNull()) {
                    playTrack();
                    frameLayout.removeAllViews();
                    frameLayout.addView(pauseBtn);
                    Utils.CurrTrackInfo.paused = false;

                } else if (!Utils.CurrTrackInfo.paused) {
                    Utils.CurrTrackInfo.paused = true;
                    Utils.CurrTrackInfo.currPlayingPos = mediaController.getCurrentPosition();
                    mediaController.pauseMedia();
                    frameLayout.removeAllViews();
                    frameLayout.addView(playBtn);
                } else {
                    mediaController.resumeMedia();
                    frameLayout.removeAllViews();
                    frameLayout.addView(pauseBtn);
                    Utils.CurrTrackInfo.paused = false;
                }

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              playNextTrack();
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.CurrTrackInfo.TrackPosInPlaylist > 0) {
                    Utils.CurrTrackInfo.TrackPosInPlaylist--;
                }
                frameLayout.removeAllViews();
                frameLayout.addView(pauseBtn);
                Utils.CurrTrackInfo.paused = false;
                trackPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
                layoutManager.scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
                Utils.setTrackInfo(0, Utils.CurrTrackInfo.TrackPosInPlaylist, tracks);
                updateScreen();
                playTrack();
            }
        });
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.CurrTrackInfo.track.isLiked()) {
                    Utils.CurrTrackInfo.track.setLiked(false);
                    likeBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    Utils.CurrTrackInfo.track.setLiked(true);
                    likeBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        });
        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.CurrTrackInfo.currPlaylistTracks.get(Utils.CurrTrackInfo.TrackPosInPlaylist).setHidden(true);
                playNextTrack();
            }
        });
        closeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        trackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO handle btn here
                Toast.makeText(PlayActivity.this, "Settings", Toast.LENGTH_SHORT).show();

            }
        });

    }

    ConstraintLayout constraintLayout;
    int i = 0;

    /**
     * this function updates views with incoming data
     */
    private void updateScreen() {

        Log.e("PlayActivity", "update screen     " + i++);

        trackTitle.setText(tracks.get(trackPos).getmTitle());
        trackArtist.setText(tracks.get(trackPos).getmDescription());
        playlistTitle.setText(tracks.get(trackPos).getPlaylistName());
        if (layoutManager != null)
            layoutManager.scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
        // change background color according to track image
        constraintLayout = findViewById(R.id.background_play_activity);
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable drawable = Utils.createBackground(PlayActivity.this, bitmap);
                // transition drawable controls the animation ov changing background
                TransitionDrawable td = new TransitionDrawable(new Drawable[]{trackBackgroun, drawable});
                trackBackgroun = drawable;
                constraintLayout.setBackground(td);
                td.startTransition(500);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        if (!Constants.DEBUG_STATUS)
            Picasso.get()
                    .load(tracks.get(trackPos).getImageUrl())
                    .into(target);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable drawable = Utils.createBackground(this, tracks.get(trackPos).getmImageResources());
            // transition drawable controls the animation ov changing background
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{trackBackgroun, drawable});
            trackBackgroun = drawable;
            constraintLayout.setBackground(td);
            td.startTransition(500);
        }
        //update seekbar position
        if (mediaController.isMediaNotNull()) {
            if (mediaController.isMediaPlayerPlaying()) {
                frameLayout.removeAllViews();
                frameLayout.addView(pauseBtn);
            } else {
                frameLayout.removeAllViews();
                frameLayout.addView(playBtn);
            }
            if (Utils.CurrTrackInfo.track.isLiked()) {
                likeBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else likeBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            seekBar.setProgress(0);
            seekBar.setMax(Utils.CurrTrackInfo.track.getmDuration() / 1000);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                seekBar.setProgress(mediaController.getCurrentPosition() / 1000, true);
            } else {
                seekBar.setProgress(mediaController.getCurrentPosition() / 1000);
            }

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (mediaController.isMediaNotNull() && fromUser) {
                        mediaController.seekTo(progress * 1000);
                    }
                }
            });

            //Make sure you update SeekBar on UI thread
        }

    }


    /**
     * this function reads track info
     */
    private void readTrackData() {
        trackPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
        tracks = Utils.CurrTrackInfo.currPlaylistTracks;
    }

    /**
     * this function bind views to variables
     */
    private void attachViews() {
        playBtn = new ImageView(this);
        playBtn.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        pauseBtn = new ImageView(this);
        pauseBtn.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        progressBar =  new ProgressBar(this);

        rvTracks = findViewById(R.id.rv_playlist_play_activity);
        trackArtist = findViewById(R.id.tv_track_artist);
        trackTitle = findViewById(R.id.tv_track_title_play_activity);
        playlistTitle = findViewById(R.id.tv_playlist_title_play_activity);
        closeActivity = findViewById(R.id.iv_close_play_activity);
        trackSettings = findViewById(R.id.iv_track_settings_play_activity);
        frameLayout = findViewById(R.id.play_btn_controller_play_activity);
        nextBtn = findViewById(R.id.iv_next_track_playActivity);
        prevBtn = findViewById(R.id.iv_prev_track_playActivity);
        hideBtn = findViewById(R.id.iv_hide_track_playActivity);
        likeBtn = findViewById(R.id.iv_like_track_playActivity);
        seekBar = findViewById(R.id.seek_bar_play_activity);
        seeKBarRemain = findViewById(R.id.tv_remain_secs_play_activity);
        seekBarCurr = findViewById(R.id.tv_curr_sec_play_activity);

    }

    public void checkAds() {
        if (Constants.currentUser.isPremuim()) {
            Intent i = new Intent(this, AdDialog.class);
            startActivity(i);
        }
    }

}
