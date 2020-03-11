package com.example.symphonia.Activities.UserUI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.ScrollingHelpers.SnapHelperOneByOne;
import com.example.symphonia.Utils.Track;
import com.example.symphonia.adapters.RvTracksPlayActivityAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements Serializable, RvTracksPlayActivityAdapter.OnItemSwitched {

    ArrayList<Track> tracks;
    RecyclerView rvTracks;
    RecyclerView.LayoutManager layoutManager;
    RvTracksPlayActivityAdapter adapterPlayActivity;
    TextView trackTitle;
    TextView trackArtist;
    TextView playlistTitle;
    ImageButton closeActivity;
    ImageButton trackSettings;
    ImageButton playBtn;
    ImageButton nextBtn;
    ImageButton prevBtn;
    ImageButton hideBtn;
    ImageButton likeBtn;
    int trackPos;
    private Drawable trackBackgroun;

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
        addListeners();
        readDataSendByIntent();
        trackBackgroun = getResources().getDrawable(R.drawable.background);
        updateScreen();
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvTracks.setLayoutManager(layoutManager);
        adapterPlayActivity = new RvTracksPlayActivityAdapter(this, tracks);
        rvTracks.setAdapter(adapterPlayActivity);
        rvTracks.setHasFixedSize(true);
        rvTracks.scrollToPosition(trackPos);

        //TODO set playlist title
        //  playlistTitle.setText(tracks.get(trackPos).getPlaylistName());

        // add the recycler view to the snapHelper
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvTracks);

    }

    private void addListeners() {
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO handle btn here
                Toast.makeText(PlayActivity.this, "play", Toast.LENGTH_SHORT).show();

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO handle btn here
                Toast.makeText(PlayActivity.this, "next", Toast.LENGTH_SHORT).show();

            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO handle btn here
                Toast.makeText(PlayActivity.this, "prev", Toast.LENGTH_SHORT).show();

            }
        });
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO handle btn here
                Toast.makeText(PlayActivity.this, "added to liked", Toast.LENGTH_SHORT).show();

            }
        });
        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO handle btn here
                Toast.makeText(PlayActivity.this, "track hidden from playlist", Toast.LENGTH_SHORT).show();

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
        closeActivity = findViewById(R.id.iv_close_play_activity);
        trackSettings = findViewById(R.id.iv_track_settings_play_activity);
        playBtn = findViewById(R.id.iv_play_track_playActivity);
        nextBtn = findViewById(R.id.iv_next_track_playActivity);
        prevBtn = findViewById(R.id.iv_prev_track_playActivity);
        hideBtn = findViewById(R.id.iv_hide_track_playActivity);
        likeBtn = findViewById(R.id.iv_like_track_playActivity);


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
