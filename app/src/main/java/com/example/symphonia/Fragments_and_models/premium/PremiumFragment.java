package com.example.symphonia.Fragments_and_models.premium;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.symphonia.Adapters.PremiumAdapter;
import com.example.symphonia.R;

import java.util.ArrayList;

public class PremiumFragment extends Fragment {

    private PremiumViewModel premiumViewModel;
    private ArrayList<String>featuresFree;
    private ArrayList<String>featuresPrem;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        premiumViewModel = ViewModelProviders.of(this).get(PremiumViewModel.class);
        View root = inflater.inflate(R.layout.fragment_premium, container, false);

        TextView t1 = (TextView) root.findViewById(R.id.t1);
        t1.setMovementMethod(LinkMovementMethod.getInstance());

        featuresFree = new ArrayList<>();
        featuresFree.add(getResources().getString(R.string.ad_break));
        featuresFree.add(getResources().getString(R.string.with_locked_songs));

        featuresPrem = new ArrayList<>();
        featuresPrem.add(getResources().getString(R.string.ad_free_music));
        featuresPrem.add(getResources().getString(R.string.play_any_song));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.rv_premium);
        SnapHelper snapHelper = new PagerSnapHelper();
        layoutManager.findFirstVisibleItemPosition();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new com.example.symphonia.Adapters.CirclePagerIndicatorDecoration());
        snapHelper.attachToRecyclerView(recyclerView);
        PremiumAdapter adapter = new PremiumAdapter(featuresFree, featuresPrem, getContext());
        recyclerView.setAdapter(adapter);
        return root;
    }


}