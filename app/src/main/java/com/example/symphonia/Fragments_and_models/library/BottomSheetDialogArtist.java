
package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.graphics.Color;

import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialogArtist extends BottomSheetDialogFragment {

    private Artist artist;
    private float firstY = 0;

    public BottomSheetDialogArtist(Artist artist) {
        this.artist = artist;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.bottom_sheet_artist_properties, null);
        bottomSheet.setContentView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        TextView artistName = view.findViewById(R.id.text_artist_name);
        artistName.setText(artist.getArtistName());
        ImageView artistImage = view.findViewById(R.id.image_artist);
        artistImage.setImageBitmap(artist.getImage());

        ConstraintLayout imageFrame = view.findViewById(R.id.image_frame);
        ImageView symphoniaImage = view.findViewById(R.id.image_symphonia);
        ImageView soundWave = view.findViewById(R.id.image_sound_wave);

        int dominantColor = Utils.getDominantColor(artist.getImage());
        imageFrame.setBackgroundColor(dominantColor);
        if(!Utils.isColorDark(dominantColor)){
            symphoniaImage.setColorFilter(Color.rgb(0, 0, 0));
            soundWave.setColorFilter(Color.rgb(0, 0, 0));
        }

        bottomSheet.getWindow()
                .findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(R.drawable.background_bottom_sheet);


        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        final LinearLayout following = view.findViewById(R.id.layout_following);
        final LinearLayout removeArtist = view.findViewById(R.id.layout_remove_artist);
        final LinearLayout viewArtist = view.findViewById(R.id.layout_view_artist);
        final LinearLayout share = view.findViewById(R.id.layout_share);
        final LinearLayout homeScreen = view.findViewById(R.id.layout_home_screen);

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "following clicked", Toast.LENGTH_SHORT).show();
            }
        });

        removeArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "removeArtist clicked", Toast.LENGTH_SHORT).show();
            }
        });

        viewArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "viewArtist clicked", Toast.LENGTH_SHORT).show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "share clicked", Toast.LENGTH_SHORT).show();
            }
        });

        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "homeScreen clicked", Toast.LENGTH_SHORT).show();
            }
        });


        following.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.99f, 0.5f);
                        firstY = currentY;
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        if((currentY >= 0) && (currentY <= v.getHeight())){
                            v.performClick();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(following);
                        }
                        return true;

                }

                return false;
            }
        });

        removeArtist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.99f, 0.5f);
                        firstY = currentY;
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        if((currentY >= 0) && (currentY <= v.getHeight())){
                            v.performClick();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(following);
                        }
                        return true;

                }

                return false;
            }
        });

        viewArtist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.99f, 0.5f);
                        firstY = currentY;
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        if((currentY >= 0) && (currentY <= v.getHeight())){
                            v.performClick();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(following);
                        }
                        return true;

                }

                return false;
            }
        });

        share.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.99f, 0.5f);
                        firstY = currentY;
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        if((currentY >= 0) && (currentY <= v.getHeight())){
                            v.performClick();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(following);
                        }
                        return true;

                }

                return false;
            }
        });

        homeScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.99f, 0.5f);
                        firstY = currentY;
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        if((currentY >= 0) && (currentY <= v.getHeight())){
                            v.performClick();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(following);
                        }
                        return true;

                }

                return false;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstY = currentY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(following);
                            Utils.cancelTouchAnimation(removeArtist);
                            Utils.cancelTouchAnimation(viewArtist);
                            Utils.cancelTouchAnimation(share);
                            Utils.cancelTouchAnimation(homeScreen);
                        }
                }
                return false;
            }
        });





        return bottomSheet;
    }
}
