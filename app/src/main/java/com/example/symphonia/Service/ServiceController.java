package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;

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

    public boolean checkEmailAvailability(Context context, String email, boolean mType) {
        return mSupplier.checkEmailAvailability(context, email, mType);
    }


    public ArrayList<Playlist> getRecentPlaylists(Context context, String mToken) {
        return mSupplier.getRecentPlaylists(context, mToken);
    }

    public ArrayList<Playlist> getRandomPlaylists(Context context, String mToken) {
        return mSupplier.getRandomPlaylists(context, mToken);
    }

    public ArrayList<Playlist> getMadeForYoutPlaylists(Context context, String mToken) {
        return mSupplier.getMadeForYouPlaylists(context, mToken);
    }

    public ArrayList<Playlist> getPopularPlaylists(Context context, String mToken) {
        return mSupplier.getPopularPlaylists(context, mToken);
    }


    public ArrayList<Container> getResentResult(Context context) {
        return mSupplier.getResentResult(context);
    }

    public ArrayList<Container> getResultsOfSearch(Context context, String searchWord) {
        return mSupplier.getResultsOfSearch(context, searchWord);
    }

    public ArrayList<Container> getCategories(Context context) {
        return mSupplier.getCategories(context);
    }

    public ArrayList<Container> getGenres(Context context) {
        return mSupplier.getGenres(context);
    }

    public ArrayList<Container> getArtists(Context context, String searchWord) {
        return mSupplier.getArtists(context, searchWord);
    }

    public ArrayList<Container> getSongs(Context context, String searchWord) {
        return mSupplier.getSongs(context, searchWord);
    }

    public ArrayList<Container> getAlbums(Context context, String searchWord) {
        return mSupplier.getAlbums(context, searchWord);

    }

    public ArrayList<Container> getGenresAndMoods(Context context, String searchWord) {
        return mSupplier.getGenresAndMoods(context, searchWord);

    }

    public ArrayList<Container> getPlaylists(Context context, String searchWord) {
        return mSupplier.getPlaylists(context, searchWord);

    }

    public ArrayList<Container> getProfiles(Context context, String searchWord) {
        return mSupplier.getProfiles(context, searchWord);

    }

    public void removeOneRecentSearch(Context context, int position) {
        mSupplier.removeOneRecentSearch(context, position);
    }

    public void removeAllRecentSearches(Context context) {
        mSupplier.removeAllRecentSearches(context);
    }

    public ArrayList<Artist> getFollowedArtists(Boolean type, String mToken, int limit) {
        return mSupplier.getFollowedArtists(type, mToken, limit);
    }

    public void followArtistOrUser(Boolean type, String mToken, String id) {
        mSupplier.followArtistOrUser(type, mToken, id);
    }

    public void unFollowArtistOrUser(Boolean type, String mToken, String id){
        mSupplier.unFollowArtistOrUser(type, mToken, id);
    }

    public Boolean isFollowing(Boolean type, String mToken, String id) {
        return mSupplier.isFollowing(type, mToken, id);
    }

    public ArrayList<Artist> getRecommendedArtists(Boolean type, String mToken, int limit) {
        return mSupplier.getRecommendedArtists(type, mToken, limit);
    }

    public ArrayList<Artist> searchArtist(Context context, String q){
        return mSupplier.searchArtist(context, q, 0, 20);
    }

    public ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit){
        return mSupplier.searchArtist(context, q, offset, limit);
    }

    public ArrayList<Artist> getArtistRelatedArtists(Context context, String id){
        return mSupplier.getArtistRelatedArtists(context, id);
    }

}
