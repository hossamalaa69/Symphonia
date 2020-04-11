package com.example.symphonia.Fragments_and_models.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Adapters.SearchMainAdapter;
import com.example.symphonia.Entities.Category;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * SearchFragment to show the fragment_search layout
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class SearchFragment extends Fragment implements SearchMainAdapter.CatListItemClickListner {

    private ServiceController con;

    private RecyclerView RV2;

    View root;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        /**
         * move to SearchListFragment
         */
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new SearchListFragment())
                    .addToBackStack(null)
                    .commit();
        }
    };

    private SearchMainAdapter catAdapter;
    private SearchMainAdapter genresAdapter;

    private SearchViewModel searchViewModel;
    /**
     *attach views and add data to the layout
     * @param inflater inflate fragment layout
     * @param container fragment viewgroup
     * @param savedInstanceState saved data from previous calls
     * @return fragment view
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

            root = inflater.inflate(R.layout.fragment_search, container, false);
        //get instance of the service controller
        con=ServiceController.getInstance();

        //attach views
        LinearLayout RL = root.findViewById(R.id.send_to_serchlist);
        RecyclerView RV = root.findViewById(R.id.search_top_genres_grid);
        RV2 = root.findViewById(R.id.search_browse_all_grid);

        //handle click
        RL.setOnClickListener(listener);

        //add data and setup for RV
        ArrayList<Category> Genre = con.getGenres(getContext());
        RV.setNestedScrollingEnabled(false);//disable the scroll of the recycler view
        GridLayoutManager LM = new GridLayoutManager(getContext(), 2);
        RV.setLayoutManager(LM);
        RV.setHasFixedSize(true);
        genresAdapter = new SearchMainAdapter(Genre,this);
        RV.setAdapter(genresAdapter);

        //add data and setup for RV2
        //RestApi restApi=new RestApi();
        ArrayList<Category> Category = con.getCategories(getContext());
        RV2.setNestedScrollingEnabled(false);
        GridLayoutManager LM2 = new GridLayoutManager(getContext(), 2);
        RV2.setLayoutManager(LM2);
        RV2.setHasFixedSize(true);
        catAdapter = new SearchMainAdapter(Category,this);
        RV2.setAdapter(catAdapter);

        return root;
    }

    /**
     *implement SearchMainAdapter.CatListItemClickListner function
     * go to CategoryFragment when click on any category
     * @param c cotainer which has the information of the category that will be shown
     */
    @Override
    public void onListItemClickListner(Category c) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new CategoryFragment(c))
                .addToBackStack(null)
                .commit();
    }


    public void UpdateUiGetCategories(ArrayList<Category> c){

        RV2 = root.findViewById(R.id.search_browse_all_grid);
        RV2.setNestedScrollingEnabled(false);
        GridLayoutManager LM2 = new GridLayoutManager(getContext(), 2);
        RV2.setLayoutManager(LM2);
        RV2.setHasFixedSize(true);
        catAdapter = new SearchMainAdapter(c,this);
        RV2.setAdapter(catAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setRoot(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).setRoot(false);
    }
}