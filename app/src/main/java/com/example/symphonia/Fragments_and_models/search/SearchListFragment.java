package com.example.symphonia.Fragments_and_models.search;

import android.app.Activity;
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

import com.example.symphonia.R;
import com.example.symphonia.Utils.Container;
import com.example.symphonia.adapters.SearchResultAdapter;

import java.util.ArrayList;


public class SearchListFragment extends Fragment {

    private SearchResultAdapter Adapter1;
    private SearchResultAdapter Adapter2;
    private View Filter;
    private View RecentRecearches;
    private FrameLayout frameLayout;
    private TextView tv1;
    private TextView tv2;
    private ImageView ImgItem;
    private TextView SearchText;
    private ImageView Arrow_Img;
    private ImageView EraseText;
    private RecyclerView RV;
    private RecyclerView RV2;
    private TextView ClearRecentSearches;
    private EditText editText;

    private TextView artistsText;
    private TextView songsText;
    private TextView playlistsText;
    private TextView albumsText;
    private TextView genresText;
    private TextView profilesText;

    private View.OnClickListener CLearRecentListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Adapter1=new SearchResultAdapter(new ArrayList<Container>(),false);
            RV.setAdapter(Adapter1);
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
                    .replace(R.id.list_search_view, new SearchForAllFragment("Songs",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllArtists=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.list_search_view, new SearchForAllFragment("Artists",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllAlbums=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.list_search_view, new SearchForAllFragment("Albums",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllPlaylists=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.list_search_view, new SearchForAllFragment("Playlists",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllGenres=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.list_search_view, new SearchForAllFragment("Genres & Moods",editText.getText().toString()))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener getAllProfiles=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.list_search_view, new SearchForAllFragment("Profiles",editText.getText().toString()))
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
                SearchText.setVisibility(View.INVISIBLE);
                Arrow_Img.setVisibility(View.VISIBLE);
                EraseText.setVisibility(View.VISIBLE);
            }
            else {
                SearchText.setVisibility(View.INVISIBLE);
                Arrow_Img.setVisibility(View.VISIBLE);
                EraseText.setVisibility(View.GONE);
            }
        }
    };

    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(count==0){
                EraseText.setVisibility(View.GONE);
                Adapter1=new SearchResultAdapter(GetResentData(),false);
                RV.setAdapter(Adapter1);
            }
            else{
                EraseText.setVisibility(View.VISIBLE);
                Adapter1=new SearchResultAdapter(GetResultData(s),true);
                RV2.setAdapter(Adapter1);
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
     /*   KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if(isOpen){
                            SearchText.setVisibility(View.INVISIBLE);
                            Arrow_Img.setVisibility(View.VISIBLE);
                        }
                        else {
                            SearchText.setVisibility(View.VISIBLE);
                            Arrow_Img.setVisibility(View.INVISIBLE);
                        }
                    }
                });*/
        View root = inflater.inflate(R.layout.search_list, container, false);

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
        EraseText=(ImageView)root.findViewById(R.id.close_search_rectangle);
        EraseText.setOnClickListener(Erase);
        ClearRecentSearches=(TextView)root.findViewById(R.id.tv_clear_recent_searches);
        ClearRecentSearches.setOnClickListener(CLearRecentListener);
        SearchText=root.findViewById(R.id.search_list_text);
        Arrow_Img=root.findViewById(R.id.img_arrow_left);
        Arrow_Img.setOnClickListener(Back);
        RecentRecearches=root.findViewById(R.id.recent_searches_layout);
        Filter=root.findViewById(R.id.search_list_filter_layout);
        frameLayout=root.findViewById(R.id.frame_recycler_list_search);
        tv1=(TextView)root.findViewById(R.id.find_music_text);
        tv2=(TextView)root.findViewById(R.id.find_music_text2);
        ImgItem=(ImageView)root.findViewById(R.id.img_best_search);
        editText.addTextChangedListener(filterTextWatcher);
        RV = root.findViewById(R.id.recycler_recent_searches);
        RV.setNestedScrollingEnabled(false);
        LinearLayoutManager LM = new LinearLayoutManager(getContext());
        RV.setLayoutManager(LM);
        RV.setHasFixedSize(false);
        Adapter1=new SearchResultAdapter(GetResentData(),false);
        RV.setAdapter(Adapter1);

        RV2 = root.findViewById(R.id.recycler_list_search);
        RV2.setNestedScrollingEnabled(false);
        LinearLayoutManager LM2 = new LinearLayoutManager(getContext());
        RV2.setLayoutManager(LM2);
        RV2.setHasFixedSize(false);

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
        ArrayList<Container> data=new ArrayList<Container>();
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        if (data.size() == 0) {
            SearchResultOff(s);
        } else {
            SearchResultOn();
        }
        return data;
    }

    private ArrayList<Container> GetResentData() {
        ArrayList<Container> data=new ArrayList<Container>();
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        data.add(new Container("song name(somthing)","song.sadf",R.drawable.download1));
        if(data.size()==0){
            RecentSearchesOff();
        } else {
            RecentSearchesOn();
        }
        return data;
    }

    private void RecentSearchesOn() {
        frameLayout.setVisibility(View.GONE);
        Filter.setVisibility(View.GONE);
        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        ImgItem.setVisibility(View.GONE);
        RecentRecearches.setVisibility(View.VISIBLE);
    }

    private void RecentSearchesOff() {
        frameLayout.setVisibility(View.GONE);
        Filter.setVisibility(View.GONE);
        tv1.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
        ImgItem.setVisibility(View.VISIBLE);
        RecentRecearches.setVisibility(View.GONE);
        tv1.setText("Find the music you love");
        tv2.setText("Search for artists, songs, playlists, and more");
        ImgItem.setImageResource(R.mipmap.big_search_foreground);
    }

    private void SearchResultOn() {
        frameLayout.setVisibility(View.VISIBLE);
        Filter.setVisibility(View.VISIBLE);
        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        ImgItem.setVisibility(View.GONE);
        RecentRecearches.setVisibility(View.GONE);
    }

    private void SearchResultOff(CharSequence s) {
        frameLayout.setVisibility(View.GONE);
        Filter.setVisibility(View.GONE);
        tv1.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
        ImgItem.setVisibility(View.VISIBLE);
        RecentRecearches.setVisibility(View.GONE);
        tv1.setText("No result found for \"" + s + "\"");
        tv2.setText("Please check you have the right spelling, or try differant keywords.");
        ImgItem.setImageResource(R.mipmap.flag_white_foreground);
    }

}
