package com.example.symphonia.Fragments_and_models.premium;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.symphonia.Adapters.PremiumAdapter;
import com.example.symphonia.R;

import java.util.ArrayList;

public class PremiumFragment extends Fragment {

    private PremiumViewModel mPremiumViewModel;

    private ArrayList<String> mFeaturesFree;
    private ArrayList<String> mFeaturesPrem;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPremiumViewModel = ViewModelProviders.of(this).get(PremiumViewModel.class);
        View root = inflater.inflate(R.layout.fragment_premium, container, false);

        //makes text view with anchor to be clickable
        TextView text_view_anchor = (TextView) root.findViewById(R.id.t1);
        text_view_anchor.setMovementMethod(LinkMovementMethod.getInstance());

        //fills arrays of features from stored strings for free
        mFeaturesFree = new ArrayList<>();
        mFeaturesFree.add(getResources().getString(R.string.ad_break));
        mFeaturesFree.add(getResources().getString(R.string.with_locked_songs));

        //fills arrays of features from stored strings for premium
        mFeaturesPrem = new ArrayList<>();
        mFeaturesPrem.add(getResources().getString(R.string.ad_free_music));
        mFeaturesPrem.add(getResources().getString(R.string.play_any_song));

        //object of layoutManager that controls recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()
                    , LinearLayoutManager.HORIZONTAL, false);

        //gets layout of recycler view by id
        RecyclerView recyclerView = root.findViewById(R.id.rv_premium);

        //snapHelper that makes features for recycler view
        SnapHelper snapHelper = new PagerSnapHelper();

        //indicates the position of current item for dotIndicator
        layoutManager.findFirstVisibleItemPosition();
        recyclerView.setLayoutManager(layoutManager);

        //set Dot indicator for recycler view
        recyclerView.addItemDecoration(new com.example.symphonia.Adapters.CirclePagerIndicatorDecoration());

        //makes one item shown in scrolling
        snapHelper.attachToRecyclerView(recyclerView);

        //set adapter of recycler view with arrays of features
        PremiumAdapter adapter = new PremiumAdapter(mFeaturesFree, mFeaturesPrem, getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }
}