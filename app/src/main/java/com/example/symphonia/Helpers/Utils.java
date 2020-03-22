package com.example.symphonia.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import com.example.symphonia.Entities.Track;
import com.example.symphonia.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utils {

    public static String getNameFromEmail(String email){
        return email.split("@")[0];
    }


    public static class MediaPlayerInfo {
        public MediaPlayerInfo() {

        }

        private static MediaPlayer mediaPlayer;
        private static AudioManager audioManager;
        private static AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                        mediaPlayer.start();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        clearMediaPlayer();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        mediaPlayer.stop();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        mediaPlayer.pause();
                        CurrTrackInfo.currPlayingPos = mediaPlayer.getCurrentPosition();
                        break;

                }
            }

        };

        public static void createMediaPlayer(Context context) {
            mediaPlayer = MediaPlayer.create(context, CurrTrackInfo.track.getUri());
        }

        public static void playTrack(Context context) {
            clearMediaPlayer();
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int status = 0;
            if (audioManager != null) {
                status = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                createMediaPlayer(context);
                if (status == AudioManager.AUDIOFOCUS_REQUEST_GRANTED && mediaPlayer != null) {
                    mediaPlayer.seekTo(CurrTrackInfo.currPlayingPos);
                    mediaPlayer.start();

                }
            }
        }

        public static void clearMediaPlayer() {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;

            }
            if (audioManager != null) {
                audioManager.abandonAudioFocus(onAudioFocusChangeListener);

            }
        }

        public static void pauseTrack() {
            if(mediaPlayer!=null)
            {
                mediaPlayer.pause();
            }
        }

        public static void resumeTrack() {
            if(mediaPlayer!=null)
            {
                mediaPlayer.start();
            }
        }

        public static boolean isMediaPlayerPlaying() {
            return mediaPlayer.isPlaying();
        }
    }

    public static class CurrTrackInfo {
        public static int TrackPosInPlaylist;
        public static int TrackPosInAlbum;
        public static ArrayList<Track> currPlaylistTracks;
        public static String currPlaylistName;
        public static Track track;
        public static int currPlayingPos;
    }

    /**
     * check if string is email form or not
     *
     * @param target: input string
     * @return boolean
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static Bitmap convertToBitmap(int mImageResourceId) {
        return BitmapFactory.decodeResource(App.getContext().getResources(), mImageResourceId);
    }

    public static void hideKeyboard(AppCompatActivity activity, Context context) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public int add2(int x, int y) {
        return x + y;
    }

    /**
     * create gradient background for an image
     *
     * @return
     */
    public static Drawable createBackground(Context context, int ImageResources) {
        int color = getDominantColor(BitmapFactory.decodeResource(context.getResources()
                , ImageResources));

        SomeDrawable drawable = new SomeDrawable(color, Color.BLACK);
        return drawable;

    }

    public static Drawable createBackground(Context context, Bitmap ImageResources) {
        int color = getDominantColor(ImageResources);

        SomeDrawable2 drawable = new SomeDrawable2(color, Color.BLACK);
        return drawable;

    }

    public static Drawable createAlbumBackground(Context context, Bitmap ImageResources) {
        int color = getDominantColor(ImageResources);

        return new AlbumDrawable(color, ContextCompat.getColor(context, R.color.colorPrimary));

    }

    /**
     * gets the dominant color in a bitmap image
     *
     * @param bitmap
     * @return integer refers to the dominant color
     */
    private static int getDominantColor(Bitmap bitmap) {
        List<Palette.Swatch> swatchesTemp = Palette.from(bitmap).generate().getSwatches();
        List<Palette.Swatch> swatches = new ArrayList<Palette.Swatch>(swatchesTemp);
        Collections.sort(swatches, new Comparator<Palette.Swatch>() {
            @Override
            public int compare(Palette.Swatch swatch1, Palette.Swatch swatch2) {
                return swatch2.getPopulation() - swatch1.getPopulation();
            }
        });
        return swatches.size() > 0 ? swatches.get(0).getRgb() : 0;
    }

    /**
     * create gradient drawable for track image
     */
    private static class SomeDrawable extends GradientDrawable {

        private SomeDrawable(int pStartColor, int pEndColor) {
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pStartColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }

    private static class SomeDrawable2 extends GradientDrawable {

        private SomeDrawable2(int pStartColor, int pEndColor) {
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pEndColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }

    private static class AlbumDrawable extends  GradientDrawable{
        private AlbumDrawable(int pStartColor, int pEndColor){
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }
    }
}
