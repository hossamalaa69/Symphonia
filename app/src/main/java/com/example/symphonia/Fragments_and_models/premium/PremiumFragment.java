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

        TextView text_view_anchor = (TextView) root.findViewById(R.id.t1);
        text_view_anchor.setMovementMethod(LinkMovementMethod.getInstance());

        mFeaturesFree = new ArrayList<>();
        mFeaturesFree.add(getResources().getString(R.string.ad_break));
        mFeaturesFree.add(getResources().getString(R.string.with_locked_songs));

        mFeaturesPrem = new ArrayList<>();
        mFeaturesPrem.add(getResources().getString(R.string.ad_free_music));
        mFeaturesPrem.add(getResources().getString(R.string.play_any_song));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()
                    , LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = root.findViewById(R.id.rv_premium);
        SnapHelper snapHelper = new PagerSnapHelper();
        layoutManager.findFirstVisibleItemPosition();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new com.example.symphonia.Adapters.CirclePagerIndicatorDecoration());
        snapHelper.attachToRecyclerView(recyclerView);
        PremiumAdapter adapter = new PremiumAdapter(mFeaturesFree, mFeaturesPrem, getContext());
        recyclerView.setAdapter(adapter);
        return root;
    }

}