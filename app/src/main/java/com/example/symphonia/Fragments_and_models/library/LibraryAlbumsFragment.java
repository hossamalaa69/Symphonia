package com.example.symphonia.Fragments_and_models.library;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.RvListAlbumsAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Helpers.SnackbarHelper;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * responsible for all the interaction with albums
 * including deleting, searching and showing
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class LibraryAlbumsFragment extends Fragment implements RvListAlbumsAdapter.ListItemClickListener,
        RvListAlbumsAdapter.ListItemLongClickListener, BottomSheetDialogAlbum.BottomSheetListener {

    private static final String ALBUM_ID = "ALBUM_ID";
    private static final String CLICKED_INDEX = "CLICKED_INDEX";

    private RecyclerView mAlbumsList;

    private TextView albumsEmptyState;
    /**
     * adapter to control the items in recyclerview
     */
    private RvListAlbumsAdapter mAdapter;
    /**
     * user's saved albums
     */
    private ArrayList<Album> mLikedAlbums;
    /**
     * instance to request data from mock services and API
     */
    private ServiceController mServiceController;
    private View touchedView = null;
    private float firstX = 0;
    private float firstY = 0;

    /**
     * empty constructor
     */
    public LibraryAlbumsFragment() {
        // Required empty public constructor
    }


    /**
     * create the albums recyclerview to show
     * the list of saved albums
     *
     * @param inflater inflate fragment layout
     * @param container fragment viewgroup
     * @param savedInstanceState saved data from previous calls
     * @return fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_albums, container, false);
        mServiceController = ServiceController.getInstance();
        albumsEmptyState = rootView.findViewById(R.id.text_albums_empty_state);

        mLikedAlbums = mServiceController.getUserSavedAlbums(getContext(),0, 20);

        if(mLikedAlbums.size() != 0)
            albumsEmptyState.setVisibility(View.GONE);
        else
            albumsEmptyState.setVisibility(View.VISIBLE);


        mAlbumsList = rootView.findViewById(R.id.rv_albums);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAlbumsList.setLayoutManager(layoutManager);
        mAdapter = new RvListAlbumsAdapter(mLikedAlbums, this, this);
        mAlbumsList.setAdapter(mAdapter);

        mAlbumsList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                float currentX = e.getX();
                float currentY = e.getY();

                View newTouchedView = mAlbumsList.findChildViewUnder(currentX, currentY);
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
     * handles the clicking on the recyclerview item
     * replaces the current fragment with clicked album framgnet
     *
     * @param v clicked view
     * @param clickedItemIndex index of the clicked view
     */
    @Override
    public void onListItemClick(View v, int clickedItemIndex) {

        AlbumFragment fragment = new AlbumFragment();
        Bundle arguments = new Bundle();
        arguments.putString( ALBUM_ID , mLikedAlbums.get(clickedItemIndex).getAlbumId());
        fragment.setArguments(arguments);


        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onListItemLongClick(int clickedItemIndex) {
        BottomSheetDialogAlbum bottomSheet = new BottomSheetDialogAlbum(this);
        Bundle arguments = new Bundle();
        arguments.putString( ALBUM_ID , mLikedAlbums.get(clickedItemIndex).getAlbumId());
        arguments.putInt(CLICKED_INDEX , clickedItemIndex);
        bottomSheet.setArguments(arguments);
        bottomSheet.show(((MainActivity)getActivity()).getSupportFragmentManager(), bottomSheet.getTag());
    }

    @Override
    public void onLikedLayoutClicked(final String id, final int clickedItemIndex) {

        mServiceController.removeAlbumsForUser(getContext(),
                new ArrayList<String>(Collections.singletonList(id)));

        mLikedAlbums.remove(clickedItemIndex);
        mAdapter.notifyItemRemoved(clickedItemIndex);
        if(mLikedAlbums.size() == 0) albumsEmptyState.setVisibility(View.VISIBLE);

        Snackbar snack = Snackbar.make(mAlbumsList, R.string.remove_album_snackbar_text, Snackbar.LENGTH_LONG);
        SnackbarHelper.configSnackbar(getContext(), snack, R.drawable.custom_snackbar, Color.BLACK);
        snack.show();
    }

    @Override
    public void onGoFullAlbumLayoutClicked(String mAlbumId) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle arguments = new Bundle();
        arguments.putString( ALBUM_ID , mAlbumId);
        fragment.setArguments(arguments);


        ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

}
