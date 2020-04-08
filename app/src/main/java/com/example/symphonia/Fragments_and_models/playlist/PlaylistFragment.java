package com.example.symphonia.Fragments_and_models.playlist;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.RvTracksHomeAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * fragment that represent playlist tracks to user
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class PlaylistFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private RvTracksHomeAdapter rvTracksHomeAdapter;
    private AppBarLayout appBarLayout;
    private ImageView playlistImage;
    private TextView playlistTitle;
    private TextView madeForUser;
    private RecyclerView rvTracks;
    private Button playBtn;

    /**
     * this is required empty constructor
     */
    public PlaylistFragment() {
        // Required empty public constructor

    }

    RelativeLayout backgroundLayout;

    /**
     * inflate view of fragment
     *
     * @param inflater           inflate the fragment
     * @param container          viewgroup of the fragment
     * @param savedInstanceState saved data
     * @return fragment view
     */
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
        playBtn = view.findViewById(R.id.btn_play);
        backgroundLayout = view.findViewById(R.id.rl_background_frag_playlist);

        appBarLayout.setScrollbarFadingEnabled(true);

        playlistTitle.setText(Utils.CurrPlaylist.playlist.getmPlaylistTitle());
        madeForUser.setText(R.string.made_for_you_by_spotify);

        if (Constants.DEBUG_STATUS)
            updateTracks();
        else {
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.getTracksOfPlaylist(getContext(), Utils.CurrPlaylist.playlist.getId(), this);
        }
        // transition drawable controls the animation ov changing background


        final FrameLayout frameLayout = view.findViewById(R.id.frame_playlist_fragment);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = (float) (
                        (appBarLayout.getScrollBarSize() - verticalOffset / 40) / (appBarLayout.getScrollBarSize() * 1.0));
                //          frameLayout.setAlpha(1-alpha);

                if (alpha < 1.2) {
                    playlistImage.setScaleX(1 / (alpha));
                    playlistImage.setScaleY(1 / (alpha));
                }
                frameLayout.setAlpha((long) 2 * (alpha - 1));
            }
        });
        rvTracks.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Utils.CurrTrackInfo.TrackPosInPlaylist != -1) {
                    TextView title = (TextView) rvTracks.getChildAt(
                            Utils.CurrTrackInfo.TrackPosInPlaylist).findViewById(R.id.tv_track_title_item);
                    if (((String) title.getText()).matches(Utils.CurrTrackInfo.track.getmTitle()))
                        title.setTextColor(getContext().getResources().getColor(R.color.colorGreen));
                }
                // unregister listener (this is important)
                rvTracks.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prev = Utils.CurrTrackInfo.TrackPosInPlaylist;
                for (int i = 0; i < Utils.CurrPlaylist.playlist.getTracks().size(); i++) {
                    if (!Utils.CurrPlaylist.playlist.getTracks().get(i).isHidden() &&
                            !(Utils.CurrPlaylist.playlist.getTracks().get(i).isLocked() && !Constants.currentUser.isPremuim())) {
                        Utils.CurrTrackInfo.TrackPosInPlaylist = i;
                        Utils.setTrackInfo(0, Utils.CurrTrackInfo.TrackPosInPlaylist, Utils.CurrPlaylist.playlist.getTracks());
                        Utils.CurrTrackInfo.prevTrackPos = Utils.CurrTrackInfo.TrackPosInPlaylist;
                        changeSelected(prev, Utils.CurrTrackInfo.TrackPosInPlaylist);
                        ((MainActivity) getActivity()).showPlayBar();
                        ((MainActivity) getActivity()).updatePlayBar();
                        ((MainActivity) getActivity()).playTrack();
                        return;
                    }
                }
            }
        });

        return view;
    }

    Drawable background;

    public void updateTracks() {
        layoutManager = new LinearLayoutManager(getContext());
        rvTracks.setHasFixedSize(true);
        rvTracks.setLayoutManager(layoutManager);
        rvTracksHomeAdapter = new RvTracksHomeAdapter(getContext(), Utils.CurrPlaylist.playlist.getTracks());
        rvTracks.setAdapter(rvTracksHomeAdapter);
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Utils.CurrPlaylist.playlist.setPlaylistImage(bitmap);
                background = Utils.createBackground(getContext(), Utils.CurrPlaylist.playlist.getmPlaylistImage());
                playlistImage.setImageBitmap(Utils.CurrPlaylist.playlist.getmPlaylistImage());
                TransitionDrawable td = new TransitionDrawable(
                        new Drawable[]{getResources().getDrawable(R.drawable.background), background});
                backgroundLayout.setBackground(td);
                td.startTransition(500);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        if (!Constants.DEBUG_STATUS)
            Picasso.get()
                    .load(Utils.CurrPlaylist.playlist.getImageUrl())
                    .into(target);

        if (Constants.DEBUG_STATUS) {
            playlistImage.setImageBitmap(Utils.CurrPlaylist.playlist.getmPlaylistImage());
            background = Utils.createBackground(getContext(), Utils.CurrPlaylist.playlist.getmPlaylistImage());
            TransitionDrawable td = new TransitionDrawable(
                    new Drawable[]{getResources().getDrawable(R.drawable.background), background});
            backgroundLayout.setBackground(td);
            td.startTransition(500);
        }


    }

    /**
     * this function change item image view favourite according to its data
     *
     * @param pos     position of item
     * @param isLiked if item favourite
     */
    public void changeLikedItemAtPos(int pos, boolean isLiked) {
        View view = rvTracks.getLayoutManager().getChildAt(pos);
        ImageView ivLike = view.findViewById(R.id.iv_like_track_item);
        if (isLiked) {
            ivLike.setImageResource(R.drawable.ic_favorite_black_24dp);
            ivLike.setSelected(true);
        } else {
            ivLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            ivLike.setSelected(false);
        }

    }


    /**
     * this function change color of selected item and resets color of previous one
     *
     * @param prev position of previous item
     * @param pos  position of current item
     */
    public void changeSelected(int prev, int pos) {
        if (Utils.CurrPlaylist.playlist == null || Utils.CurrTrackInfo.currPlaylistName == null ||
                !Utils.CurrPlaylist.playlist.getmPlaylistTitle().matches(Utils.CurrTrackInfo.currPlaylistName)) {
            return;
        }
        if (prev != -1 && Utils.CurrTrackInfo.currPlaylistTracks != null &&
                !(Utils.CurrTrackInfo.currPlaylistTracks.get(prev).isLocked() && !Constants.currentUser.isPremuim())
        ) {
            if (prev > -1 && pos < Utils.CurrTrackInfo.currPlaylistTracks.size()) {
                View prevView = rvTracks.getLayoutManager().getChildAt(prev);
                TextView tvPrevTitle = null;
                if (prevView != null) {
                    tvPrevTitle = prevView.findViewById(R.id.tv_track_title_item);
                }
                if (tvPrevTitle != null) {
                    if (!Utils.CurrPlaylist.playlist.getTracks().get(prev).isHidden())
                        tvPrevTitle.setTextColor(getContext().getResources().getColor(R.color.white));
                    else {
                        tvPrevTitle.setTextColor(getContext().getResources().getColor(R.color.light_gray));

                    }
                }
            }
        }
        if (pos != -1 && pos < Utils.CurrTrackInfo.currPlaylistTracks.size()) {
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

    /**
     * this function called to change hidden item in tracks recycler view
     *
     * @param pos      position of track to be hidden
     * @param isHidden if track is hidden
     */
    public void changeHidden(int pos, boolean isHidden) {
        if (pos != -1 && pos < Utils.CurrPlaylist.playlist.getTracks().size()) {
            View view = rvTracks.getLayoutManager().getChildAt(pos);
            TextView tvTitle = null;
            ImageView ivHide = null;
            if (null != view) {
                tvTitle = view.findViewById(R.id.tv_track_title_item);
                ivHide = view.findViewById(R.id.iv_hide_track_item);
            }
            if (tvTitle != null) {
                if (isHidden) {
                    ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_red_24dp);
                    ivHide.setSelected(true);
                    tvTitle.setTextColor(getContext().getResources().getColor(R.color.light_gray));
                } else {
                    ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
                    ivHide.setSelected(false);

                }
            }
        }

    }
}
