package com.example.symphonia.Fragments_and_models.playlist;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.RvTracksHomeAdapter;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.google.android.material.appbar.AppBarLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    RecyclerView.LayoutManager layoutManager;
    RvTracksHomeAdapter rvTracksHomeAdapter;
    AppBarLayout appBarLayout;
    Toast toast;
    private Playlist mPlaylist;
    private ImageView playlistImage;
    private TextView playlistTitle;
    private TextView madeForUser;
    private RecyclerView rvTracks;

    public PlaylistFragment() {
        // Required empty public constructor

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Utils.CurrTrackInfo.currPlaylistTracks != null) {
            for (int i = 0; i < Utils.CurrTrackInfo.currPlaylistTracks.size(); i++) {
                if (Utils.CurrTrackInfo.currPlaylistTracks.get(i) == Utils.CurrTrackInfo.track) {
                    changeSelected(Utils.CurrTrackInfo.prevTrackPos, Utils.CurrTrackInfo.TrackPosInPlaylist);
                }
            }
        }
    }

    public PlaylistFragment(Playlist mPlaylist) {
        // Required empty public constructor
        this.mPlaylist = mPlaylist;
    }

    void setmPlaylist(Playlist mPlaylist) {
        this.mPlaylist = mPlaylist;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        rvTracks = view.findViewById(R.id.rv_playlist_tracks);
        madeForUser = view.findViewById(R.id.tv_made_for_you_tracks);
        playlistTitle = view.findViewById(R.id.tv_playlist_title_tracks);
        playlistImage = view.findViewById(R.id.tv_playlist_image_tracks);
        appBarLayout = view.findViewById(R.id.appBarLayout2);
        appBarLayout.setScrollbarFadingEnabled(true);

        playlistImage.setImageBitmap(mPlaylist.getmPlaylistImage());
        playlistTitle.setText(mPlaylist.getmPlaylistTitle());
        madeForUser.setText(R.string.made_for_you_by_spotify);
        layoutManager = new LinearLayoutManager(getContext());
        rvTracks.setHasFixedSize(true);
        rvTracks.setLayoutManager(layoutManager);
        rvTracksHomeAdapter = new RvTracksHomeAdapter(getContext(), mPlaylist.getTracks());
        // transition drawable controls the animation ov changing background

        Drawable background = Utils.createBackground(getContext(), mPlaylist.getmPlaylistImage());
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.background), background});
        RelativeLayout backgroundLayout = view.findViewById(R.id.rl_background_frag_playlist);

        backgroundLayout.setBackground(td);
        td.startTransition(500);
        rvTracks.setAdapter(rvTracksHomeAdapter);
        final FrameLayout frameLayout = view.findViewById(R.id.frame_playlist_fragment);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = (float) ((appBarLayout.getScrollBarSize() - verticalOffset / 40) / (appBarLayout.getScrollBarSize() * 1.0));
                //          frameLayout.setAlpha(1-alpha);

                if (alpha < 1.2) {
                    playlistImage.setScaleX(1 / (alpha));
                    playlistImage.setScaleY(1 / (alpha));
                }
                frameLayout.setAlpha((long) 2 * (alpha - 1));
            }
        });
        //TODO handle toolbar here

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void changeLikedItemAtPos(int pos, boolean isLiked) {
        View view = rvTracks.getLayoutManager().getChildAt(pos);
        ImageView ivLike = view.findViewById(R.id.iv_like_track_item);
        if (isLiked) {
            ivLike.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else
            ivLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);

    }


    public void changeSelected(int prev, int pos) {
        if (prev > -1) {
            View prevView = rvTracks.getLayoutManager().getChildAt(prev);
            TextView tvPrevTitle = null;
            if (prevView != null) {
                tvPrevTitle = prevView.findViewById(R.id.tv_track_title_item);
            }
            if (tvPrevTitle != null) {
                tvPrevTitle.setTextColor(getContext().getResources().getColor(R.color.white));
            }
        }
        if (pos != -1) {
            View view = rvTracks.getLayoutManager().getChildAt(pos);
            TextView tvTitle = null;
            if (null != view) {
                tvTitle = view.findViewById(R.id.tv_track_title_item);
            }
            if (tvTitle != null) {
                tvTitle.setTextColor(getContext().getResources().getColor(R.color.colorGreen));
            }
        }

    }
}
