package com.example.symphonia.ui.home;

import android.graphics.BitmapFactory;
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

import com.example.symphonia.R;
import com.example.symphonia.adapters.RvPlaylistsAdapterHome;
import com.example.symphonia.data.Playlist;
import com.example.symphonia.data.Track;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView.LayoutManager layoutManager;
    RvPlaylistsAdapterHome rvPlaylistsAdapterHome;
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
        ArrayList<Track> tracks = new ArrayList<Track>();

        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.images));
        tracks.add(new Track("la la land ", "islam", "HOme", R.drawable.download));
        tracks.add(new Track("ha ha hand ", "Hossam", "baba ma ", R.drawable.download1));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.images3));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.images2));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.images));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.download1));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.download));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.download1));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.download));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.download1));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.images2));
        tracks.add(new Track("Try this", "khaled,seyam,azoz", "HOme", R.drawable.download));


        ArrayList<Playlist> playlists = new ArrayList<>();
        playlists.add(new Playlist("HOme", "khaled,seyam,azoz this playlist is so popular",
                BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher_background), tracks));
        playlists.add(new Playlist("HOme", "khaled,seyam,azoz this playlist is so popular",
                BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher_background), tracks));
        playlists.add(new Playlist("HOme", "khaled,seyam,azoz this playlist is so popular",
                BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher_background), tracks));
        playlists.add(new Playlist("HOme", "khaled,seyam,azoz this playlist is so popular",
                BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher_background), tracks));
        playlists.add(new Playlist("HOme", "khaled,seyam,azoz this playlist is so popular",
                BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher_background), tracks));
        playlists.add(new Playlist("HOme", "khaled,seyam,azoz this playlist is so popular",
                BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher_background), tracks));
        playlists.add(new Playlist("HOme", "khaled,seyam,azoz this playlist is so popular",
                BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher_background), tracks));

        //----------------------
        TextView playlistTitle;
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        View view = root.findViewById(R.id.recently_played_playlist);
        rvRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.recently_played);
        rvRecentlyPlayed.setLayoutManager(layoutManager);
        rvPlaylistsAdapterHome = new RvPlaylistsAdapterHome(getContext(), playlists);
        rvRecentlyPlayed.setAdapter(rvPlaylistsAdapterHome);

        // made for you playlist
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        view = root.findViewById(R.id.made_for_you_playlist);
        rvMadeForYou = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.made_for_you);
        rvMadeForYou.setLayoutManager(layoutManager);
        rvPlaylistsAdapterHome = new RvPlaylistsAdapterHome(getContext(), playlists);
        rvMadeForYou.setAdapter(rvPlaylistsAdapterHome);

        //heavy playlist
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        view = root.findViewById(R.id.your_heavy_rotation_playlist);
        rvHeavyPlaylist = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.heavy_playlist);
        rvHeavyPlaylist.setLayoutManager(layoutManager);
        rvPlaylistsAdapterHome = new RvPlaylistsAdapterHome(getContext(), playlists);
        rvHeavyPlaylist.setAdapter(rvPlaylistsAdapterHome);

        //popular playlist
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        view = root.findViewById(R.id.popular_playlist_playlist);
        rvPopularPlaylist = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.popular_playlist);
        rvPopularPlaylist.setLayoutManager(layoutManager);
        rvPlaylistsAdapterHome = new RvPlaylistsAdapterHome(getContext(), playlists);
        rvPopularPlaylist.setAdapter(rvPlaylistsAdapterHome);

        // based on your recently played
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        view = root.findViewById(R.id.based_on_your_recently_listening_playlist);
        rvBasedOnYourRecentlyPlayed = view.findViewById(R.id.rv_sample_home);
        playlistTitle = view.findViewById(R.id.tv_playlist_type_sample_home);
        playlistTitle.setText(R.string.based_on_your_recently_played);
        rvBasedOnYourRecentlyPlayed.setLayoutManager(layoutManager);
        rvPlaylistsAdapterHome = new RvPlaylistsAdapterHome(getContext(), playlists);
        rvBasedOnYourRecentlyPlayed.setAdapter(rvPlaylistsAdapterHome);

    }
}