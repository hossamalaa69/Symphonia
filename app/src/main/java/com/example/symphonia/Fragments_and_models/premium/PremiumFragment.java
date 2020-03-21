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

import com.example.symphonia.R;

public class PremiumFragment extends Fragment {

    private PremiumViewModel premiumViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        premiumViewModel = ViewModelProviders.of(this).get(PremiumViewModel.class);
        View root = inflater.inflate(R.layout.fragment_premium, container, false);

        TextView t1 = (TextView) root.findViewById(R.id.t1);
        t1.setMovementMethod(LinkMovementMethod.getInstance());




        return root;
    }
}