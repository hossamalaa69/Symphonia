package com.example.symphonia.Fragments_and_models.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.RvPlaylistsHomeAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Fragments_and_models.settings.SettingsFragment;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * fragment that represent playlist to user
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class HomeFragment extends Fragment {


    RecyclerView.LayoutManager layoutManager;
    RvPlaylistsHomeAdapter rvPlaylistsHomeAdapter;
    private HomeViewModel homeViewModel;
    private RecyclerView rvRecentlyPlayed;
    private RecyclerView rvMadeForYou;
    private RecyclerView rvHeavyPlaylist;
    private RecyclerView rvPopularPlaylist;
    private RecyclerView rvBasedOnYourRecentlyPlayed;

    /**
     * inflate view of fragment
     *
     * @param inflater           inflate the fragment
     * @param container          viewgroup of the fragment
     * @param savedInstanceState saved data
     * @return fragment view
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(root);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        final ImageView ivSettings = root.findViewById(R.id.iv_setting_home);
        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                        R.id.nav_host_fragment, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        final FrameLayout frameLayout = root.findViewById(R.id.frame_home_fragment);
        final ScrollView scrollView = root.findViewById(R.id.sv_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    // get distance from padding and normalize it
                    float alpha = (float) ((scrollView.getPaddingTop() - scrollY) / (scrollView.getPaddingTop() * 1.0));
                    frameLayout.setAlpha(1 - alpha);
                    if (scrollView.getPaddingTop() > scrollY)
                        ivSettings.setAlpha(alpha);
                    if (alpha <= 0) {
                        ivSettings.setVisibility(View.GONE);
                    } else
                        ivSettings.setVisibility(View.VISIBLE);
                }
            });
        }

        return root;

    }

    /**
     * this function initialize views for fragment
     *
     * @param root this is the view that is inflated for this fragment
     */
    void initViews(View root) {

        // test

        ServiceController SController = ServiceController.getInstance();
        ArrayList<Playlist> playlists = SController.getRandomPlaylists(getContext(), Constants.currentToken);
        ArrayList<Playlist> popularPlaylists = SController.getPopularPlaylists(getContext(), Constants.currentToken);
        ArrayList<Playlist> recentPlaylists = SController.getRecentPlaylists(getContext(), Constants.currentToken);

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