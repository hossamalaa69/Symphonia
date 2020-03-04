package com.example.symphonia.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.symphonia.R;

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
        return inflater.inflate(R.layout.fragment_library_artists, container, false);
    }
}
