package com.example.symphonia.Fragments_and_models.library;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Adapters.RvListArtistsAdapter;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * responsible for all the interaction with artists
 * including adding, deleting, searching and showing
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class LibraryArtistsFragment extends Fragment implements RvListArtistsAdapter.ListItemLongClickListener {

    private static final String ARTIST_ID = "ARTIST_ID";
    /**
     * holds the user's following artists
     */
    private ArrayList<Artist> mFollowedArtists;
    /**
     * Instance for requesting from mock services and Api
     */
    private ServiceController mServiceController;
    /**
     * adapter to control the items in artists recyclerview
     */
    private RvListArtistsAdapter mAdapter;

    private View touchedView = null;
    private float firstX = 0;
    private float firstY = 0;


    /**
     * empty constructor
     */
    public LibraryArtistsFragment() {
        // Required empty public constructor
    }

    /**
     * create the artists recyclerview to show the list of artists
     *
     * @param inflater inflate fragment layout
     * @param container fragment viewgroup
     * @param savedInstanceState saved data from previous calls
     * @return fragment view
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_artists, container, false);

        mServiceController = ServiceController.getInstance();
        mFollowedArtists = mServiceController.getFollowedArtists(Constants.currentUser.isListenerType(), Constants.currentToken, 30);

        final RecyclerView mArtistsList = rootView.findViewById(R.id.rv_artists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mArtistsList.setLayoutManager(layoutManager);
        mAdapter = new RvListArtistsAdapter(mFollowedArtists, getActivity(), this);
        mArtistsList.setAdapter(mAdapter);

        mArtistsList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                float currentX = e.getX();
                float currentY = e.getY();

                View newTouchedView = mArtistsList.findChildViewUnder(currentX, currentY);
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


        return rootView;
    }

    /**
     * calls when it opens or when closing another fragment of activity that leads to it
     * updates the artists list
     */
    @Override
    public void onResume() {
        super.onResume();
        mFollowedArtists = mServiceController.getFollowedArtists
                (Constants.currentUser.isListenerType(), Constants.currentToken, 30);

        mAdapter.clear();
        mAdapter.addAll(mFollowedArtists);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onListItemLongClick(int clickedItemIndex) {
        BottomSheetDialogArtist bottomSheet = new BottomSheetDialogArtist();
        Bundle arguments = new Bundle();
        arguments.putString(ARTIST_ID , mFollowedArtists.get(clickedItemIndex).getId());
        bottomSheet.setArguments(arguments);
        bottomSheet.show(((MainActivity)getActivity()).getSupportFragmentManager(), bottomSheet.getTag());
    }

}
