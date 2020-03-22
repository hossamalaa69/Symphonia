package com.example.symphonia.Fragments_and_models.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.SeeAllPlaylistsAdapter;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * @author Mahmoud Amr Nabil
 * @version 1.0
 * fragment to show all_popular_playlists layout
 */
public class PopularPlaylistsFragment extends Fragment {
    private ServiceController controller;
    private RecyclerView recyclerView;
    private ImageView back;
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().popBackStack();
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.all_popular_playlists, container, false);
        //get instance of ServiceController
        controller=ServiceController.getInstance();

        //attach views
        back=root.findViewById(R.id.img_back_category);
        recyclerView=root.findViewById(R.id.rv_all_popular_playlists);

        //handle click
        back.setOnClickListener(listener);

        //add data and setup for recyclerView
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        SeeAllPlaylistsAdapter adapter=new SeeAllPlaylistsAdapter(getAllPopularPlaylists(),true);
        recyclerView.setAdapter(adapter);

        return root;
    }

    /**
     *
     * @return Container ArrayList which has data of all popular playlists
     */
    private ArrayList<Container> getAllPopularPlaylists(){
        ArrayList<Container>data=controller.getAllPopularPlaylists(getContext());
        return data;
    }
}
