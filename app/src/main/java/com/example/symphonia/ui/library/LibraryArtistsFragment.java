package com.example.symphonia.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.symphonia.AddArtistsActivity;
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
        View rootView = inflater.inflate(R.layout.fragment_library_artists, container, false);
        LinearLayout addArtists = (LinearLayout) rootView.findViewById(R.id.add_artists);
        addArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addArtistsIntent = new Intent(getActivity(), AddArtistsActivity.class);
                startActivity(addArtistsIntent);
            }
        });
        return rootView;
    }
}
