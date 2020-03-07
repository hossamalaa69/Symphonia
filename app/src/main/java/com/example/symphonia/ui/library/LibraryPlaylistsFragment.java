package com.example.symphonia.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.symphonia.CreatePlaylistActivity;
import com.example.symphonia.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryPlaylistsFragment extends Fragment {

    public LibraryPlaylistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_playlists, container, false);
        LinearLayout createPlaylist = (LinearLayout) rootView.findViewById(R.id.create_playlist);
        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createPlaylistintent = new Intent(getActivity(), CreatePlaylistActivity.class);
                startActivity(createPlaylistintent);
            }
        });
        return rootView;
    }
}
