package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Playlist;

import java.util.ArrayList;

public interface APIs {

    ArrayList<Playlist> getRecentPlaylists(Context context);

    boolean logIn(Context context, String username, String password, boolean mType);

    ArrayList<Artist> getFollowedArtists(Boolean type, String mToken, int limit);

    void followArtistOrUser(Boolean type, String mToken, String id);

    Boolean isFollowing(Boolean type, String mToken, String id);

    ArrayList<Artist> getRecommendedArtists(Boolean type, String mToken, int limit);

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

    ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit);
}
