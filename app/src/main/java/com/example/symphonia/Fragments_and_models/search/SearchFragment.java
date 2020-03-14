package com.example.symphonia.Fragments_and_models.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Adapters.SearchMainAdapter;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private ServiceController con;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_search_view, new SearchListFragment()) // Add this transaction to the back stack (name is an optional name for this back stack state, or null).
                    .addToBackStack(null)
                    .commit();
        }
    };


    private SearchMainAdapter catAdapter;
    private SearchMainAdapter genresAdapter;
    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        searchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        con=ServiceController.getInstance();

        RelativeLayout RL = root.findViewById(R.id.send_to_serchlist);
        RL.setOnClickListener(listener);

        ArrayList<Container> Genre = con.getGenres(getContext());

        RecyclerView RV = root.findViewById(R.id.search_top_genres_grid);
        RV.setNestedScrollingEnabled(false);
        GridLayoutManager LM = new GridLayoutManager(getContext(), 2);
        RV.setLayoutManager(LM);
        RV.setHasFixedSize(true);
        genresAdapter = new SearchMainAdapter(Genre);
        RV.setAdapter(genresAdapter);

        ArrayList<Container> Category = con.getCategories(getContext());

        RecyclerView RV2 = root.findViewById(R.id.search_browse_all_grid);
        RV2.setNestedScrollingEnabled(false);
        GridLayoutManager LM2 = new GridLayoutManager(getContext(), 2);
        RV2.setLayoutManager(LM2);
        RV2.setHasFixedSize(true);
        catAdapter = new SearchMainAdapter(Category);
        RV2.setAdapter(catAdapter);

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}