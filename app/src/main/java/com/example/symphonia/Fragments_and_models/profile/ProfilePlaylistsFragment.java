package com.example.symphonia.Fragments_and_models.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.ProfilePlaylistsAdapter;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

public class ProfilePlaylistsFragment extends Fragment {
    private ServiceController controller;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.all_profile_playlists, container, false);
        controller=ServiceController.getInstance();
        recyclerView=root.findViewById(R.id.rv_all_profile_playlists);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ProfilePlaylistsAdapter adapter=new ProfilePlaylistsAdapter(controller.getAllPopularPlaylists(getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }
}
