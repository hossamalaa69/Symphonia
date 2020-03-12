package com.example.symphonia.Fragments_and_models.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.UserUI.AddArtistsActivity;
import com.example.symphonia.R;
import com.example.symphonia.Utils.Artist;
import com.example.symphonia.adapters.RvListArtistsAdapter;

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

        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(new Artist(R.drawable.download, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download1, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images2, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images3, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download1, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images2, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.images3, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download, "Mahmoud El Esseily"));
        artists.add(new Artist(R.drawable.download1, "Mahmoud El Esseily"));

        artists.add(new Artist(R.drawable.add_image, "Add Artists"));


        RecyclerView artistList = rootView.findViewById(R.id.rv_artists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        artistList.setLayoutManager(layoutManager);
        RvListArtistsAdapter adapter = new RvListArtistsAdapter(artists, getActivity());
        artistList.setAdapter(adapter);

        return rootView;
    }
}
