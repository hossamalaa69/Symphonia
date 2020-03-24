package com.example.symphonia.Fragments_and_models.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_artists, container, false);

        mServiceController = ServiceController.getInstance();
        mFollowedArtists = mServiceController.getFollowedArtists(Constants.currentUser.isListenerType(), Constants.currentToken, 30);

        RecyclerView mArtistsList = rootView.findViewById(R.id.rv_artists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mArtistsList.setLayoutManager(layoutManager);
        mAdapter = new RvListArtistsAdapter(mFollowedArtists, getActivity(), this);
        mArtistsList.setAdapter(mAdapter);

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
        BottomSheetDialogArtist bottomSheet = new BottomSheetDialogArtist(mFollowedArtists.get(clickedItemIndex));
        assert getFragmentManager() != null;
        bottomSheet.show(getFragmentManager(),bottomSheet.getTag());
    }
}
