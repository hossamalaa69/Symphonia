
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


import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class BottomSheetDialogArtist extends BottomSheetDialogFragment {

    private static final String ARTIST_ID = "ARTIST_ID";
    private static final String CLICKED_INDEX = "CLICKED_INDEX";

    private BottomSheetListener mListener;

    private float firstY = 0;

    public BottomSheetDialogArtist(BottomSheetListener mListener) {
        this.mListener = mListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.bottom_sheet_artist_properties, null);
        bottomSheet.setContentView(view);

        ServiceController serviceController = ServiceController.getInstance();

        Bundle arguments = getArguments();
        assert arguments != null;
        final String artistId = arguments.getString(ARTIST_ID);
        final int clickedIndex = arguments.getInt(CLICKED_INDEX);

        Artist mArtist = serviceController.getArtist(getContext(),  artistId);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



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

        TextView artistName = view.findViewById(R.id.text_artist_name);
        artistName.setText(mArtist.getArtistName());
        ImageView artistImage = view.findViewById(R.id.image_artist);
        artistImage.setImageBitmap(mArtist.getImage());

        ConstraintLayout imageFrame = view.findViewById(R.id.image_frame);
        ImageView symphoniaImage = view.findViewById(R.id.image_symphonia);
        ImageView soundWave = view.findViewById(R.id.image_sound_wave);

        int dominantColor = Utils.getDominantColor(mArtist.getImage());
        imageFrame.setBackgroundColor(dominantColor);
        if(!Utils.isColorDark(dominantColor)){
            symphoniaImage.setColorFilter(Color.rgb(0, 0, 0));
            soundWave.setColorFilter(Color.rgb(0, 0, 0));
        }
        else{
            symphoniaImage.setColorFilter(Color.rgb(255, 255, 255));
            soundWave.setColorFilter(Color.rgb(255, 255, 255));
        }

        final LinearLayout following = view.findViewById(R.id.layout_following);
        final LinearLayout removeArtist = view.findViewById(R.id.layout_remove_artist);
        final LinearLayout viewArtist = view.findViewById(R.id.layout_view_artist);
        final LinearLayout share = view.findViewById(R.id.layout_share);
        final LinearLayout homeScreen = view.findViewById(R.id.layout_home_screen);

        ArrayList<LinearLayout> layouts = new ArrayList<>(asList(following, removeArtist, viewArtist, share, homeScreen));

        for (LinearLayout layout: layouts) {
            layout.setOnTouchListener(new View.OnTouchListener() {
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
                                Utils.cancelTouchAnimation(v);
                            }
                            return true;

                    }

                    return false;
                }
            });
        }


        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mListener.onFollowingLayoutClicked(artistId, clickedIndex);
            }
        });




        return bottomSheet;
    }

    public interface BottomSheetListener{
        void onFollowingLayoutClicked(String id, int clickedItemIndex);
    }

}
