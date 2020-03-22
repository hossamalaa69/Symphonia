package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;

import java.util.ArrayList;

/**
 * Interface that holds all functions to be used to fill
 * metadata of application, will be implemented in several ways
 *
 * @author Hossam Alaa
 * @since 23-3-2020
 * @version: 1.0
 */
public interface APIs {

    /**
     * holds logging user in, creation of user object and sets token
     * @param context holds context of activity that called this method
     * @param username email or username of user
     * @param password password of user
     * @param mType type of user, true for listener and false for artist
     * @return return true if data is matched
     */
    boolean logIn(Context context, String username, String password, boolean mType);

    /**
     * checks if email is already signed in database or not
     * @param context holds context of activity that called this method
     * @param email email of user
     * @param mType type of user, true for listener and false for artist
     * @return returns true if email is new, false if it's signed before
     */
    boolean checkEmailAvailability(Context context, String email, boolean mType);

    /**
     * handles that user is signing up, initializes new user object
     * fill database with new user
     * @param context holds context of activity that called this method
     * @param mType type of user, true for listener and false for artist
     * @param email email of user
     * @param password password of user
     * @param DOB date of birth of user
     * @param gender gender of user
     * @param name name of user
     * @return returns true if sign up is done
     */
    boolean signUp(Context context, boolean mType, String email, String password
                , String DOB, String gender, String name);


    ArrayList<Playlist> getPopularPlaylists(Context context, String mToken);

    ArrayList<Playlist> getMadeForYouPlaylists(Context context, String mToken);

    ArrayList<Playlist> getRecentPlaylists(Context context, String mToken);

    ArrayList<Playlist> getRandomPlaylists(Context context, String mToken);

    ArrayList<Artist> getFollowedArtists(Boolean type, String mToken, int limit);

    void followArtistOrUser(Boolean type, String mToken, String id);

    void unFollowArtistOrUser(Boolean type, String mToken, String id);

    Boolean isFollowing(Boolean type, String mToken, String id);

    ArrayList<Artist> getRecommendedArtists(Boolean type, String mToken, int limit);

    ArrayList<Container> getResentResult(Context context);

    ArrayList<Container> getResultsOfSearch(Context context, String searchWord);

    ArrayList<Container> getCategories(Context context);

    ArrayList<Container> getGenres(Context context);

    ArrayList<Container> getArtists(Context context, String searchWord);

    ArrayList<Container> getSongs(Context context, String searchWord);

    ArrayList<Container> getAlbums(Context context, String searchWord);

    ArrayList<Container> getGenresAndMoods(Context context, String searchWord);

    ArrayList<Container> getPlaylists(Context context, String searchWord);

    ArrayList<Container> getProfiles(Context context, String searchWord);

    void removeOneRecentSearch(Context context, int position);

    void removeAllRecentSearches(Context context);

    ArrayList<Container>getAllPopularPlaylists(Context context);

    ArrayList<Container>getFourPlaylists(Context context);

    Artist getArtist(Context context, String mToken, String id);

    ArrayList<Artist> getArtistRelatedArtists(Context context, String id);

    ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit);

    ArrayList<Album> getUserSavedAlbums(Context context, String mToken, int offset, int limit);

}
