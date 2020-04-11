package com.example.symphonia.Service;

import android.content.Context;
import android.view.View;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Category;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Fragments_and_models.profile.FragmentProfile;
import com.example.symphonia.Fragments_and_models.profile.ProfileFollowersFragment;

import java.util.ArrayList;

import static com.example.symphonia.Constants.DEBUG_STATUS;

/**
 * Class that Controls which service type will be used(REST APIs or MockService)
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 23-3-2020
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
        if (DEBUG_STATUS)
            mSupplier = new MockService();
        else
            mSupplier = new RestApi();
    }

    /**
     * getter for instance of class object
     *
     * @return return instance of class object
     */
    public static ServiceController getInstance() {
        return REST_CLIENT;
    }

    /**
     * holds logging user in, creation of user object and sets token
     *
     * @param context  holds context of activity that called this method
     * @param username email or username of user
     * @param password password of user
     * @param mType    type of user, true for listener and false for artist
     * @return return true if data is matched
     */
    public boolean logIn(final Context context, String username, String password, boolean mType) {
        return mSupplier.logIn(context, username, password, mType);
    }



    /**
     * checks if email is already signed in database or not
     *
     * @param context holds context of activity that called this method
     * @param email   email of user
     * @param mType   type of user, true for listener and false for artist
     * @return returns true if email is new, false if it's signed before
     */
    public boolean checkEmailAvailability(final Context context, String email, boolean mType) {
        return mSupplier.checkEmailAvailability(context, email, mType);
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
    public boolean signUp(final Context context, boolean mType, String email, String password,
                          String DOB, String gender, String name) {
        return mSupplier.signUp(context, mType, email, password, DOB, gender, name);
    }


    /**
     * getter for recently-player playlist
     *
     * @param context context of hosting activity
     * @param fragment  fragment of user
     * @return recently-player  playlist
     */
    public ArrayList<Playlist> getRecentPlaylists(Context context, HomeFragment fragment) {
        return mSupplier.getRecentPlaylists(context, fragment);
    }

    /**
     * getter for random playlist
     *
     * @param context context of hosting activity
     * @param homeFragment  token of user
     * @return random  playlist
     */
    public ArrayList<Playlist> getRandomPlaylists(Context context, HomeFragment homeFragment) {
        return mSupplier.getRandomPlaylists(context,homeFragment );
    }

    /**
     * getter for made-for-you playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return made-for-you  playlist
     */
    public ArrayList<Playlist> getMadeForYoutPlaylists(Context context, String mToken) {
        return mSupplier.getMadeForYouPlaylists(context, mToken);
    }

    /**
     * getter for popular playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return popular  playlist
     */
    public ArrayList<Playlist> getPopularPlaylists(Context context, String mToken) {
        return mSupplier.getPopularPlaylists(context, mToken);
    }


    public ArrayList<Container> getResentResult(Context context) {
        return mSupplier.getResentResult(context);
    }

    public ArrayList<Container> getResultsOfSearch(Context context, String searchWord) {
        return mSupplier.getResultsOfSearch(context, searchWord);
    }

    public ArrayList<Category> getCategories(Context context) {
        return mSupplier.getCategories(context);
    }

    public ArrayList<Category> getGenres(Context context) {
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

    public ArrayList<Artist> getFollowedArtists(Context context, String type, int limit, String after) {
        return mSupplier.getFollowedArtists(context, type, limit, after);
    }

    public void followArtistsOrUsers(Context context, String type, ArrayList<String> ids) {
        mSupplier.followArtistsOrUsers(context, type, ids);
    }

    public void unFollowArtistsOrUsers(Context context, String type, ArrayList<String> ids) {
        mSupplier.unFollowArtistsOrUsers(context, type, ids);
    }

    public ArrayList<Boolean> isFollowing(Context context, String type, ArrayList<String> ids) {
        return mSupplier.isFollowing(context, type, ids);
    }

    /**
     * Get a list of recommended artist for the current user
     *
     * @param type true for user and false for artist
     * @param limit he maximum number of items to return
     * @return list of recommended artists
     */
    public ArrayList<Artist> getRecommendedArtists(Context context, String type, int offset, int limit) {
        return mSupplier.getRecommendedArtists(context, type, offset, limit);
    }

    public ArrayList<Artist> searchArtist(Context context, String q) {
        return mSupplier.searchArtist(context, q, 0, 20);
    }

    public ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit) {
        return mSupplier.searchArtist(context, q, offset, limit);
    }

    public ArrayList<Artist> getArtistRelatedArtists(Context context, String id) {
        return mSupplier.getArtistRelatedArtists(context, id);
    }

    public ArrayList<Album> getUserSavedAlbums(Context context, int offset, int limit) {
        return mSupplier.getUserSavedAlbums(context, offset, limit);
    }

    public ArrayList<Container> getAllPopularPlaylists(Context context) {
        return mSupplier.getAllPopularPlaylists(context);
    }

    public ArrayList<Container> getFourPlaylists(Context context) {
        return mSupplier.getFourPlaylists(context);
    }

    /**
     * Get information for a single artist identified by their unique ID
     *
     * @param context activity context
     * @param id artist id
     * @return artist object
     */
    public Artist getArtist(Context context, String id){
        return mSupplier.getArtist(context, id);
    }

    public Album getAlbum(Context context, String id) {
        return mSupplier.getAlbum(context, id);
    }

    public ArrayList<Track> getAlbumTracks(Context context, String id, int offset, int limit) {
        return mSupplier.getAlbumTracks(context, id, offset, limit);
    }

    public void saveAlbumsForUser(Context context, ArrayList<String> ids) {
        mSupplier.saveAlbumsForUser(context, ids);
    }

    public void removeAlbumsForUser(Context context, ArrayList<String> ids) {
        mSupplier.removeAlbumsForUser(context, ids);
    }

    public ArrayList<Boolean> checkUserSavedAlbums(Context context, ArrayList<String> ids) {
        return mSupplier.checkUserSavedAlbums(context, ids);
    }

    /**
     * handles promoting user to premium
     * @param context holds context of activity
     * @param root holds root view of fragment
     * @param token holds token of user
     * @return returns true if promoted
     */
    public boolean promotePremium(final Context context, View root, String token){
        return mSupplier.promotePremium(context, root, token);
    }

    public ArrayList<Container>getProfileFollowers(Context context){
        return mSupplier.getProfileFollowers(context);
    }

    public ArrayList<Container>getProfileFollowing(Context context){
        return mSupplier.getProfileFollowing(context);
    }

    public ArrayList<Container>getCurrentUserPlaylists(Context context, FragmentProfile fragmentProfile){
        return mSupplier.getCurrentUserPlaylists(context,fragmentProfile);
    }

    public String getNumbersoUserFollowers(Context context, FragmentProfile fragmentProfile){
        return mSupplier.getNumbersoUserFollowers(context,fragmentProfile);
    }

    public String getNumbersoUserFollowing(Context context, FragmentProfile fragmentProfile){
        return mSupplier.getNumbersoUserFollowing(context,fragmentProfile);
    }

    public ArrayList<Container>getCurrentUserFollowers(Context context, ProfileFollowersFragment profileFollowersFragment){
        return mSupplier.getCurrentUserFollowers(context,profileFollowersFragment);
    }

    public int getProfilesCount(){
        return mSupplier.getProfilessCount();
    }

    public int getPlaylistsCount(){
        return mSupplier.getPlaylistsCount();
    }

    public int getArtistsCount(){
        return mSupplier.getArtistsCount();
    }

    public int getAlbumsCount(){
        return mSupplier.getAlbumsCount();
    }

    public int getGenresCount(){
        return mSupplier.getGenresCount();
    }

    public int getSongsCount(){
        return mSupplier.getSongsCount();
    }

    public void playTrack(Context context, String id,String context_id,String context_url,String context_type)
    {
        mSupplier.playTrack(context,id,context_id,context_url,context_type);
    }
    public  ArrayList<Track>  getTracksOfPlaylist(Context context, String id, PlaylistFragment playlistFragment) {
         return  mSupplier.getTracksOfPlaylist(context,id,playlistFragment);
    }
}
