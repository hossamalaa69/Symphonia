package com.example.symphonia.Activities.User_Interface;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.example.symphonia.Helpers.MediaHelpers.MediaController;
import com.example.symphonia.Helpers.MediaHelpers.OnClearFromRecentService;
import com.example.symphonia.Helpers.MediaHelpers.PlayBarNotification;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.example.symphonia.Helpers.MediaHelpers.playable;
import com.example.symphonia.Service.updateUiPlaylists;
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
        , updateUiPlaylists
        , MediaController.OnStartListener
        , playable {


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
    public void onStartListener() {
        frameLayout.removeAllViews();
        frameLayout.addView(pauseBtn);
        frameLayout.setOnClickListener(playBtnListener);
    }

    @Override
    public void getCategoriesSuccess() {

    }

    /**
     * listener of notification
     */
    @Override
    public void onTrackPrev() {
        playPrevTrack();
    }

    /**
     * listener of notification
     */
    @Override
    public void onTrackPlay() {

        PlayBarNotification.PlayBarNotification(PlayActivity.this
                , Utils.currTrack
                , R.drawable.ic_baseline_pause_24
                , Utils.getPosInPlaying(Utils.currTrack.getId())
                , Utils.playingContext.getTracks().size() - 1);
        frameLayout.removeAllViews();
        frameLayout.addView(pauseBtn);
        frameLayout.setOnClickListener(playBtnListener);
    }

    /**
     * listener of notification
     */
    @Override
    public void onTrackPause() {
        PlayBarNotification.PlayBarNotification(PlayActivity.this
                , Utils.currTrack
                , R.drawable.ic_baseline_play_arrow_24
                , Utils.getPosInPlaying(Utils.currTrack.getId())
                , Utils.playingContext.getTracks().size() - 1);
        frameLayout.removeAllViews();
        frameLayout.addView(playBtn);
        frameLayout.setOnClickListener(playBtnListener);
    }

    /**
     * listener of notification
     */
    @Override
    public void onTrackNext() {
        playNextTrack();
    }

    @Override
    public void getCurrPlayingTrackSuccess(String id) {
        int pos = Utils.getPosInPlaying(id);
        if (Constants.DEBUG_STATUS || pos > -1) {
            if (!Utils.playingContext.getTracks().get(pos).isHidden()
                    && !Utils.playingContext.getTracks().get(pos).isLocked()) {
                Utils.currTrack = Utils.playingContext.getTracks().get(pos);
                playTrack();
            } else {
                playNextTrack();
            }
        } else
            ServiceController.getInstance().getTrack(PlayActivity.this, id);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(android.content.Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            if (action.matches(PlayBarNotification.CHANNEL_NEXT)) {
                onTrackNext();
            } else if (action.matches(PlayBarNotification.CHANNEL_PLAY)) {
               if (!Utils.currTrack.isPlaying())
                    onTrackPause();
               else
                   onTrackPlay();

            } else if (action.matches(PlayBarNotification.CHANNEL_PREV)) {
                onTrackPrev();
            }
        }
    };

    @Override
    public void getCurrPlayingTrackFailed() {
        MediaController.getController().releaseMedia();
        resetSeekBar();
        frameLayout.removeAllViews();
        frameLayout.addView(playBtn);
        frameLayout.setOnClickListener(playBtnListener);
        onTrackPause();
    }

    @Override
    public void updateUiNoTracks(PlaylistFragment playlistFragment) {

    }

    @Override
    public void updateUiGetTracksOfPlaylist(PlaylistFragment playlistFragment, ArrayList<Track> tracksList) {

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
    boolean hardScroll = false;

    /**
     * this function is called when track is switched
     *
     * @param pos position of current track
     */
    @Override
    public void OnItemSwitchedListener(int pos) {
        if(hardScroll){hardScroll = false; return;}
        if (Utils.playingContext.getTracks().get(pos).isHidden()) {
            return;
        } else
            hideBtn.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
        int prev = Utils.getPosInPlaying(Utils.currTrack.getId());
        if (pos - prev > 0) playNextTrack();
        else
            playPrevTrack();
    }

    private void playPrevTrack() {
        frameLayout.removeAllViews();
        frameLayout.addView(progressBar);
        frameLayout.setOnClickListener(null);
        ServiceController.getInstance().playPrev(this);
    }

    /**
     * this function start a service to play audio
     */
    private void playTrack() {
        resetSeekBar();
        Intent intent = new Intent(this, MediaController.class);
        intent.setAction(MediaController.ACTION_PLAY);
        Log.e("PlayActivity", "play track");
        startService(intent);
        onTrackPlay();
        updateScreen();
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
        registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        checkAds();
        Utils.CurrTrackInfo.paused = getIntent().getBooleanExtra(IS_PAUSED, false);
        mediaController = MediaController.getController();
        attachViews();
        addListeners();
        trackBackgroun = getResources().getDrawable(R.drawable.background);
        updateScreen();
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvTracks.setLayoutManager(layoutManager);
        adapterPlayActivity = new RvTracksPlayActivityAdapter(this, Utils.playingContext.getTracks());
        rvTracks.setAdapter(adapterPlayActivity);
        hardScroll = true;
        rvTracks.getLayoutManager().scrollToPosition(Utils.getPosInPlaying(Utils.currTrack.getId()));
        // add the recycler view to the snapHelper
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvTracks);
        if (MediaController.getController() != null &&
                MediaController.getController().isMediaPlayerPlaying()) {
            frameLayout.removeAllViews();
            frameLayout.addView(pauseBtn);
            frameLayout.setOnClickListener(playBtnListener);
        } else if (MediaController.getController() != null &&
                !MediaController.getController().isMediaPlayerPlaying()) {
            frameLayout.removeAllViews();
            frameLayout.addView(playBtn);
            frameLayout.setOnClickListener(playBtnListener);
        } else {
            frameLayout.removeAllViews();
            frameLayout.addView(progressBar);
            frameLayout.setOnClickListener(null);
        }
        MediaController.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNextTrack();
                frameLayout.removeAllViews();
                frameLayout.addView(progressBar);
                frameLayout.setOnClickListener(null);
            }
        });
        mediaController.setMediaPlayCompletionService();

        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mediaController.isMediaNotNull() && mediaController.isMediaPlayerPlaying()) {
                    seekBar.setMax(Utils.currTrack.getmDuration() / 1000);
                    int mCurrentPosition = mediaController.getCurrentPosition();
                    seekBar.setProgress(mCurrentPosition / 1000);
                    seekBarCurr.setText((mCurrentPosition / 60000 + ":" + (mCurrentPosition / 1000) % 60));
                    seeKBarRemain.setText("-" + (Utils.currTrack.getmDuration() - mCurrentPosition) / 60000
                            + ":" + ((Utils.currTrack.getmDuration() - mCurrentPosition) / 1000) % 60);
                } else if (!mediaController.isMediaNotNull()) {
                    seekBar.setMax(0);
                    seekBar.setProgress(0);
                    seekBarCurr.setText(String.valueOf(0));
                    seeKBarRemain.setText(String.valueOf(0));
                }
                mHandler.postDelayed(this, 1000);
            }
        });
    }

    /**
     * this function gets the next not hidden track
     */
    private void playNextTrack() {
        Log.e("PlayActivity", "play next track");
        ServiceController.getInstance().playNext(this);
        frameLayout.removeAllViews();
        frameLayout.addView(progressBar);
        frameLayout.setOnClickListener(null);
    }

    private View.OnClickListener playBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!mediaController.isMediaNotNull()) {
                playTrack();
                frameLayout.removeAllViews();
                frameLayout.addView(pauseBtn);
                Utils.CurrTrackInfo.paused = false;

            } else if (!Utils.CurrTrackInfo.paused) {
                Utils.CurrTrackInfo.paused = true;
                onTrackPause();
                Utils.CurrTrackInfo.currPlayingPos = mediaController.getCurrentPosition();
                mediaController.pauseMedia();
                frameLayout.removeAllViews();
                frameLayout.addView(playBtn);
            } else {
                onTrackPlay();
                mediaController.resumeMedia();
                frameLayout.removeAllViews();
                frameLayout.addView(pauseBtn);
                Utils.CurrTrackInfo.paused = false;
            }

        }
    };

    /**
     * add listeners to views (playBtn , nextBtn , prevBtn , likeBtn ,hideBtn )
     */
    private void addListeners() {
        frameLayout.setOnClickListener(playBtnListener);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNextTrack();
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPrevTrack();
            }
        });
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.currTrack.isLiked()) {
                    Utils.currTrack.setLiked(false);
                    Utils.playingContext.getTracks().get(Utils.getPosInPlaying(Utils.currTrack.getId())).setLiked(false);
                    likeBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    Utils.currTrack.setLiked(true);
                    Utils.playingContext.getTracks().get(Utils.getPosInPlaying(Utils.currTrack.getId())).setLiked(true);
                    ServiceController.getInstance().removeFromSaved(PlayActivity.this,Utils.currTrack.getId());
                    likeBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        });
        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.playingContext.getTracks().get(Utils.getPosInPlaying(Utils.currTrack.getId())).setHidden(true);
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

    /**
     * this function updates views with incoming data
     */
    private void updateScreen() {

        Log.e("PlayActivity", "update screen");

        trackTitle.setText(Utils.currTrack.getmTitle());
        trackArtist.setText(Utils.currTrack.getmDescription());
        playlistTitle.setText(Utils.currTrack.getPlaylistName());
        frameLayout.removeAllViews();
        frameLayout.addView(pauseBtn);
        frameLayout.setOnClickListener(playBtnListener);
        if (layoutManager != null) {
            hardScroll = true;
            rvTracks.getLayoutManager().scrollToPosition(Utils.getPosInPlaying(Utils.currTrack.getId()));
        }
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
                    .load(Utils.currTrack.getImageUrl())
                    .into(target);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable drawable = Utils.createBackground(this, Utils.currTrack.getmImageResources());
            // transition drawable controls the animation ov changing background
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{trackBackgroun, drawable});
            trackBackgroun = drawable;
            constraintLayout.setBackground(td);
            td.startTransition(500);
        }
        //update seekbar position
        if (mediaController.isMediaNotNull()) {
            if (Utils.currTrack.isLiked()) {
                likeBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else likeBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            seekBar.setProgress(0);
            seekBar.setMax(Utils.currTrack.getmDuration() / 1000);
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
     * this function bind views to variables
     */
    private void attachViews() {
        playBtn = new ImageView(this);
        playBtn.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        pauseBtn = new ImageView(this);
        pauseBtn.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        progressBar = new ProgressBar(this);

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
