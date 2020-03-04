package com.example.symphonia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.adapters.RvTracksAdapterPlayActivity;
import com.example.symphonia.data.Track;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements Serializable, RvTracksAdapterPlayActivity.OnItemSwitched {

    ArrayList<Track> tracks;
    RecyclerView rvTracks;
    RecyclerView.LayoutManager layoutManager;
    RvTracksAdapterPlayActivity adapterPlayActivity;
    TextView trackTitle;
    TextView trackArtist;
    TextView playlistTitle;
    int trackPos;
    private Drawable trackBackgroun;

    @Override
    public void OnItemSwitchedListener(int pos) {
        //TODO play track at "pos" , update data on screen , change color of back ground
        trackPos = pos;
        updateScreen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        attachViews();
        readDataSendByIntent();
        trackBackgroun = getResources().getDrawable(R.drawable.background);
        updateScreen();
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvTracks.setLayoutManager(layoutManager);
        adapterPlayActivity = new RvTracksAdapterPlayActivity(this, tracks);
        rvTracks.setAdapter(adapterPlayActivity);
        rvTracks.setHasFixedSize(true);
        rvTracks.scrollToPosition(trackPos);

        //TODO set playlist title
        //  playlistTitle.setText(tracks.get(trackPos).getPlaylistName());

        // attach the recycler view to the snapHelper
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvTracks);
        rvTracks.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //    Toast.makeText(PlayActivity.this,""+ layoutManager.getPosition(getCurrentFocus()) ,Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void updateScreen() {
        trackTitle.setText(tracks.get(trackPos).getmTitle());
        trackArtist.setText(tracks.get(trackPos).getmDescription());
        playlistTitle.setText(tracks.get(trackPos).getPlaylistName());

        // change background color according to track image
        ConstraintLayout constraintLayout = findViewById(R.id.background_play_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable drawable = createBackground(trackPos);

            // transition drawable controls the animation ov changing background
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{trackBackgroun, drawable});
            trackBackgroun = drawable;
            constraintLayout.setBackground(td);
            td.startTransition(500);
        }


    }

    private void readDataSendByIntent() {
        Serializable serializable = getIntent()
                .getSerializableExtra(getString(R.string.playlist_send_to_playActivtiy_intent));
        trackPos = (Integer) getIntent().
                getSerializableExtra(getString(R.string.curr_playing_track_play_acitivity_intent));
        if (serializable != null) {
            try {
                tracks = (ArrayList<Track>) serializable;
            } catch (ClassCastException e) {
                Toast.makeText(this, getString(R.string.cant_read), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * attach views
     */
    private void attachViews() {
        rvTracks = findViewById(R.id.rv_playlist_play_activity);
        trackArtist = findViewById(R.id.tv_track_artist);
        trackTitle = findViewById(R.id.tv_track_title_play_activity);
        playlistTitle = findViewById(R.id.tv_playlist_title_play_activity);

    }


    /**
     * makes the recycler view scroll one item at a time
     */
    private class SnapHelperOneByOne extends LinearSnapHelper {

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {

            if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
                return RecyclerView.NO_POSITION;
            }

            final View currentView = findSnapView(layoutManager);

            if (currentView == null) {
                return RecyclerView.NO_POSITION;
            }

            final int currentPosition = layoutManager.getPosition(currentView);

            if (currentPosition == RecyclerView.NO_POSITION) {
                return RecyclerView.NO_POSITION;
            }
            if (toast != null) {
                toast.cancel();
            }

            /*  toast= Toast.makeText(PlayActivity.this
                    ,Integer.toHexString(getDominantColor( BitmapFactory.decodeResource(getResources(),R.drawable.download1)))+"",Toast.LENGTH_SHORT);
            toast.show();
          */
            return currentPosition;
        }
    }

    int pos;
    Toast toast;

    /**
     * gets the dominant color in a bitmap image
     *
     * @param bitmap
     * @return integer refers to the dominant color
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
     * create gradient background for track
     *
     * @return
     */
    private Drawable createBackground(int pos) {
        int color = getDominantColor(BitmapFactory.decodeResource(getResources()
                , tracks.get(pos).getmImageResources()));

        SomeDrawable drawable = new SomeDrawable(color, Color.BLACK);
        return drawable;

    }

    /**
     * create gradient drawable for track image
     */
    public class SomeDrawable extends GradientDrawable {

        public SomeDrawable(int pStartColor, int pEndColor) {
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pStartColor, pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }
}
