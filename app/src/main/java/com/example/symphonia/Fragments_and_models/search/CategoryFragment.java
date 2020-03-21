package com.example.symphonia.Fragments_and_models.search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.SeeAllPlaylistsAdapter;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.category_layout, container, false);
        serviceController=ServiceController.getInstance();
        back=root.findViewById(R.id.img_back_search_main);
        back.setOnClickListener(backListener);
        button=root.findViewById(R.id.btn_see_more);
        button.setOnClickListener(listener);
        animatedLayout=root.findViewById(R.id.animated_layout);
        textView=root.findViewById(R.id.tv_search_background);
        textView2=root.findViewById(R.id.tv_category_name_animated);
        textView.setText(category.getCat_Name());
        textView2.setText(category.getCat_Name());
        background=root.findViewById(R.id.category_include);
        Drawable drawable=createBackground(category);
        background.setBackground(drawable);
        appBarLayout=root.findViewById(R.id.app_bar);
        popularPlaylists=root.findViewById(R.id.rv_popular_playlists);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        popularPlaylists.setLayoutManager(layoutManager);
        popularPlaylists.setHasFixedSize(false);
        popularPlaylists.setNestedScrollingEnabled(false);
        SeeAllPlaylistsAdapter adapter=new SeeAllPlaylistsAdapter(getPopularPlaylists(),true);
        popularPlaylists.setAdapter(adapter);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
     * create gradient background for track
     *
     * @return
     */
    private Drawable createBackground(Container container) {
        int color = getDominantColor(BitmapFactory.decodeResource(getContext().getResources()
                , container.getImg_Res()));

        CategoryFragment.SomeDrawable drawable = new CategoryFragment.SomeDrawable(color, Color.BLACK);
        return drawable;

    }

    /**
     * create gradient drawable for track image
     */
    public class SomeDrawable extends GradientDrawable {

        public SomeDrawable(int pStartColor, int pEndColor) {
            super(Orientation.BR_TL, new int[]{pEndColor, pEndColor,pEndColor ,pStartColor});
            setShape(GradientDrawable.RECTANGLE);
        }

    }

    public static int getDominantColor(Bitmap bitmap) {
        List<Palette.Swatch> swatchesTemp = Palette.from(bitmap).generate().getSwatches();
        List<Palette.Swatch> swatches = new ArrayList<Palette.Swatch>(swatchesTemp);
        Collections.sort(swatches, new Comparator<Palette.Swatch>() {
            @Override
            public int compare(Palette.Swatch swatch1, Palette.Swatch swatch2) {
                return swatch2.getPopulation() - swatch1.getPopulation();
            }
        });
        return swatches.size() > 0 ? swatches.get(0).getRgb() : 0;
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

    private ArrayList<Container> getPopularPlaylists(){
        ArrayList<Container>data=serviceController.getFourPlaylists(getContext());
        if(data.size()<4)button.setVisibility(View.GONE);
        return data;
    }
}
