package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;

import java.util.ArrayList;

import static com.example.symphonia.Constants.DEBUG_STATUS;

/**
 * Class that Controls which service type will be used(REST APIs or MockService)
 *
 * @author Hossam Alaa
 * @since 23-3-2020
 * @version 1.0
 */

public class ServiceController {

    /**
     * object of parent(abstract class) for supplying app with data
     */
    private final APIs mSupplier;
    /**
     * new object of controller class to decide which service to be used
     */
    private static final ServiceController REST_CLIENT = new ServiceController();

    /**
     * constructor of ServiceController that decides which type service to be used
     */
    private ServiceController() {
        //checks if mode is debugging, then use mockService
        if (DEBUG_STATUS) mSupplier = new MockService();

    }

    /**
     * getter for instance of class object
     * @return return instance of class object
     */
    public static ServiceController getInstance() {
        return REST_CLIENT;
    }

    /**
     * holds logging user in, creation of user object and sets token
     * @param context holds context of activity that called this method
     * @param username email or username of user
     * @param password password of user
     * @param mType type of user, true for listener and false for artist
     * @return return true if data is matched
     */
    public boolean logIn(Context context, String username, String password, boolean mType) {
        return mSupplier.logIn(context, username, password, mType);
    }

    /**
     * checks if email is already signed in database or not
     * @param context holds context of activity that called this method
     * @param email email of user
     * @param mType type of user, true for listener and false for artist
     * @return returns true if email is new, false if it's signed before
     */
    public boolean checkEmailAvailability(Context context, String email, boolean mType) {
        return mSupplier.checkEmailAvailability(context, email, mType);
    }

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
    public boolean signUp(Context context, boolean mType, String email, String password,
                       String DOB, String gender, String name) {
        return mSupplier.signUp(context, mType, email, password, DOB, gender, name);
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

    public ArrayList<Album> getUserSavedAlbums(Context context, String mToken, int offset, int limit){
        return mSupplier.getUserSavedAlbums(context, mToken, offset, limit);
    }

    public ArrayList<Container>getAllPopularPlaylists(Context context){
        return mSupplier.getAllPopularPlaylists(context);
    }

    public ArrayList<Container>getFourPlaylists(Context context){
        return mSupplier.getFourPlaylists(context);
    }

    public Artist getArtist(Context context, String mToken, String id){
        return mSupplier.getArtist(context, mToken, id);
    }

}
