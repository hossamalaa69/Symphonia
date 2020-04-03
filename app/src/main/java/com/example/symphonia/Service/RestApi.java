package com.example.symphonia.Service;

import android.content.Context;
import android.view.View;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;

import java.util.ArrayList;

public class RestApi implements APIs {
    /**
     * holds logging user in, creation of user object and sets token
     *
     * @param context  holds context of activity that called this method
     * @param username email or username of user
     * @param password password of user
     * @param mType    type of user, true for listener and false for artist
     * @return return true if data is matched
     */
    @Override
    public boolean logIn(Context context, String username, String password, boolean mType) {
        return false;
    }

    /**
     * checks if email is already signed in database or not
     *
     * @param context holds context of activity that called this method
     * @param email   email of user
     * @param mType   type of user, true for listener and false for artist
     * @return returns true if email is new, false if it's signed before
     */
    @Override
    public boolean checkEmailAvailability(Context context, String email, boolean mType) {
        return false;
    }

    /**
     * handles that user is signing up, initializes new user object
     * fill database with new user
     *
     * @param context  holds context of activity that called this method
     * @param mType    type of user, true for listener and false for artist
     * @param email    email of user
     * @param password password of user
     * @param DOB      date of birth of user
     * @param gender   gender of user
     * @param name     name of user
     * @return returns true if sign up is done
     */
    @Override
    public boolean signUp(Context context, boolean mType, String email, String password, String DOB, String gender, String name) {
        return false;
    }

    /**
     * getter for popular playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return popular  playlist
     */
    @Override
    public ArrayList<Playlist> getPopularPlaylists(Context context, String mToken) {
        return null;
    }

    /**
     * getter for made-for-you playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return made-for-you  playlist
     */
    @Override
    public ArrayList<Playlist> getMadeForYouPlaylists(Context context, String mToken) {
        return null;
    }

    /**
     * getter for recently-player playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return recently-player  playlist
     */
    @Override
    public ArrayList<Playlist> getRecentPlaylists(Context context, String mToken) {
        return null;
    }

    /**
     * getter for random playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return random  playlist
     */
    @Override
    public ArrayList<Playlist> getRandomPlaylists(Context context, String mToken) {
        return null;
    }

    @Override
    public ArrayList<Container> getResentResult(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getResultsOfSearch(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getCategories(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getGenres(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getArtists(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getSongs(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getAlbums(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getGenresAndMoods(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getPlaylists(Context context, String searchWord) {
        return null;
    }

    @Override
    public ArrayList<Container> getProfiles(Context context, String searchWord) {
        return null;
    }

    @Override
    public void removeOneRecentSearch(Context context, int position) {

    }

    @Override
    public void removeAllRecentSearches(Context context) {

    }

    @Override
    public ArrayList<Container> getAllPopularPlaylists(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getFourPlaylists(Context context) {
        return null;
    }

    /**
     * Get information for a single artist identified by their unique ID
     *
     * @param context activity context
     * @param mToken  user's access token
     * @param id      artist id
     * @return artist object
     */
    @Override
    public Artist getArtist(Context context, String mToken, String id) {
        return null;
    }

    /**
     * Get information about artists similar to a given artist.
     *
     * @param context activity context
     * @param id      artist id
     * @return Arraylist of similar artists
     */
    @Override
    public ArrayList<Artist> getArtistRelatedArtists(Context context, String id) {
        return null;
    }

    /**
     * Search for a specific artist
     *
     * @param context Activity context
     * @param q       Query to search for
     * @param offset  The index of the first result to return
     * @param limit   Maximum number of results to return
     * @return List of search result artists
     */
    @Override
    public ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit) {
        return null;
    }

    /**
     * Get a list of the albums saved in the current user’s ‘Your Music’ library
     *
     * @param context Activity context
     * @param mToken  User's access token
     * @param offset  The index of the first object to return
     * @param limit   The maximum number of objects to return
     * @return List of saved albums
     */
    @Override
    public ArrayList<Album> getUserSavedAlbums(Context context, String mToken, int offset, int limit) {
        return null;
    }

    /**
     * Get the current user’s followed artists
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param limit  he maximum number of items to return
     * @return list of followed artists
     */
    @Override
    public ArrayList<Artist> getFollowedArtists(Boolean type, String mToken, int limit) {
        return null;
    }

    /**
     * Add the current user as a follower of one artist or other users
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param id     user or artist id
     */
    @Override
    public void followArtistOrUser(Boolean type, String mToken, String id) {

    }

    /**
     * Remove the current user as a follower of one artist or other users
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param id     user or artist id
     */
    @Override
    public void unFollowArtistOrUser(Boolean type, String mToken, String id) {

    }

    /**
     * Check to see if the current user is following an artist or other users
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param id     user or artist id
     * @return true if following and false if not
     */
    @Override
    public Boolean isFollowing(Boolean type, String mToken, String id) {
        return null;
    }

    /**
     * Get a list of recommended artist for the current user
     *
     * @param type   true for user and false for artist
     * @param mToken user's access token
     * @param limit  he maximum number of items to return
     * @return list of recommended artists
     */
    @Override
    public ArrayList<Artist> getRecommendedArtists(Boolean type, String mToken, int limit) {
        return null;
    }

    @Override
    public boolean promotePremium(Context context, View root, String token) {
        return false;
    }

    @Override
    public ArrayList<Container> getProfileFollowers(Context context) {
        return null;
    }

    @Override
    public ArrayList<Container> getProfileFollowing(Context context) {
        return null;
    }
}
