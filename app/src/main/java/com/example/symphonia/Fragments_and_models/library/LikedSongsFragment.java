package com.example.symphonia.Fragments_and_models.library;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.symphonia.Adapters.RvListLikedSongsAdapter;
import com.example.symphonia.Adapters.RvTracksPreviewAdapter;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikedSongsFragment extends Fragment implements RvListLikedSongsAdapter.ListItemClickListener,
        RvListLikedSongsAdapter.ListItemLongClickListener,
        RestApi.UpdateSavedTracks {

    private RecyclerView mTracksList;

    /**
     * adapter to control the items in recyclerview
     */
    private RvListLikedSongsAdapter mAdapter;
    /**
     * user's saved albums
     */
    private ArrayList<Track> mLikedSongs;
    /**
     * instance to request data from mock services and API
     */
    private ServiceController mServiceController;
    /**
     * the last touched view
     */
    private View touchedView = null;
    /**
     * the first x when the user puts his finger on the screen
     * used to animate the touch of the views
     */
    private float firstX = 0;
    /**
     * the first y when the user puts his finger on the screen
     * used to animate the touch of the views
     */
    private float firstY = 0;

    public LikedSongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_liked_songs, container, false);

        ImageView backIcon = rootView.findViewById(R.id.arrow_back);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        final TextView likedSongs = rootView.findViewById(R.id.text_liked_songs);
        final TextView title = rootView.findViewById(R.id.title);
        title.setAlpha(0);

        AppBarLayout appBarLayout = rootView.findViewById(R.id.appbar);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                int halfRange = appBarLayout.getTotalScrollRange()/2;
                Utils.startTouchAnimation(likedSongs,1 + (float)verticalOffset/(halfRange*16),  1 + (float)verticalOffset/(halfRange*4));
                if(Math.abs(verticalOffset) > halfRange){
                    title.setAlpha((float)(-((float)verticalOffset/halfRange+1)));
                }
                else{
                    title.setAlpha(0);
                }
            }
        });


        mServiceController = ServiceController.getInstance();
        mTracksList = rootView.findViewById(R.id.rv_songs);

        mLikedSongs = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mTracksList.setLayoutManager(layoutManager);
        mAdapter = new RvListLikedSongsAdapter(new ArrayList<Track>(), this, this);
        mTracksList.setAdapter(mAdapter);

        mTracksList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                float currentX = e.getX();
                float currentY = e.getY();

                View newTouchedView = mTracksList.findChildViewUnder(currentX, currentY);
                if(touchedView != null && touchedView != newTouchedView){
                    Utils.cancelTouchAnimation(touchedView);
                }

                touchedView = newTouchedView;
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(touchedView, 0.98f, 0.5f);
                        firstX = currentX;
                        firstY = currentY;
                        break;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(touchedView);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if((currentX != firstX) || (Math.abs(currentY - firstY) > 3)){
                            Utils.cancelTouchAnimation(touchedView);
                        }
                        break;
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        mServiceController.getUserSavedTracks(this,0, 65535);


        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    @Override
    public void onListItemLongClick(int clickedItemIndex) {

    }

    @Override
    public void updateTracks(ArrayList<Track> returnedTracks) {
        mLikedSongs.clear();
        mLikedSongs.addAll(returnedTracks);

        mAdapter.clear();
        mAdapter.addAll(mLikedSongs);
        mAdapter.notifyDataSetChanged();
    }
}
