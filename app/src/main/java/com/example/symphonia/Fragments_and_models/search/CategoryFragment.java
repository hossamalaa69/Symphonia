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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CategoryFragment extends Fragment {
    private TextView catName;
    private TextView catNameHidden;
    private ImageView back;
    private RecyclerView popularPlaylists;
    private LinearLayout linearLayout;
    private AppBarLayout appBarLayout;
    private LinearLayout background;
    private View background2;

    public CategoryFragment(Container category){
        catName.setText(category.getCat_Name());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.category_layout, container, false);
        catName=root.findViewById(R.id.tv_category_name_animated);

        background2=root.findViewById(R.id.category_include);
        CategoryFragment.SomeDrawable drawable=new SomeDrawable(getResources().getColor(R.color.colorGreen),Color.BLACK);
        background2.setBackground(drawable);
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

}
