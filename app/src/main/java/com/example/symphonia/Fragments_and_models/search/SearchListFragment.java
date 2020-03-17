package com.example.symphonia.Fragments_and_models.search;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.SearchResultAdapter;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


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
        context=this;
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
                eraseText.setVisibility(View.VISIBLE);
            }
            else {
                searchText.setVisibility(View.INVISIBLE);
                arrow_Img.setVisibility(View.VISIBLE);
                eraseText.setVisibility(View.GONE);
            }
        }
    };

    private TextWatcher filterTextWatcher = new TextWatcher() {

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
                        Drawable drawable = createBackground(f.get(0));

                        // transition drawable controls the animation ov changing background
                   TransitionDrawable td = new TransitionDrawable(new Drawable[]{trackBackgroun, drawable});
                    trackBackgroun = drawable;
                    resultRecycler.setBackground(td);
                    td.startTransition(300);
                        //resultRecycler.setBackground(drawable);
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

    /*private ViewTreeObserver.OnGlobalLayoutListener d=new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int x=listview.getRootView().getHeight();
            int y=listview.getHeight();
            int heightDiff = x-y;
            if (heightDiff > 100) {
                t.makeText(getContext(),"yes",Toast.LENGTH_SHORT);
            }
            else     //it will work
            {
                t.makeText(getContext(),"no",Toast.LENGTH_SHORT);
            }

        }
    };*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.search_include, container, false);
        trackBackgroun=getResources().getDrawable(R.drawable.search_background);

        controller=ServiceController.getInstance();
        artistsText=root.findViewById(R.id.tv_search_artists);
        artistsText.setOnClickListener(getAllArtists);
        songsText=root.findViewById(R.id.tv_search_songs);
        songsText.setOnClickListener(getAllSongs);
        albumsText=root.findViewById(R.id.tv_search_albums);
        albumsText.setOnClickListener(getAllAlbums);
        playlistsText=root.findViewById(R.id.tv_search_playlists);
        playlistsText.setOnClickListener(getAllPlaylists);
        genresText=root.findViewById(R.id.tv_search_genres);
        genresText.setOnClickListener(getAllGenres);
        profilesText=root.findViewById(R.id.tv_search_profiles);
        profilesText.setOnClickListener(getAllProfiles);
        editText = (EditText) root.findViewById(R.id.search_edit_text);
        eraseText=(ImageView)root.findViewById(R.id.close_search_rectangle);
        eraseText.setOnClickListener(Erase);
        clearRecentSearches=(TextView)root.findViewById(R.id.tv_clear_recent_searches);
        clearRecentSearches.setOnClickListener(CLearRecentListener);
        searchText=root.findViewById(R.id.search_list_text);
        arrow_Img=root.findViewById(R.id.img_arrow_left);
        arrow_Img.setOnClickListener(Back);
        recentSearches=root.findViewById(R.id.recent_searches_layout);
        filter=root.findViewById(R.id.search_list_filter_layout);
        frameLayout=root.findViewById(R.id.frame_recycler_list_search);
        textView1=(TextView)root.findViewById(R.id.find_music_text);
        textView2=(TextView)root.findViewById(R.id.find_music_text2);
        imgItem=(ImageView)root.findViewById(R.id.img_best_search);
        editText.addTextChangedListener(filterTextWatcher);
        recentRecycler = root.findViewById(R.id.recycler_recent_searches);
        recentRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager LM = new LinearLayoutManager(getContext());
        recentRecycler.setLayoutManager(LM);
        recentRecycler.setHasFixedSize(false);
        adapter1=new SearchResultAdapter(GetResentData(),false,context);
        recentRecycler.setAdapter(adapter1);

        resultRecycler = root.findViewById(R.id.recycler_list_search);
        resultRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager LM2 = new LinearLayoutManager(getContext());
        resultRecycler.setLayoutManager(LM2);
        resultRecycler.setHasFixedSize(false);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        //listview.getViewTreeObserver().addOnGlobalLayoutListener(d);
        editText.setOnFocusChangeListener(FocusListener);
        return root;
    }

    private ArrayList<Container> GetResultData(CharSequence s) {
        ArrayList<Container> data=controller.getResultsOfSearch(getContext(),s.toString());
        if (data.size() == 0) {
            searchResultOff(s);
        } else {
            searchResultOn();
        }
        return data;
    }

    private ArrayList<Container> GetResentData() {
        ArrayList<Container> data=controller.getResentResult(getContext());

        if(data.size()==0){
            recentSearchesOff();
        } else {
            recentSearchesOn();
        }
        return data;
    }

    private void recentSearchesOn() {
        frameLayout.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        imgItem.setVisibility(View.GONE);
        recentSearches.setVisibility(View.VISIBLE);
    }

    public void recentSearchesOff() {

        filter.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        textView1.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        imgItem.setVisibility(View.VISIBLE);
        recentSearches.setVisibility(View.GONE);
        textView1.setText("Find the music you love");
        textView2.setText("Search for artists, songs, playlists, and more");
        imgItem.setImageResource(R.mipmap.big_search_foreground);
    }

    private void searchResultOn() {
        frameLayout.setVisibility(View.VISIBLE);
        filter.setVisibility(View.VISIBLE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        imgItem.setVisibility(View.GONE);
        recentSearches.setVisibility(View.GONE);
    }

    private void searchResultOff(CharSequence s) {
        frameLayout.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        textView1.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        imgItem.setVisibility(View.VISIBLE);
        recentSearches.setVisibility(View.GONE);
        textView1.setText("No result found for \"" + s + "\"");
        textView2.setText("Please check you have the right spelling,\n or try differant keywords.");
        imgItem.setImageResource(R.mipmap.flag_white_foreground);
    }

    @Override
    public void onItemEraseListener(int pos, int containerSize) {
        adapter1.notifyItemRemoved(pos);
        adapter1.notifyItemRangeChanged(pos, containerSize);

        if(containerSize==0)
            recentSearchesOff();
    }

    /**
     * create gradient background for track
     *
     * @return
     */
    private Drawable createBackground(Container container) {
        int color = getDominantColor(BitmapFactory.decodeResource(getContext().getResources()
                , container.getImg_Res()));

        SearchListFragment.SomeDrawable drawable = new SearchListFragment.SomeDrawable(color, Color.BLACK);
        return drawable;

    }

    /**
     * create gradient drawable for track image
     */
    public class SomeDrawable extends GradientDrawable {

        public SomeDrawable(int pStartColor, int pEndColor) {
            super(Orientation.BOTTOM_TOP, new int[]{pEndColor, pStartColor, pStartColor});
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
