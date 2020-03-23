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

import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Class that holds many functions to be used in all classes
 *
 * @author All team members
 * @since 22-3-2020
 * @version 1.0
 */
public class Utils {

    /**
     *
     * extracts name from email
     * @param email email input from user
     * @return returns extracted name from this email
     *
     * @author Hossam Alaa
     * @since 22-3-2020
     * @version 1.0
     */
    public static String getNameFromEmail(String email) {
        return email.split("@")[0];
    }

    public static class MediaPlayerInfo {
        public static MediaPlayer mediaPlayer;
        private static AudioManager audioManager;
        private static AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                        if (mediaPlayer != null)
                            mediaPlayer.start();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        clearMediaPlayer();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        if (mediaPlayer != null) {
                            mediaPlayer.pause();
                            CurrTrackInfo.currPlayingPos = mediaPlayer.getCurrentPosition();
                        }
                        break;

                }
            }

        };

        public static void createMediaPlayer(Context context) {
            mediaPlayer = MediaPlayer.create(context, CurrTrackInfo.track.getUri());
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        public static MediaPlayer.OnCompletionListener onCompletionListener;

        public static void playTrack(Context context) {
            clearMediaPlayer();
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int status = 0;
            if (audioManager != null) {
                status = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC
                        , AudioManager.AUDIOFOCUS_GAIN);
                createMediaPlayer(context);
                if (status == AudioManager.AUDIOFOCUS_REQUEST_GRANTED && mediaPlayer != null) {
                    mediaPlayer.seekTo(CurrTrackInfo.currPlayingPos);
                    mediaPlayer.start();


                }
            }
        }

        public static void clearMediaPlayer() {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer = null;
            }
            if (audioManager != null) {
                audioManager.abandonAudioFocus(onAudioFocusChangeListener);

            }
        }

        public static void pauseTrack() {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }

        public static void resumeTrack() {
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        }

        public static boolean isMediaPlayerPlaying() {
            if (mediaPlayer != null)
                return mediaPlayer.isPlaying();
            return false;
        }
    }


    public static class CurrTrackInfo {
        public static int TrackPosInPlaylist = -1;
        public static int prevTrackPos = -1;
        public static ArrayList<Track> currPlaylistTracks;
        public static String currPlaylistName;
        public static Track track;
        public static int currPlayingPos;
    }


    /**
     * checks if string is in email form or not
     *
     * @param target input string to be checked
     * @return returns true if it's valid
     * @author Hossam Alaa
     * @since 22-3-2020
     * @version 1.0
     */
    public final static boolean isValidEmail(CharSequence target) {
        //checks if text is empty
        if (target == null) return false;
        //return validity of text as email matching with email's pattern(built-in library)
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * takes the image resource id and convert it
     * to a bitmap image
     *
     * @author islamahmed1092
     * @param mImageResourceId image resource id
     * @return the converted bitmap image
     */
    public static Bitmap convertToBitmap(int mImageResourceId) {
        return BitmapFactory.decodeResource(App.getContext().getResources(), mImageResourceId);
    }

    /**
     * hides the soft keyboard from the screen
     * if it's opened
     *
     * @author islamahmed1092
     * @param activity the current opened activity
     * @param context the context of the keyboard
     */
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

    /**
     *  create gradient background for SearchList layout
     * @author Mahmoud Amr Nabil
     * @param context
     * @param container container which has the data of the first item in recycler view
     * @return a drawable to be set as background of search list
     */
    public static Drawable createSearchListBackground(Context context, Container container) {
        int color = getDominantColor(BitmapFactory.decodeResource(context.getResources()
                , container.getImg_Res()));

        SomeDrawable3 drawable = new SomeDrawable3(color, Color.BLACK);
        return drawable;

    }

    /**
     * create gradient background for category layout
     * @author Mahmoud Amr Nabil
     *@param context
     *@param container container which has the data of category
     * @return a drawable to be set as background of category
     */
    public static Drawable createCategoryBackground(Context context,Container container) {
        int color = getDominantColor(BitmapFactory.decodeResource(context.getResources()
                , container.getImg_Res()));

        SomeDrawable4 drawable = new SomeDrawable4(color, Color.BLACK);
        return drawable;

    }


    /**
     * create a gradient background for the albums
     * based on the album photo dominant color
     *
     * @author islamahmed1092
     * @param context background context
     * @param ImageResources bitmap image
     * @return drawable gradient background
     */
    public static Drawable createAlbumBackground(Context context, Bitmap ImageResources) {
        int color = getDominantColor(ImageResources);

        return new AlbumDrawable(color, ContextCompat.getColor(context, R.color.colorPrimary));

    }

    /**
     * gets the dominant color in a bitmap image
     *
     * @param bitmap bitmap resource of the image
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

    /**
     * create gradient drawable for search result recycler view
     * @author Mahmoud Amr Nabil
     */
    private static class SomeDrawable3 extends GradientDrawable {

        public SomeDrawable3(int pStartColor, int pEndColor) {
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pEndColor,pEndColor,pStartColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }

    /**
     * create gradient drawable for category layout
     * @author Mahmoud Amr Nabil
     */
    private static class SomeDrawable4 extends GradientDrawable {

        public SomeDrawable4(int pStartColor, int pEndColor) {
            super(Orientation.BR_TL, new int[]{pEndColor, pEndColor,pEndColor ,pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }


    /**
     * create gradient drawable for album fragment
     * @author islamahmed1092
     */
    private static class AlbumDrawable extends  GradientDrawable{
        private AlbumDrawable(int pStartColor, int pEndColor){
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }
    }
}
