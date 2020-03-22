package com.example.symphonia.Fragments_and_models.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.SearchResultAdapter;
import com.example.symphonia.Adapters.SeeAllArtistsAdapter;
import com.example.symphonia.Adapters.SeeAllPlaylistsAdapter;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

public class SearchForAllFragment extends Fragment {
    private String searchBy;
    private String searchFor;
    private TextView searchTitle;
    private RecyclerView searchResult;
    private RecyclerView.LayoutManager layoutMangr;
    private ImageView backArrow;

    private ServiceController cont;

    private View.OnClickListener back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().popBackStack();
        }
    };

    public SearchForAllFragment(String s1,String s2){
        searchBy=s1;
        searchFor=s2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_forall_layout, container, false);
        cont=ServiceController.getInstance();
        if(searchBy=="Playlists"){
            layoutMangr=new GridLayoutManager(getContext(),2);
        }
        else {
            layoutMangr=new LinearLayoutManager(getContext());
        }
        backArrow=(ImageView)root.findViewById(R.id.img_back_search_forall);
        backArrow.setOnClickListener(back);
        searchTitle=root.findViewById(R.id.tv_search_all_title);
        searchTitle.setText("\""+searchFor+"\" in "+searchBy);
        searchResult=(RecyclerView)root.findViewById(R.id.rv_search_result);
        searchResult.setLayoutManager(layoutMangr);
        searchResult.setHasFixedSize(true);
        if(searchBy=="Playlists") getPlatlistsData();
        else if(searchBy=="Artists") getArtistsData();
        else if(searchBy=="Profiles") getProfilesData();
        else if(searchBy=="Genres & Moods") getGenresData();
        else if(searchBy=="Songs") getSongsData();
        else if(searchBy=="Albums") getAlbumsData();
        else getSongsData();
        return root;
    }

    private void getPlatlistsData(){
        ArrayList<Container> data=cont.getPlaylists(getContext(),searchFor);
        SeeAllPlaylistsAdapter d=new SeeAllPlaylistsAdapter(data,false);
        searchResult.setAdapter(d);
    }

    private void getArtistsData(){
        ArrayList<Container> data=cont.getArtists(getContext(),searchFor);
        SeeAllArtistsAdapter d=new SeeAllArtistsAdapter(data);
        searchResult.setAdapter(d);
    }

    private void getSongsData(){
        ArrayList<Container> data=cont.getSongs(getContext(),searchFor);
        SearchResultAdapter d=new SearchResultAdapter(data,true,true);
        searchResult.setAdapter(d);
    }

    private void getAlbumsData(){
        ArrayList<Container> data=cont.getAlbums(getContext(),searchFor);
        SearchResultAdapter d=new SearchResultAdapter(data,true,true);
        searchResult.setAdapter(d);
    }

    private void getProfilesData(){
        ArrayList<Container> data=cont.getProfiles(getContext(),searchFor);
        SeeAllArtistsAdapter d=new SeeAllArtistsAdapter(data);
        searchResult.setAdapter(d);
    }

    private void getGenresData(){
        ArrayList<Container> data=cont.getGenresAndMoods(getContext(),searchFor);
        SeeAllArtistsAdapter d=new SeeAllArtistsAdapter(data);
        searchResult.setAdapter(d);
    }

}
