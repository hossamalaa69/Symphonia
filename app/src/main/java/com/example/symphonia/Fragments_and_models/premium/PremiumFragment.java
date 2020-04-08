package com.example.symphonia.Fragments_and_models.premium;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.PremiumAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * Fragment that handles user interaction with premium page
 * handles all requests to be premium account
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class PremiumFragment extends Fragment {

    /**
     * array of free features to be displayed
     */
    private ArrayList<String> mFeaturesFree;
    /**
     * array of premium features to be displayed
     */
    private ArrayList<String> mFeaturesPrem;

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater object that can be used to inflate any views in the fragment
     * @param container parent view that the fragment's UI should be attached to.
     * @param savedInstanceState this fragment is being re-constructed from a previous saved state as given here.
     * @return returns the View for the fragment's UI
     */

    private TextView text_view_try_premium;
    private TextView text_view_current_plan;
    private Button btn_promote;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //sets layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_premium, container, false);

        checkPremium(root);

        ((MainActivity)getActivity()).setRoot(true);
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

        btn_promote = (Button) root.findViewById(R.id.promote_premium);
        btn_promote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ServiceController serviceController = ServiceController.getInstance();

                serviceController.promotePremium(getContext(), root, Constants.currentToken);
                checkPremium(root);
            }
        });

        return root;
    }

    public void checkPremium(View root){
        btn_promote = (Button) root.findViewById(R.id.promote_premium);
        text_view_current_plan = (TextView) root.findViewById(R.id.try_premium);
        text_view_try_premium = (TextView) root.findViewById(R.id.symphonia_free);

        if(Constants.currentUser.isPremuim()){
            btn_promote.setEnabled(false);
            btn_promote.setBackgroundResource(R.drawable.btn_curved_gray);
            text_view_current_plan.setText(getResources().getString(R.string.symphonia_premium));
            text_view_try_premium.setText(getResources().getString(R.string.promoted_premium));
        } else{
            btn_promote.setEnabled(true);
            btn_promote.setBackgroundResource(R.drawable.btn_curved_white);
            text_view_current_plan.setText(getResources().getString(R.string.try_premium));
            text_view_try_premium.setText(getResources().getString(R.string.spotify_free));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).setRoot(false);
    }
}