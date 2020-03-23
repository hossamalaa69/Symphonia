package com.example.symphonia.Fragments_and_models.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.symphonia.Activities.User_Interface.CreatePlaylistActivity;
import com.example.symphonia.R;

/**
 * responsible for all the interaction with the playlists
 * including creating, deleting and showing
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class LibraryPlaylistsFragment extends Fragment {

    public LibraryPlaylistsFragment() {
        // Required empty public constructor
    }


    /**
     * Till now it just showing create playlist
     * and handle clicking on it
     *
     * @param inflater inflate fragment layout
     * @param container viewgroup of the fragment
     * @param savedInstanceState saved data from previous calls
     * @return fragment layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library_playlists, container, false);
        LinearLayout createPlaylist = rootView.findViewById(R.id.create_playlist);
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
