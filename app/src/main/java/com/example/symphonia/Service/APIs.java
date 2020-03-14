package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Entities.Container;

import java.util.ArrayList;

public interface APIs {

    boolean logIn(Context context, String username, String password, boolean mType);


    ArrayList<Container> getResentResult(Context context);

    ArrayList<Container> getResultsOfSearch(Context context,String searchWord);

    ArrayList<Container>getCategories(Context context);

    ArrayList<Container>getGenres(Context context);

    ArrayList<Container>getArtists(Context context,String searchWord);

    ArrayList<Container>getSongs(Context context,String searchWord);

    ArrayList<Container>getAlbums(Context context,String searchWord);

    ArrayList<Container>getGenresAndMoods(Context context,String searchWord);

    ArrayList<Container>getPlaylists(Context context,String searchWord);

    ArrayList<Container>getProfiles(Context context,String searchWord);

    void removeOneRecentSearch(Context context,int position);

    void removeAllRecentSearches(Context context);

}
