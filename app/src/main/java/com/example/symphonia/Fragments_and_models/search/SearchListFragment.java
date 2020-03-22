package com.example.symphonia.Fragments_and_models.search;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.SearchResultAdapter;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * @author Mahmoud Amr Nabil
 * @version 1.0
 * fragment to show searchlist layout
 */
public class SearchListFragment extends Fragment implements SearchResultAdapter.ListItemClickListner{
    private ServiceController controller;
    private SearchListFragment context;
    private SearchResultAdapter adapter1;
    private View filter;
    private View recentSearches;
    private FrameLayout frameLayout;
    private TextView textView1;
    private TextView textView2;
    private ImageView imgItem;
    private TextView searchText;
    private ImageView arrow_Img;
    private ImageView eraseText;
    private RecyclerView recentRecycler;
    private RecyclerView resultRecycler;
    private TextView clearRecentSearches;
    private EditText editText;
    private Drawable trackBackgroun;
    private TextView artistsText;
    private TextView songsText;
    private TextView playlistsText;
    private TextView albumsText;
    private TextView genresText;
    private TextView profilesText;
    SearchListFragment(){
        context=this;//get the context of the fragment
    }

    private View.OnClickListener CLearRecentListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter1=new SearchResultAdapter(new ArrayList<Container>(),false,context);
            recentRecycler.setAdapter(adapter1);
            controller.removeAllRecentSearches(getContext());
            recentSearchesOff();
        }
    };

    private View.OnClickListener Back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            getFragmentManager().popBackStack();
        }
    };

    private View.OnClickListener getAllSongs=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.search_include, new SearchForAllFragment("Songs",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllArtists=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.search_include, new SearchForAllFragment("Artists",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllAlbums=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.search_include, new SearchForAllFragment("Albums",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllPlaylists=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.search_include, new SearchForAllFragment("Playlists",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllGenres=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.search_include, new SearchForAllFragment("Genres & Moods",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllProfiles=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.search_include, new SearchForAllFragment("Profiles",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener Erase=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editText.setText("");
        }
    };

    private View.OnFocusChangeListener FocusListener=new View.OnFocusChangeListener(){

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                searchText.setVisibility(View.INVISIBLE);
                arrow_Img.setVisibility(View.VISIBLE);
            }
            else {
                searchText.setVisibility(View.INVISIBLE);
                arrow_Img.setVisibility(View.VISIBLE);
            }
        }
    };

    private TextWatcher filterTextWatcher = new TextWatcher() {
        /**
         * if edit text empty then recent searches layout shows
         * else results of search is shown
         * @param s the text user typed
         * @param start
         * @param before
         * @param count number of characters
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(count==0&&s.length()==0){
                eraseText.setVisibility(View.GONE);
                adapter1=new SearchResultAdapter(GetResentData(),false,context);
                recentRecycler.setAdapter(adapter1);
            }
            else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ArrayList<Container>f=GetResultData(s);
                    if(f.size()!=0) {
                        Drawable drawable = Utils.createSearchListBackground(getContext(),f.get(0));

                        // transition drawable controls the animation ov changing background
                   TransitionDrawable td = new TransitionDrawable(new Drawable[]{trackBackgroun, drawable});
                    trackBackgroun = drawable;
                    resultRecycler.setBackground(td);
                    td.startTransition(300);
                    }
                }
                if(controller.getArtists(getContext(),editText.getText().toString()).size()==0) artistsText.setVisibility(View.GONE);
                else artistsText.setVisibility(View.VISIBLE);
                if(controller.getAlbums(getContext(),editText.getText().toString()).size()==0) albumsText.setVisibility(View.GONE);
                else albumsText.setVisibility(View.VISIBLE);
                if(controller.getSongs(getContext(),editText.getText().toString()).size()==0) songsText.setVisibility(View.GONE);
                else songsText.setVisibility(View.VISIBLE);
                if(controller.getPlaylists(getContext(),editText.getText().toString()).size()==0) playlistsText.setVisibility(View.GONE);
                else playlistsText.setVisibility(View.VISIBLE);
                if(controller.getGenresAndMoods(getContext(),editText.getText().toString()).size()==0) genresText.setVisibility(View.GONE);
                else genresText.setVisibility(View.VISIBLE);
                if(controller.getProfiles(getContext(),editText.getText().toString()).size()==0) profilesText.setVisibility(View.GONE);
                else profilesText.setVisibility(View.VISIBLE);
                eraseText.setVisibility(View.VISIBLE);
                adapter1=new SearchResultAdapter(GetResultData(s),true,context);
                resultRecycler.setAdapter(adapter1);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.search_include, container, false);

        trackBackgroun=getResources().getDrawable(R.drawable.search_background);

        controller=ServiceController.getInstance();

        //attach views
        artistsText=root.findViewById(R.id.tv_search_artists);
        songsText=root.findViewById(R.id.tv_search_songs);
        albumsText=root.findViewById(R.id.tv_search_albums);
        playlistsText=root.findViewById(R.id.tv_search_playlists);
        genresText=root.findViewById(R.id.tv_search_genres);
        profilesText=root.findViewById(R.id.tv_search_profiles);
        editText = (EditText) root.findViewById(R.id.text_search_edit);
        eraseText=(ImageView)root.findViewById(R.id.close_search_rectangle);
        clearRecentSearches=(TextView)root.findViewById(R.id.tv_clear_recent_searches);
        searchText=root.findViewById(R.id.search_list_text);
        arrow_Img=root.findViewById(R.id.img_arrow_left);
        recentSearches=root.findViewById(R.id.recent_searches_layout);
        filter=root.findViewById(R.id.search_list_filter_layout);
        frameLayout=root.findViewById(R.id.frame_recycler_list_search);
        textView1=(TextView)root.findViewById(R.id.find_music_text);
        textView2=(TextView)root.findViewById(R.id.find_music_text2);
        imgItem=(ImageView)root.findViewById(R.id.img_best_search);
        recentRecycler = root.findViewById(R.id.recycler_recent_searches);
        resultRecycler = root.findViewById(R.id.recycler_list_search);


        //handle clicking
        artistsText.setOnClickListener(getAllArtists);
        albumsText.setOnClickListener(getAllAlbums);
        playlistsText.setOnClickListener(getAllPlaylists);
        genresText.setOnClickListener(getAllGenres);
        profilesText.setOnClickListener(getAllProfiles);
        eraseText.setOnClickListener(Erase);
        songsText.setOnClickListener(getAllSongs);
        clearRecentSearches.setOnClickListener(CLearRecentListener);
        arrow_Img.setOnClickListener(Back);

        //add a text change listener to get the result of search permanently
        editText.addTextChangedListener(filterTextWatcher);

        //give recentRecycler its configuration
        recentRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager LM = new LinearLayoutManager(getContext());
        recentRecycler.setLayoutManager(LM);
        recentRecycler.setHasFixedSize(false);
        adapter1=new SearchResultAdapter(GetResentData(),false,context);
        recentRecycler.setAdapter(adapter1);

        //give resultRecycler its configuration
        resultRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager LM2 = new LinearLayoutManager(getContext());
        resultRecycler.setLayoutManager(LM2);
        resultRecycler.setHasFixedSize(false);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            /**
             *if user click on action_search that will hide the keyboard
             * @param v The view that was clicked.
             * @param actionId id of the action
             * @param event If triggered by an enter key, this is the event; otherwise, this is null.
             * @return true if you have consumed the action, else false.
             */
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        //give editText a FocusChangeListener so when click on it set the viability of the icons
        editText.setOnFocusChangeListener(FocusListener);
        return root;
    }

    /**
     *
     * @param s the search word the user searched with
     * @return ArrayList of Container which has the data of search results
     */
    private ArrayList<Container> GetResultData(CharSequence s) {
        ArrayList<Container> data=controller.getResultsOfSearch(getContext(),s.toString());
        if (data.size() == 0) {
            searchResultOff(s);
        } else {
            searchResultOn();
        }
        return data;
    }

    /**
     * get the recent data that user searched for
     * @return ArrayList of Container which has the data of recent searches
     */
    private ArrayList<Container> GetResentData() {
        ArrayList<Container> data=controller.getResentResult(getContext());

        if(data.size()==0){
            recentSearchesOff();
        } else {
            recentSearchesOn();
        }
        return data;
    }

    /**
     * is called to show the recent searches layout
     */
    private void recentSearchesOn() {
        frameLayout.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        imgItem.setVisibility(View.GONE);
        recentSearches.setVisibility(View.VISIBLE);
    }

    /**
     * is called to hide the recent searches layout
     */
    public void recentSearchesOff() {

        filter.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        textView1.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        imgItem.setVisibility(View.VISIBLE);
        recentSearches.setVisibility(View.GONE);
        textView1.setText(getContext().getResources().getString(R.string.Find_the_music_you_love));
        textView2.setText(getContext().getResources().getString(R.string.Search_for_anything));
        imgItem.setImageResource(R.mipmap.big_search_foreground);
    }

    /**
     * is called to show the search result layout
     */
    private void searchResultOn() {
        frameLayout.setVisibility(View.VISIBLE);
        filter.setVisibility(View.VISIBLE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        imgItem.setVisibility(View.GONE);
        recentSearches.setVisibility(View.GONE);
    }

    /**
     * is called to hide the search result layout
     */
    private void searchResultOff(CharSequence s) {
        frameLayout.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        textView1.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        imgItem.setVisibility(View.VISIBLE);
        recentSearches.setVisibility(View.GONE);
        textView1.setText(getContext().getResources().getString(R.string.No_result_found_for)+"\"" + s + "\"");
        textView2.setText(getContext().getResources().getString(R.string.help_to_search));
        imgItem.setImageResource(R.mipmap.flag_white_foreground);
    }

    /**
     *implement SearchResultAdapter.ListItemClickListner.
     * when click the item is deleted and if the number of items equals zero
     * then the recent searches layout will hide.
     * @param pos the position of item to be deleted
     * @param containerSize the number of items
     */
    @Override
    public void onItemEraseListener(int pos, int containerSize) {
        adapter1.notifyItemRemoved(pos);
        adapter1.notifyItemRangeChanged(pos, containerSize);
        controller.removeOneRecentSearch(getContext(),pos);
        if(containerSize==0)
            recentSearchesOff();
    }


}
