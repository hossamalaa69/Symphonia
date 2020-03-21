package com.example.symphonia.Fragments_and_models.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.SeeAllPlaylistsAdapter;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;

import java.util.ArrayList;

public class PopularPlaylistsFragment extends Fragment {
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.all_popular_playlists, container, false);
        recyclerView=root.findViewById(R.id.rv_all_popular_playlists);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        SeeAllPlaylistsAdapter adapter=new SeeAllPlaylistsAdapter(getAllPopularPlaylists(),true);
        recyclerView.setAdapter(adapter);
        return root;
    }
    private ArrayList<Container> getAllPopularPlaylists(){
        ArrayList<Container>data=new ArrayList<>();
        data.add(new Container("greate playlist","2,700 followers",R.drawable.adele));
        data.add(new Container("Amr Diab","5,200 followers",R.drawable.amr));
        data.add(new Container("beautiful","3,300 followers",R.drawable.imagine));
        data.add(new Container("simple","800 followers",R.drawable.alan));
        data.add(new Container("Bahaa Sultan","2,100 followers",R.drawable.bahaa));
        data.add(new Container("anghami","1,100 followers",R.drawable.angham));
        return data;
    }
}
