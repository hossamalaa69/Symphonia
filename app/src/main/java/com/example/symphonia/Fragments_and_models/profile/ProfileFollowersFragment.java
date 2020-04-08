package com.example.symphonia.Fragments_and_models.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.FollowersAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

public class ProfileFollowersFragment extends Fragment implements FollowersAdapter.ProfileFollowersItemClickListner{
    private ArrayList<Container>data;
    private ServiceController controller;
    private RecyclerView recyclerView;
    private TextView textView;
    private ImageView backImg;
    private View.OnClickListener back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager().popBackStack();
        }
    };

    private boolean getRightData;//to choose if we get the data of followers or following by artist

    public ProfileFollowersFragment(boolean b){
        getRightData=b;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_folllowers, container, false);

        controller=ServiceController.getInstance();

        recyclerView=root.findViewById(R.id.rv_followers_profile);
        textView=root.findViewById(R.id.tv_followers_following);
        backImg=root.findViewById(R.id.img_back_profile_main);

        backImg.setOnClickListener(back);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        data=new ArrayList<>();
        RestApi restApi=new RestApi();
        if(getRightData) {
            textView.setText(getResources().getString(R.string.followers));
            if(!Constants.DEBUG_STATUS)
                restApi.getCurrentUserFollowers(getContext(),this);
            else
            data=controller.getCurrentUserFollowers(getContext(),this);
            //data=controller.getProfileFollowers(getContext());
                    }
        else {
            textView.setText(getResources().getString(R.string.Following));
            if(!Constants.DEBUG_STATUS) restApi.getCurrentUserFollowing(getContext(),this);
            else data=controller.getProfileFollowing(getContext());

        }
        if(Constants.DEBUG_STATUS) {
            FollowersAdapter adapter = new FollowersAdapter(data, this);
            recyclerView.setAdapter(adapter);
        }
        return root;
    }

    @Override
    public void onProfileFollowerItemlongClickListener(Container c) {
        BottomSheetDialogProfile bottomSheet = new BottomSheetDialogProfile(c,4);
        assert getParentFragmentManager() != null;
        bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());
    }

    public void updateUiFollowers(ArrayList<Container> c){
        FollowersAdapter adapter = new FollowersAdapter(c, this);
        recyclerView.setAdapter(adapter);
    }

    public void updateUiFollowing(ArrayList<Container> c){
        FollowersAdapter adapter = new FollowersAdapter(c, this);
        recyclerView.setAdapter(adapter);
    }
}
