package com.example.symphonia.Fragments_and_models.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.RvListAlbumsAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryAlbumsFragment extends Fragment {

    private RecyclerView albumsList;
    private RvListAlbumsAdapter adapter;
    private ArrayList<Album> mLikedAlbums;
    private ServiceController serviceController;

    public LibraryAlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_albums, container, false);
        serviceController = ServiceController.getInstance();
        TextView albumsEmptyState = rootView.findViewById(R.id.albums_empty_state);

        mLikedAlbums = serviceController.getUserSavedAlbums(getContext(), "token1", 0, 20);

        if(mLikedAlbums.size() != 0)
            albumsEmptyState.setVisibility(View.GONE);
        else
            albumsEmptyState.setVisibility(View.VISIBLE);

        albumsList = rootView.findViewById(R.id.rv_albums);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumsList.setLayoutManager(layoutManager);
        adapter = new RvListAlbumsAdapter(mLikedAlbums);
        albumsList.setAdapter(adapter);
        return rootView;
    }
}
