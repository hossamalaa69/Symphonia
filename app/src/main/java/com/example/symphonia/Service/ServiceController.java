package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Entities.Container;

import java.util.ArrayList;

import static com.example.symphonia.Constants.debug;

public class ServiceController {

    private final APIs mSupplier;

    private static final ServiceController restClient = new ServiceController();


    private ServiceController() {
        if (debug) {
            mSupplier = new MockService();
        }
    }

    public static ServiceController getInstance() {
        return restClient;
    }

    public boolean logIn(Context context, String username, String password, boolean mType) {
        return mSupplier.logIn(context, username, password, mType);
    }

    public ArrayList<Container> getResentResult(Context context){
        return mSupplier.getResentResult(context);
    }

    public ArrayList<Container> getResultsOfSearch(Context context,String searchWord){
        return mSupplier.getResultsOfSearch(context,searchWord);
    }

    public ArrayList<Container>getCategories(Context context){
        return mSupplier.getCategories(context);
    }

    public ArrayList<Container>getGenres(Context context){
        return mSupplier.getGenres(context);
    }

    public ArrayList<Container>getArtists(Context context,String searchWord){
        return mSupplier.getArtists(context,searchWord);
    }

    public ArrayList<Container>getSongs(Context context,String searchWord){
        return mSupplier.getSongs(context,searchWord);
    }

    public ArrayList<Container>getAlbums(Context context,String searchWord){
        return mSupplier.getAlbums(context,searchWord);

    }

    public ArrayList<Container>getGenresAndMoods(Context context,String searchWord){
        return mSupplier.getGenresAndMoods(context,searchWord);

    }

    public ArrayList<Container>getPlaylists(Context context,String searchWord){
        return mSupplier.getPlaylists(context,searchWord);

    }

    public ArrayList<Container>getProfiles(Context context,String searchWord){
        return mSupplier.getProfiles(context,searchWord);

    }

    public void removeOneRecentSearch(Context context,int position){
        mSupplier.removeOneRecentSearch(context,position);
    }

    public void removeAllRecentSearches(Context context){
        mSupplier.removeAllRecentSearches(context);
    }

}
