package com.example.symphonia.Fragments_and_models.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.FollowersAdapter;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

public class ProfileFollowersFragment extends Fragment {
    private ArrayList<Container>data;
    private ServiceController controller;
    private RecyclerView recyclerView;
    private TextView textView;
    private boolean getRightData;//to choose if we get the data of followers or following by artist

    public ProfileFollowersFragment(boolean b){
        getRightData=b;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_folllowers, container, false);

        controller=ServiceController.getInstance();

        recyclerView=root.findViewById(R.id.rv_followers_profile);
        textView=root.findViewById(R.id.tv_followers_following);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        data=new ArrayList<>();

        if(getRightData) {
            textView.setText(getResources().getString(R.string.followers));
            data=controller.getProfileFollowers(getContext());
                    }
        else {
            textView.setText(getResources().getString(R.string.Following));
            data=controller.getProfileFollowing(getContext());
        }
        FollowersAdapter adapter=new FollowersAdapter(data);
        recyclerView.setAdapter(adapter);
        return root;
    }
}
