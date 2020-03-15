package com.example.symphonia.Fragments_and_models.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Adapters.RvListArtistsAdapter;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryArtistsFragment extends Fragment {

    public LibraryArtistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_artists, container, false);

        ServiceController serviceController = ServiceController.getInstance();

        ArrayList<Artist> mFollowedArtists = serviceController.getFollowedArtists(Constants.user.isListenerType(), Constants.mToken, 25);
        mFollowedArtists.add(new Artist("-1", Utils.convertToBitmap(R.drawable.add_image), "Add Artists"));

        RecyclerView artistList = rootView.findViewById(R.id.rv_artists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        artistList.setLayoutManager(layoutManager);
        RvListArtistsAdapter adapter = new RvListArtistsAdapter(mFollowedArtists, getActivity());
        artistList.setAdapter(adapter);

        return rootView;
    }
}
