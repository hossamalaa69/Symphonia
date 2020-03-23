package com.example.symphonia.Fragments_and_models.search;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.SeeAllPlaylistsAdapter;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

/**
 * fragment to show category_layout
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class CategoryFragment extends Fragment {
    private ServiceController serviceController;
    private TextView textView;
    private TextView textView2;
    private Container category;
    private ImageView back;
    private RecyclerView popularPlaylists;
    private LinearLayout linearLayout;
    private AppBarLayout appBarLayout;
    private LinearLayout animatedLayout;
    private View background;
    private Button button;
    private View.OnClickListener backListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().popBackStack();
        }
    };
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.category_layout,new PopularPlaylistsFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    };
    public CategoryFragment(Container cat){
        category=cat;
    }
    /**
     *attach views and add data to the layout
     * @param inflater inflate fragment layout
     * @param container fragment viewgroup
     * @param savedInstanceState saved data from previous calls
     * @return fragment view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.category_layout, container, false);
        //get instance of serviceController
        serviceController=ServiceController.getInstance();
        //attach views
        back=root.findViewById(R.id.img_back_search_main);
        button=root.findViewById(R.id.btn_see_more);
        animatedLayout=root.findViewById(R.id.animated_layout);
        textView=root.findViewById(R.id.tv_search_background);
        textView2=root.findViewById(R.id.tv_category_name_animated);
        background=root.findViewById(R.id.category_include);
        appBarLayout=root.findViewById(R.id.app_bar);
        popularPlaylists=root.findViewById(R.id.rv_popular_playlists);

        textView.setText(category.getCat_Name());
        textView2.setText(category.getCat_Name());

        //handle clicks
        back.setOnClickListener(backListener);
        button.setOnClickListener(listener);

        Drawable drawable= Utils.createCategoryBackground(getContext(),category);
        background.setBackground(drawable);

        //give popularPlaylists data and setup
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        popularPlaylists.setLayoutManager(layoutManager);
        popularPlaylists.setHasFixedSize(false);
        popularPlaylists.setNestedScrollingEnabled(false);
        SeeAllPlaylistsAdapter adapter=new SeeAllPlaylistsAdapter(getPopularPlaylists(),true);
        popularPlaylists.setAdapter(adapter);

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
                if (dp<=155&&dp>=0) {
                    if(dp==0) {
                        appBarLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0f));
                        animatedLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0f));
                        textView2.setTextColor(getColorWithAlpha(getResources().getColor(R.color.colorWhite), 0f));
                    }
                    else if(dp<25){
                        appBarLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.2f));
                        animatedLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.2f));
                        textView2.setTextColor(getColorWithAlpha(getResources().getColor(R.color.colorWhite),0.2f));
                    }
                    else if(dp<50){
                        appBarLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.33f));
                        animatedLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.33f));
                        textView2.setTextColor(getColorWithAlpha(getResources().getColor(R.color.colorWhite),0.33f));
                    }
                    else if(dp<75){
                        appBarLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.5f));
                        animatedLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.5f));
                        textView2.setTextColor(getColorWithAlpha(getResources().getColor(R.color.colorWhite),.5f));
                    }
                    else if(dp<100){
                        appBarLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.66f));
                        animatedLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.66f));
                        textView2.setTextColor(getColorWithAlpha(getResources().getColor(R.color.colorWhite),.66f));
                    }
                    else if(dp<125){
                        appBarLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.8f));
                        animatedLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 0.8f));
                        textView2.setTextColor(getColorWithAlpha(getResources().getColor(R.color.colorWhite),.8f));
                    }
                    else if(dp<=155){
                        appBarLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 1f));
                        animatedLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), 1f));
                        textView2.setTextColor(getColorWithAlpha(getResources().getColor(R.color.colorWhite),1f));
                    }
                }
            }
        });
        return root;
    }

    /**
     *multiply the alpha channel of the color by ratio to control transparency
     * @param color
     * @param ratio ratio of transparency
     * @return color after changing the alpha channel
     */
    private int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    /**
     * get the data of the popular playlists
     * @return ArrayList of Container of Popular Playlists data
     */
    private ArrayList<Container> getPopularPlaylists(){
        ArrayList<Container>data=serviceController.getFourPlaylists(getContext());
        if(data.size()<4)button.setVisibility(View.GONE);
        return data;
    }
}
