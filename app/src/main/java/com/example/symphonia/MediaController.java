package com.example.symphonia;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.symphonia.Helpers.Utils;

import java.io.IOException;

/**
 * this class controls media player
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class MediaController extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    public static final String ACTION_PLAY = "com.example.action.PLAY";
    private static MediaPlayer mediaPlayer = null;
    private static MediaPlayer.OnCompletionListener onCompletionListener;
    private static AudioManager audioManager;
    private static WifiManager.WifiLock wifiLock;

    /**
     * setter for completion listener
     *
     * @param onCompletionListener listener to be added to media player
     */
    public static void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        MediaController.onCompletionListener = onCompletionListener;
    }

    /**
     * static instance of class
     */
    private static final MediaController controller = new MediaController();

    /**
     * getter for class instance
     *
     * @return instance of class @MediaController
     */
    public static MediaController getController() {
        return controller;
    }

    /**
     * setter for media player completion listener
     */
    public void setMediaPlayCompletionService() {
        if (mediaPlayer != null)
            mediaPlayer.setOnCompletionListener(onCompletionListener);
    }

    /**
     * check if media is null
     *
     * @return if media is null
     */
    public boolean isMediaNotNull() {
        return mediaPlayer != null;
    }


    /**
     * listener of focus of audio
     */
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                    if (mediaPlayer != null)
                        mediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMedia();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                        Utils.CurrTrackInfo.currPlayingPos = mediaPlayer.getCurrentPosition();
                    }
                    break;

            }
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Called by the system every time a client explicitly starts the service by calling
     * Context.startService(Intent), providing the arguments it supplied and a unique
     * integer token representing the start request.
     *
     * @param intent  The Intent supplied to Context.startService(Intent), as given.
     *                This may be null if the service is being restarted after its process has gone away,
     *                and it had previously returned anything except START_STICKY_COMPATIBILITY.
     * @param flags   Additional data about this start request.
     *                Value is either 0 or a combination of START_FLAG_REDELIVERY, and START_FLAG_RETRY
     * @param startId A unique integer representing this specific request to start. Use with stopSelfResult(int).
     * @return return value indicates what semantics the system should use for the service's current started state.
     * It may be one of the constants associated with the START_CONTINUATION_MASK bits.
     * Value is START_STICKY_COMPATIBILITY, START_STICKY, START_NOT_STICKY, or START_REDELIVER_INTENT
     */
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null)
            if (ACTION_PLAY.equals(intent.getAction())) {
                setConfigrations();
            }
        return START_STICKY;
    }

    private void setConfigrations() {
        audioManager = (AudioManager) getApplicationContext().getSystemService(AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_REQUEST_GRANTED);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            stop();
            releaseMedia();
            initMediaPlayer();
            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            wifiLock = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE))
                    .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");
            wifiLock.acquire();
        }
    }

    /**
     * this function initialize media player with current track info
     */
    public void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        setMediaPlayCompletionService();
        try {
            mediaPlayer.setDataSource(getApplicationContext(), Utils.CurrTrackInfo.currPlaylistTracks.get(Utils.CurrTrackInfo.TrackPosInPlaylist).getUri());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepareAsync(); // prepare async to not block main thread
          //  makeToast(getApplicationContext().getResources().getString(R.string.preparing));

        } catch (IOException e) {
            e.printStackTrace();
            makeToast(getApplicationContext().getResources().getString(R.string.cant_play));

            mediaPlayer.reset();
        }

    }

    /**
     * this function pause media player
     */
    public void pauseMedia() {
        if (mediaPlayer != null)
            mediaPlayer.pause();
        if (wifiLock != null)
            try {
                wifiLock.release();
            } catch (RuntimeException e) {
            }
    }

    /**
     * this function resume media player
     */
    public void resumeMedia() {
        if (mediaPlayer != null)
            mediaPlayer.start();
    }

    /**
     * getter for media duration
     *
     * @return duration of media player
     */
    public int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    /**
     * getter for currnet position of media player
     *
     * @return
     */
    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    private Toast toast;

    private void makeToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * this function change current position of media player to ( @param pos)
     *
     * @param prog position of current position
     */
    public void seekTo(int prog) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(prog);
        }
    }

    /**
     * this function stop media player
     */
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
            releaseMedia();
        } else if (mediaPlayer != null) {
            mediaPlayer.reset();
            releaseMedia();
        }
        if (wifiLock != null)
            try {
                wifiLock.release();
            } catch (RuntimeException e) {
            }
    }

    /**
     * this function release data of media player
     */
    public void releaseMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            if (wifiLock != null)
                try {
                    wifiLock.release();
                } catch (RuntimeException e) {
                }
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }


    /**
     * listener for media player in case an error occurs
     *
     * @param mp    media player
     * @param what  type of error
     * @param extra extra data
     * @return weather the error is handled or not
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // ... react appropriately ...
        // The MediaPlayer has moved to the Error state, must be reset!
        mediaPlayer.reset();
        releaseMedia();
        setConfigrations();
        return true;
    }

    /**
     * Called when MediaPlayer is ready
     */
    public void onPrepared(MediaPlayer player) {
        player.start();
     //   makeToast(getApplicationContext().getResources().getString(R.string.started));
    }

    /**
     * called by the system when service is stopped
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (wifiLock != null)
            wifiLock.release();
    }

    /**
     * this function check if media player is playing
     *
     * @return if media player is playing
     */
    public boolean isMediaPlayerPlaying() {
        if (mediaPlayer != null)
            return mediaPlayer.isPlaying();
        return false;
    }
}