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
 * A simple {@link Fragment} subclass.
 */
public class LibraryArtistsFragment extends Fragment {

    private RecyclerView mArtistsList;
    private RvListArtistsAdapter mAdapter;
    private ArrayList<Artist> mFollowedArtists;
    private ServiceController mServiceController;

    public LibraryArtistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_artists, container, false);

        mServiceController = ServiceController.getInstance();
        mFollowedArtists = mServiceController.getFollowedArtists(Constants.currentUser.isListenerType(), Constants.currentToken, 30);

        mArtistsList = rootView.findViewById(R.id.rv_artists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mArtistsList.setLayoutManager(layoutManager);
        mAdapter = new RvListArtistsAdapter(mFollowedArtists, getActivity());
        mArtistsList.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFollowedArtists = mServiceController.getFollowedArtists
                (Constants.currentUser.isListenerType(), Constants.currentToken, 30);

        mAdapter.clear();
        mAdapter.addAll(mFollowedArtists);
        mAdapter.notifyDataSetChanged();
    }
}
