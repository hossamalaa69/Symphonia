package com.example.symphonia.Fragments_and_models.library;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class albumFragment extends Fragment {

    Album album;

    public albumFragment() {
        // Required empty public constructor
    }

    public albumFragment(Album album){
        this.album = album;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }
}
