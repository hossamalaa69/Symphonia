package com.example.symphonia.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;
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
 * @version 1.0
 * @since 22-3-2020
 */
public class Utils {

    /**
     * extracts name from email
     *
     * @param email email input from user
     * @return returns extracted name from this email
     * @author Hossam Alaa
     * @version 1.0
     * @since 22-3-2020
     */
    public static String getNameFromEmail(String email) {
        return email.split("@")[0];
    }

    /**
     * this function takes info on track and set it to Utils.CurrTrackInfo
     *
     * @param currPlayingPos
     * @param TrackPosInPlaylist
     * @param currPlaylistTracks
     * @author Khaled Ali
     */
    public static void setTrackInfo(int currPlayingPos, int TrackPosInPlaylist, ArrayList<Track> currPlaylistTracks) {
        CurrTrackInfo.currPlayingPos = currPlayingPos;
        CurrTrackInfo.currPlaylistTracks = currPlaylistTracks;
        CurrTrackInfo.currPlaylistName = currPlaylistTracks.get(TrackPosInPlaylist).getPlaylistName();
        CurrTrackInfo.track = currPlaylistTracks.get(TrackPosInPlaylist);
        CurrTrackInfo.TrackPosInPlaylist = TrackPosInPlaylist;
    }

    /**
     * this static class required to store current clicked playlist's data
     *
     * @author Khaled Ali
     */
    public static class CurrPlaylist {
        /**
         * current clicked playlist
         */
        public static Playlist playlist = null;
    }

    /**
     * this static class required to store current clicked track in certain playlist
     *
     * @author Khaled Ali
     */
    public static class CurrTrackInfo {

        /**
         * indicates that track is Utils.CurrTrackInfo.paused
         */
        public static boolean paused = true;
        /**
         * indicates to position of clicked track in playlist
         */
        public static int TrackPosInPlaylist = -1;
        /**
         * indicates to  position of previous clicked track in playlist
         */
        public static int prevTrackPos = -1;
        /**
         * all tracks in playlist of clicked track
         */
        public static ArrayList<Track> currPlaylistTracks;
        /**
         * playlist name
         */
        public static String currPlaylistName;
        /**
         * clicked track
         */
        public static Track track;
        /**
         * current second of playing track
         */
        public static int currPlayingPos;
    }


    /**
     * checks if string is in email form or not
     *
     * @param target input string to be checked
     * @return returns true if it's valid
     * @author Hossam Alaa
     * @version 1.0
     * @since 22-3-2020
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
     * @param mImageResourceId image resource id
     * @return the converted bitmap image
     * @author islamahmed1092
     */
    public static Bitmap convertToBitmap(int mImageResourceId) {
        return BitmapFactory.decodeResource(App.getContext().getResources(), mImageResourceId);
    }

    /**
     * hides the soft keyboard from the screen
     * if it's opened
     *
     * @param activity the current opened activity
     * @param context  the context of the keyboard
     * @author islamahmed1092
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
     * @return drawable of created background
     */
    public static Drawable createBackground(Context context, int ImageResources) {
        int color = getDominantColor(BitmapFactory.decodeResource(context.getResources()
                , ImageResources));

        SomeDrawable drawable = new SomeDrawable(color, Color.BLACK);
        return drawable;

    }

    /**
     * create gradient background for an image
     *
     * @return drawable of created background
     */
    public static Drawable createBackground(Context context, Bitmap ImageResources) {
        int color = getDominantColor(ImageResources);

        SomeDrawable2 drawable = new SomeDrawable2(color, Color.BLACK);
        return drawable;

    }

    /**
     * create gradient background for SearchList layout
     *
     * @param context
     * @param container container which has the data of the first item in recycler view
     * @return a drawable to be set as background of search list
     * @author Mahmoud Amr Nabil
     */
    public static Drawable createSearchListBackground(Context context, Container container) {
        int color = getDominantColor(BitmapFactory.decodeResource(context.getResources()
                , container.getImg_Res()));

        SomeDrawable3 drawable = new SomeDrawable3(color, Color.BLACK);
        return drawable;

    }

    /**
     * create gradient background for category layout
     *
     * @param context
     * @param container container which has the data of category
     * @return a drawable to be set as background of category
     * @author Mahmoud Amr Nabil
     */
    public static Drawable createCategoryBackground(Context context, Container container) {
        int color = getDominantColor(BitmapFactory.decodeResource(context.getResources()
                , container.getImg_Res()));

        SomeDrawable4 drawable = new SomeDrawable4(color, Color.BLACK);
        return drawable;

    }


    /**
     * create a gradient background for the albums
     * based on the album photo dominant color
     *
     * @param context        background context
     * @param ImageResources bitmap image
     * @return drawable gradient background
     * @author islamahmed1092
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
     * @author Khaled Ali
     */
    public static int getDominantColor(Bitmap bitmap) {
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
     * this class creates drawable for track with gradient below middle
     * * @author Khaled Ali
     */
    private static class SomeDrawable extends GradientDrawable {

        private SomeDrawable(int pStartColor, int pEndColor) {
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pStartColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }

    /**
     * this class creates drawable for track with gradient above middle
     *
     * @author Khaled Ali
     */
    private static class SomeDrawable2 extends GradientDrawable {

        private SomeDrawable2(int pStartColor, int pEndColor) {
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pEndColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }

    /**
     * create gradient drawable for search result recycler view
     *
     * @author Mahmoud Amr Nabil
     */
    private static class SomeDrawable3 extends GradientDrawable {

        public SomeDrawable3(int pStartColor, int pEndColor) {
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pEndColor,pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }

    /**
     * create gradient drawable for category layout
     *
     * @author Mahmoud Amr Nabil
     */
    private static class SomeDrawable4 extends GradientDrawable {

        /**
         * a non empty constructor
         *
         * @param pStartColor color to be in top
         * @param pEndColor   color to be in middle and bottom
         */
        public SomeDrawable4(int pStartColor, int pEndColor) {
            super(Orientation.BR_TL, new int[]{pEndColor, pEndColor, pEndColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }


    /**
     * create gradient drawable for album fragment
     *
     * @author islamahmed1092
     */
    private static class AlbumDrawable extends GradientDrawable {
        private AlbumDrawable(int pStartColor, int pEndColor) {
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }
    }

    public static boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            return false; // It's a light color
        } else {
            return true; // It's a dark color
        }
    }

    public static void startTouchAnimation(View view, float scale, float transparency) {
        if (view != null) {
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setAlpha(transparency);
        }
    }

    public static void cancelTouchAnimation(View view) {
        if (view != null) {
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            view.setAlpha(1.0f);
        }
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
