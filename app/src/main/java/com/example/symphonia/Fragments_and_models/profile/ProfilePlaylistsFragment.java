package com.example.symphonia.Fragments_and_models.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.ProfilePlaylistsAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

public class ProfilePlaylistsFragment extends Fragment implements ProfilePlaylistsAdapter.ProfileplaylistItemClickListner {
    private ServiceController controller;
    private RecyclerView recyclerView;
    private ImageView backImg;
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager().popBackStack();
        }
    };
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.all_profile_playlists, container, false);
        controller=ServiceController.getInstance();

        recyclerView=root.findViewById(R.id.rv_all_profile_playlists);
        backImg=root.findViewById(R.id.img_back_profile_main);

        backImg.setOnClickListener(listener);
        if(!Constants.DEBUG_STATUS) {
            RestApi restApi=new RestApi();
            restApi.getAllCurrentUserPlaylists(getContext(),this);
        }
        else {
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            ProfilePlaylistsAdapter adapter = new ProfilePlaylistsAdapter(controller.getAllPopularPlaylists(getContext()), this);
            recyclerView.setAdapter(adapter);
        }
        return root;
    }

    @Override
    public void onProfileItemlongClickListener(Container c) {
        BottomSheetDialogProfile bottomSheet = new BottomSheetDialogProfile(c,3);
        assert getParentFragmentManager() != null;
        bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());
    }

    public void updatePlaylists(ArrayList<Container>c){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ProfilePlaylistsAdapter adapter = new ProfilePlaylistsAdapter(c, this);
        recyclerView.setAdapter(adapter);
    }

}
