package com.example.symphonia.Fragments_and_models.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.google.android.material.appbar.AppBarLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private NestedScrollView nestedScrollView;
    private float firstY;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        nestedScrollView = rootView.findViewById(R.id.container);
        nestedScrollView.setNestedScrollingEnabled(false);


        AppBarLayout appBarLayout = rootView.findViewById(R.id.app_bar);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        params.setBehavior(new AppBarLayout.Behavior());
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        assert behavior != null;
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });

        final FragmentContainerView settingsFragment = rootView.findViewById(R.id.settings);
        settingsFragment.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.settings, new PreferenceFragment())
                .commit();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                settingsFragment.setVisibility(View.VISIBLE);
                nestedScrollView.scrollTo(0,0);
            }
        }, 1);

        final Button premiumButton = rootView.findViewById(R.id.button_go_premium);

        ConstraintLayout profileLayout = rootView.findViewById(R.id.layout_profile);
        final TextView userName = rootView.findViewById(R.id.text_user_name);
        final TextView viewProfile = rootView.findViewById(R.id.text_view_profile);

        premiumButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.95f, 0.5f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        return true;
                }

                return false;
            }
        });

        profileLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(userName, 1f, 0.5f);
                        Utils.startTouchAnimation(viewProfile, 1f, 0.5f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(userName);
                        Utils.cancelTouchAnimation(viewProfile);
                        return true;
                }
                return false;
            }

        });

        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstY = currentY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(premiumButton);
                            Utils.cancelTouchAnimation(userName);
                            Utils.cancelTouchAnimation(viewProfile);
                        }
                }
                return false;
            }
        });
        
        
        ImageView backIcon = rootView.findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }

}
