package com.example.symphonia.Activities.User_Interface;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.example.symphonia.Helpers.AdDialog;
import com.example.symphonia.Helpers.SnapHelperOneByOne;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.MediaController;
import com.example.symphonia.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Activity that accessing tracks and playing them
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class PlayActivity extends AppCompatActivity implements Serializable, RvTracksPlayActivityAdapter.OnItemSwitched {

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
    ImageButton playBtn;
    ImageButton nextBtn;
    ImageButton prevBtn;
    ImageButton hideBtn;
    ImageButton likeBtn;
    private Drawable trackBackgroun;
    private Handler mHandler = new Handler();
    private MediaController mediaController;

    private final String IS_PAUSED = "isPaused";
    /**
     * holds position of current track
     */
    int trackPos;


    /**
     * this function is called when track is switched
     *
     * @param pos position of current track
     */
    @Override
    public void OnItemSwitchedListener(int pos) {
        if (Utils.CurrTrackInfo.currPlaylistTracks.get(pos).isHidden()) {
            hideBtn.setImageResource(R.drawable.ic_do_not_disturb_on_red_24dp);
        } else
            hideBtn.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
        if (Utils.CurrTrackInfo.currPlaylistTracks.get(pos).isLiked())
            likeBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
        else
            likeBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        Utils.setTrackInfo(0, pos, tracks);
        trackPos = pos;
        playTrack();
        updateScreen();
        updatePlayBtn();
    }

    /**
     * this function start a service to play audio
     */
    private void playTrack() {
        Intent intent = new Intent(this, MediaController.class);
        intent.setAction(MediaController.ACTION_PLAY);
        Log.e("PlayActivity", "play track     " + i++);
        Utils.CurrTrackInfo.paused = false;
        startService(intent);
    }

    /**
     * this function updates play button
     */
    private void updatePlayBtn() {
        playBtn.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
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

    }

    /**
     * this function gets the next not hidden track
     */
    private void playNextTrack() {
        Log.e("PlayActivity", "play next track     " + i++);

        if (Utils.CurrTrackInfo.TrackPosInPlaylist < Utils.CurrTrackInfo.currPlaylistTracks.size() - 1) {
            for (int i = Utils.CurrTrackInfo.TrackPosInPlaylist + 1; i < Utils.CurrTrackInfo.currPlaylistTracks.size(); i++) {
                trackPos = i;
                if (!Utils.CurrTrackInfo.currPlaylistTracks.get(i).isHidden() && !Utils.CurrTrackInfo.currPlaylistTracks.get(i).isLocked()
                        && !Constants.currentUser.isPremuim()) {
                    break;
                }
            }

            if (trackPos > Utils.CurrTrackInfo.currPlaylistTracks.size() - 1) {
                playBtn.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                Utils.CurrTrackInfo.paused = true;
                seekBar.setProgress(0);
                seekBarCurr.setText(String.valueOf(0));
                seeKBarRemain.setText(String.valueOf(mediaController.getDuration() / 1000));
                mediaController.releaseMedia();
                return;
            }
            Utils.CurrTrackInfo.TrackPosInPlaylist = trackPos;
            layoutManager.scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
            Utils.setTrackInfo(0, Utils.CurrTrackInfo.TrackPosInPlaylist, tracks);
            playTrack();
            updateScreen();
            updatePlayBtn();
            return;
        } else {
            playBtn.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
            Utils.CurrTrackInfo.paused = true;
            seekBar.setProgress(0);
            seekBarCurr.setText(String.valueOf(0));
            seeKBarRemain.setText(String.valueOf(mediaController.getDuration() / 1000));
            mediaController.releaseMedia();
        }
        updateScreen();
    }

    /**
     * add listeners to views (playBtn , nextBtn , prevBtn , likeBtn ,hideBtn )
     */
    private void addListeners() {
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaController.isMediaNotNull()) {
                    playTrack();
                    playBtn.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    Utils.CurrTrackInfo.paused = false;

                } else if (!Utils.CurrTrackInfo.paused) {
                    Utils.CurrTrackInfo.paused = true;
                    mediaController.pauseMedia();
                    playBtn.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                } else {
                    mediaController.resumeMedia();
                    playBtn.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    Utils.CurrTrackInfo.paused = false;
                }

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.CurrTrackInfo.TrackPosInPlaylist < Utils.CurrTrackInfo.currPlaylistTracks.size() - 1) {
                    Utils.CurrTrackInfo.TrackPosInPlaylist++;
                }
                playBtn.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                Utils.CurrTrackInfo.paused = false;
                trackPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
                layoutManager.scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
                Utils.setTrackInfo(0, Utils.CurrTrackInfo.TrackPosInPlaylist, tracks);
                playTrack();
                updateScreen();
                updatePlayBtn();

            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.CurrTrackInfo.TrackPosInPlaylist > 0) {
                    Utils.CurrTrackInfo.TrackPosInPlaylist--;
                }
                playBtn.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                Utils.CurrTrackInfo.paused = false;
                trackPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
                layoutManager.scrollToPosition(Utils.CurrTrackInfo.TrackPosInPlaylist);
                Utils.setTrackInfo(0, Utils.CurrTrackInfo.TrackPosInPlaylist, tracks);
                playTrack();
                updateScreen();
                updatePlayBtn();

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
        ConstraintLayout constraintLayout = findViewById(R.id.background_play_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
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
                playBtn.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
            } else {
                playBtn.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
            }
            if (Utils.CurrTrackInfo.track.isLiked()) {
                likeBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else likeBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            seekBar.setProgress(0);
            seekBar.setMax(mediaController.getDuration() / 1000);
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
            this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mediaController.isMediaNotNull() && mediaController.isMediaPlayerPlaying()) {
                        seekBar.setMax(mediaController.getDuration() / 1000);
                        int mCurrentPosition = mediaController.getCurrentPosition();
                        seekBar.setProgress(mCurrentPosition / 1000);
                        seekBarCurr.setText(String.valueOf(mCurrentPosition / 1000));
                        seeKBarRemain.setText(String.valueOf(-(
                                mediaController.getDuration() - mCurrentPosition) / 1000));
                    } else {
                        seekBar.setMax(0);
                        seekBar.setProgress(0);
                        seekBarCurr.setText(String.valueOf(0));
                        seeKBarRemain.setText(String.valueOf(0));
                    }

                    if (!Utils.CurrTrackInfo.paused) {
                        updatePlayBtn();
                    } else {
                        playBtn.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                    }
                    mHandler.postDelayed(this, 500);
                }
            });

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
        rvTracks = findViewById(R.id.rv_playlist_play_activity);
        trackArtist = findViewById(R.id.tv_track_artist);
        trackTitle = findViewById(R.id.tv_track_title_play_activity);
        playlistTitle = findViewById(R.id.tv_playlist_title_play_activity);
        closeActivity = findViewById(R.id.iv_close_play_activity);
        trackSettings = findViewById(R.id.iv_track_settings_play_activity);
        playBtn = findViewById(R.id.iv_play_track_playActivity);
        nextBtn = findViewById(R.id.iv_next_track_playActivity);
        prevBtn = findViewById(R.id.iv_prev_track_playActivity);
        hideBtn = findViewById(R.id.iv_hide_track_playActivity);
        likeBtn = findViewById(R.id.iv_like_track_playActivity);
        seekBar = findViewById(R.id.seek_bar_play_activity);
        seeKBarRemain = findViewById(R.id.tv_remain_secs_play_activity);
        seekBarCurr = findViewById(R.id.tv_curr_sec_play_activity);

    }

    public void checkAds() {
        if (!Constants.currentUser.isPremuim()) {
            Intent i = new Intent(this, AdDialog.class);
            startActivity(i);
        }
    }

}
