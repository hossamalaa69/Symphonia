package com.example.symphonia.ui.playlist;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.adapters.RvTracksAdapterHome;
import com.example.symphonia.data.Playlist;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment {

    RecyclerView.LayoutManager layoutManager;
    RvTracksAdapterHome rvTracksAdapterHome;
    private Playlist mPlaylist;
    private ImageView playlistImage;
    private TextView playlistTitle;
    private TextView madeForUser;
    private RecyclerView rvTracks;

    public PlaylistFragment() {
        // Required empty public constructor

    }

    public PlaylistFragment(Playlist mPlaylist) {
        // Required empty public constructor
        this.mPlaylist = mPlaylist;
    }

    void setmPlaylist(Playlist mPlaylist) {
        this.mPlaylist = mPlaylist;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        rvTracks = view.findViewById(R.id.rv_playlist_tracks);
        madeForUser = view.findViewById(R.id.tv_made_for_you_tracks);
        playlistTitle = view.findViewById(R.id.tv_playlist_title_tracks);
        playlistImage = view.findViewById(R.id.tv_playlist_image_tracks);

        //TODO add data of playlist (real data ) delete hard coded
        playlistImage.setImageResource(R.drawable.download1);
        playlistTitle.setText(mPlaylist.getmPlaylistTitle());
        madeForUser.setText(R.string.made_for_you);
        layoutManager = new LinearLayoutManager(getContext());
        rvTracks.setHasFixedSize(true);
        rvTracks.setLayoutManager(layoutManager);
        rvTracksAdapterHome = new RvTracksAdapterHome(getContext(), mPlaylist.getTracks());
        rvTracks.setAdapter(rvTracksAdapterHome);

        return view;
    }

}
