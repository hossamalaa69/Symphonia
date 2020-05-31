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
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

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
    private View progressPar;

    /**
     * this is required empty constructor
     */
    public PlaylistFragment() {
        // Required empty public constructor

    }

    public void hideProgressBar() {
        progressPar.setVisibility(View.GONE);
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
        progressPar = view.findViewById(R.id.progress_bar);
        progressPar.setVisibility(View.VISIBLE);
        appBarLayout.setScrollbarFadingEnabled(true);

        playlistTitle.setText(Utils.displayedPlaylist.getmPlaylistTitle());
        madeForUser.setText(R.string.made_for_you_by_spotify);

        if (Constants.DEBUG_STATUS) {

            updateTracks();
            progressPar.setVisibility(View.GONE);
        } else {
            ServiceController serviceController = ServiceController.getInstance();
            serviceController.getTracksOfPlaylist(getContext(), Utils.displayedPlaylist.getId(), this);
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
      /*  rvTracks.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Utils.CurrTrackInfo.TrackPosInPlaylist != -1) {
                    if (rvTracks.getChildAt(
                            Utils.CurrTrackInfo.TrackPosInPlaylist) == null) return;
                    TextView title = (TextView) rvTracks.getChildAt(
                            Utils.CurrTrackInfo.TrackPosInPlaylist).findViewById(R.id.tv_track_title_item);
                    if (((String) title.getText()).matches(Utils.CurrTrackInfo.track.getmTitle())&&
                            Utils.CurrPlaylist.playlist.getmPlaylistTitle().matches(Utils.CurrTrackInfo.track.getPlaylistName()))
                        title.setTextColor(getContext().getResources().getColor(R.color.colorGreen));
                }
                // unregister listener (this is important)
                rvTracks.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });*/
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i =0 ; i<Utils.displayedPlaylist.getTracks().size();i++)
                    if(!Utils.displayedPlaylist.getTracks().get(i).isLocked()) {
                        Utils.currTrack = Utils.displayedPlaylist.getTracks().get(i);
                        Utils.currContextType  = "playlist";
                        Utils.currContextId = Utils.displayedPlaylist.getId();
                    }
                    changeSelected();
                    ((MainActivity) getActivity()).startTrack();
                    }
        });

        return view;
    }

    Drawable background;

    /**
     * this function is called to update recycler view of tracks
     */
    public void updateTracks() {
        layoutManager = new LinearLayoutManager(getContext());
        rvTracks.setHasFixedSize(true);
        rvTracks.setLayoutManager(layoutManager);
        rvTracksHomeAdapter = new RvTracksHomeAdapter(getContext(), Utils.displayedPlaylist.getTracks());
        rvTracks.setAdapter(rvTracksHomeAdapter);
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Utils.displayedPlaylist.setPlaylistImage(bitmap);
                background = Utils.createBackground(getContext(), Utils.displayedPlaylist.getmPlaylistImage());
                playlistImage.setImageBitmap(Utils.displayedPlaylist.getmPlaylistImage());
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
                    .load(Utils.displayedPlaylist.getImageUrl())
                    .into(target);

        if (Constants.DEBUG_STATUS) {
            playlistImage.setImageBitmap(Utils.displayedPlaylist.getmPlaylistImage());
            background = Utils.createBackground(getContext(), Utils.displayedPlaylist.getmPlaylistImage());
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
     * @param //prev position of previous item
     * @param //curr position of current item
     */
  /*  public void changeSelected(String prev, String curr) {
      //  View prevView = rvTracks.getLayoutManager().getChildAt(prev);

    }*/
    public void changeSelected() {
     //   rvTracksHomeAdapter.selectPlaying(Utils.currTrack.getId());
        rvTracksHomeAdapter.notifyDataSetChanged();
    }

    public void changeHidden(int pos, boolean isHidden) {
        rvTracksHomeAdapter.selectHidden(pos, isHidden);
        rvTracksHomeAdapter.notifyDataSetChanged();
    }

}
