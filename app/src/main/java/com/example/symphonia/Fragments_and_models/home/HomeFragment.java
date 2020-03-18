package com.example.symphonia.Fragments_and_models.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.RvPlaylistsHomeAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView.LayoutManager layoutManager;
    RvPlaylistsHomeAdapter rvPlaylistsHomeAdapter;
    private HomeViewModel homeViewModel;
    private RecyclerView rvRecentlyPlayed;
    private RecyclerView rvMadeForYou;
    private RecyclerView rvHeavyPlaylist;
    private RecyclerView rvPopularPlaylist;
    private RecyclerView rvBasedOnYourRecentlyPlayed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(root);
        loadDate();
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        return root;

    }


    private void loadDate() {
        //TODO load data from internet and add it to views
        updateUI();
    }

    private void updateUI() {

        //TODO update ui with data
    }


    void initViews(View root) {

        // test
        ServiceController SController = ServiceController.getInstance();
        ArrayList<Playlist> playlists = SController.getRandomPlaylists(getContext(), Constants.mToken);
        ArrayList<Playlist> popularPlaylists = SController.getPopularPlaylists(getContext(), Constants.mToken);
        ArrayList<Playlist> recentPlaylists = SController.getRecentPlaylists(getContext(), Constants.mToken);

        //----------------------
        TextView playlistTitle;
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        View view = root.findViewById(R.id.recently_played_playlist);
        rvRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.recently_played);
        rvRecentlyPlayed.setLayoutManager(layoutManager);
        rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), recentPlaylists);
        rvRecentlyPlayed.setAdapter(rvPlaylistsHomeAdapter);

        // made for you playlist;
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        view = root.findViewById(R.id.made_for_you_playlist);
        rvMadeForYou = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.made_for_you);
        rvMadeForYou.setLayoutManager(layoutManager);
        rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), playlists);
        rvMadeForYou.setAdapter(rvPlaylistsHomeAdapter);

        //heavy playlist
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        view = root.findViewById(R.id.your_heavy_rotation_playlist);
        rvHeavyPlaylist = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.heavy_playlist);
        rvHeavyPlaylist.setLayoutManager(layoutManager);
        rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), playlists);
        rvHeavyPlaylist.setAdapter(rvPlaylistsHomeAdapter);

        //popular playlist
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        view = root.findViewById(R.id.popular_playlist_playlist);
        rvPopularPlaylist = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.popular_playlist);
        rvPopularPlaylist.setLayoutManager(layoutManager);
        rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), popularPlaylists);
        rvPopularPlaylist.setAdapter(rvPlaylistsHomeAdapter);

        // based on your recently played
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        view = root.findViewById(R.id.based_on_your_recently_listening_playlist);
        rvBasedOnYourRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.based_on_your_recently_played);
        rvBasedOnYourRecentlyPlayed.setLayoutManager(layoutManager);
        rvPlaylistsHomeAdapter = new RvPlaylistsHomeAdapter(getContext(), playlists);
        rvBasedOnYourRecentlyPlayed.setAdapter(rvPlaylistsHomeAdapter);

    }
}