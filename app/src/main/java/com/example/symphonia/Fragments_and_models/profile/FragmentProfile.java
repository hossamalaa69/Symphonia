package com.example.symphonia.Fragments_and_models.profile;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.ProfilePlaylistsAdapter;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.appbar.AppBarLayout;

/**
 * FragmentProfile to show the fragment_profile layout
 *  * @author Mahmoud Amr Nabil
 *  * @version 1.0
 */
public class FragmentProfile extends Fragment {
    private ServiceController controller;
    private Container profile;
    private LinearLayout animatedLayout;
    private View background;
    private TextView profileName;
    private TextView backgroundProfileName;
    private AppBarLayout appBarLayout;
    private ImageView profileImage;
    private RecyclerView recyclerView;
    private Button seeAll;

    public FragmentProfile(Container c){
        profile=c;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        //get instance of Service controller
        controller=ServiceController.getInstance();
        //attach views
        background=root.findViewById(R.id.profile_background);
        profileName=root.findViewById(R.id.tv_profile_name_animated);
        backgroundProfileName=root.findViewById(R.id.tv_profile_background);
        appBarLayout=root.findViewById(R.id.app_bar_profile);
        animatedLayout=root.findViewById(R.id.profile_animated_layout);
        profileImage=root.findViewById(R.id.profile_image);
        recyclerView=root.findViewById(R.id.rv_profile_playlists);
        seeAll=root.findViewById(R.id.btn_see_all_profile_playlists);

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.profile_main_layout,new ProfilePlaylistsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        profileName.setText(profile.getCat_Name());
        backgroundProfileName.setText(profile.getCat_Name());
        Drawable drawable=Utils.createSearchListBackground(getContext(),profile);
        background.setBackground(drawable);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        ProfilePlaylistsAdapter adapter=new ProfilePlaylistsAdapter(controller.getFourPlaylists(getContext()));
        recyclerView.setAdapter(adapter);
        //handle animation
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            /**
             * give views transparency depnding on i
             * @param appBarLayout  the AppBarLayout which offset has changed
             * @param i the vertical offset for the parent AppBarLayout, in px
             */
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                float density = getResources().getDisplayMetrics().density;
                float dp=Math.abs(i)/density;
                float alphaRatio=1;
                if(dp<=300&&dp>=0) {
                    if (dp == 300) alphaRatio = 1;
                    else if (dp == 0) alphaRatio = 0;
                    else {
                        alphaRatio = (float) Math.ceil(dp / 30);
                        alphaRatio /= 10;
                    }
                    appBarLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), alphaRatio));
                    animatedLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), alphaRatio));
                    profileName.setTextColor(getColorWithAlpha(getResources().getColor(R.color.colorWhite), alphaRatio));
                }
                if(dp<=100&&dp>=0){
                    profileImage.setScaleX((float) (1));
                    profileImage.setScaleY((float) (1));
                }
                else if(dp<=200&&dp>100){
                    profileImage.setScaleX((float) (1-alphaRatio+.3));
                    profileImage.setScaleY((float) (1-alphaRatio+.3));
                }

            }
        });
        return root;
    }

    private int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

}
