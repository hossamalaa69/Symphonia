package com.example.symphonia.Helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utils {

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

    public static Bitmap convertToBitmap(int mImageResourceId)
    {
        return BitmapFactory.decodeResource(App.getContext().getResources(), mImageResourceId);
    }

    public static void hideKeyboard(AppCompatActivity activity, Context context)
    {
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

    public int add2(int x, int y){
        return x+y;
    }

    /**
     * create gradient background for an image
     *
     * @return
     */
    public static Drawable createBackground(Context context,int ImageResources) {
        int color = getDominantColor(BitmapFactory.decodeResource(context.getResources()
                ,ImageResources));

        SomeDrawable drawable = new SomeDrawable(color, Color.BLACK);
        return drawable;

    }
    public static Drawable createBackground(Context context,Bitmap ImageResources) {
        int color = getDominantColor(ImageResources);

        SomeDrawable2 drawable = new SomeDrawable2(color, Color.BLACK);
        return drawable;

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
}
