package com.example.symphonia.Service;

import android.content.Context;
import android.view.View;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Category;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Profile;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Fragments_and_models.settings.SettingsFragment;

import java.util.ArrayList;

/**
 * Interface that holds all functions to be used to fill
 * metadata of application, will be implemented in several ways
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 23-3-2020
 */
public interface APIs {

    /**
     * holds logging user in, creation of user object and sets token
     *
     * @param context  holds context of activity that called this method
     * @param username email or username of user
     * @param password password of user
     * @param mType    type of user, true for listener and false for artist
     * @return return true if data is matched
     */
    boolean logIn(final Context context, String username, String password, boolean mType);

    /**
     * checks if email is already signed in database or not
     *
     * @param context holds context of activity that called this method
     * @param email   email of user
     * @param mType   type of user, true for listener and false for artist
     * @return returns true if email is new, false if it's signed before
     */
    boolean checkEmailAvailability(final Context context, String email, boolean mType);

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
    boolean signUp(final Context context, boolean mType, String email, String password
            , String DOB, String gender, String name);


    /**
     * getter for popular playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return popular  playlist
     */
    ArrayList<Playlist> getPopularPlaylists(Context context, String mToken);

    /**
     * getter for made-for-you playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return made-for-you  playlist
     */
    ArrayList<Playlist> getMadeForYouPlaylists(Context context, String mToken);


    /**
     * getter for recently-player playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return recently-player  playlist
     */
    ArrayList<Playlist> getRecentPlaylists(Context context, String mToken);


    /**
     * getter for random playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return random  playlist
     */
    ArrayList<Playlist> getRandomPlaylists(Context context, String mToken);

    ArrayList<Container> getResentResult(Context context);

    ArrayList<Container> getResultsOfSearch(Context context, String searchWord);

    ArrayList<Category> getCategories(Context context);

    ArrayList<Category> getGenres(Context context);

    ArrayList<Container> getArtists(Context context, String searchWord);

    ArrayList<Container> getSongs(Context context, String searchWord);

    ArrayList<Container> getAlbums(Context context, String searchWord);

    ArrayList<Container> getGenresAndMoods(Context context, String searchWord);

    ArrayList<Container> getPlaylists(Context context, String searchWord);

    ArrayList<Container> getProfiles(Context context, String searchWord);

    void removeOneRecentSearch(Context context, int position);

    void removeAllRecentSearches(Context context);

    ArrayList<Container> getAllPopularPlaylists(Context context);

    ArrayList<Container> getFourPlaylists(Context context);

    /**
     * Get information for a single artist identified by their unique ID
     *
     * @param context activity context
     * @param id artist id
     * @return artist object
     */
    Artist getArtist(Context context, String id);

    /**
     * Get information about artists similar to a given artist.
     *
     * @param context activity context
     * @param id artist id
     * @return Arraylist of similar artists
     */
    ArrayList<Artist> getArtistRelatedArtists(Context context, String id);

    /**
     * Search for a specific artist
     *
     * @param context Activity context
     * @param q Query to search for
     * @param offset The index of the first result to return
     * @param limit Maximum number of results to return
     * @return List of search result artists
     */
    ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit);

    /**
     * Get a list of the albums saved in the current user’s ‘Your Music’ library
     *
     * @param context Activity context
     * @param offset The index of the first object to return
     * @param limit The maximum number of objects to return
     * @return List of saved albums
     */
    ArrayList<Album> getUserSavedAlbums(Context context, int offset, int limit);


    ////////////////////////////////////// To Be Edited ////////////////////////////////////////////
    /**
     * Get the current user’s followed artists
     *
     * @param type true for user and false for artist
     * @param limit he maximum number of items to return
     * @return list of followed artists
     */
    ArrayList<Artist> getFollowedArtists(Context context, String type, int limit, String after);


    /**
     * Add the current user as a follower of one artist or other users
     *
     * @param type true for user and false for artist
     * @param ids user or artist id
     */
    void followArtistsOrUsers(Context context, String type, ArrayList<String> ids);

    /**
     * Remove the current user as a follower of one artist or other users
     *
     * @param type true for user and false for artist
     * @param ids user or artist id
     */
    void unFollowArtistsOrUsers(Context context, String type, ArrayList<String> ids);

    /**
     * Check to see if the current user is following an artist or other users
     *
     * @param type true for user and false for artist
     * @param ids user or artist id
     * @return true if following and false if not
     */
    ArrayList<Boolean> isFollowing(Context context, String type, ArrayList<String> ids);

    /**
     * Get a list of recommended artist for the current user
     *
     * @param context activity context
     * @param type artist or user
     * @param offset the beginning of the items
     * @param limit he maximum number of items to return
     * @return list of recommended artists
     */
    ArrayList<Artist> getRecommendedArtists(Context context, String type, int offset, int limit);

    /////////////////////////////////////////////////////////////////////////////////////////////////

    Album getAlbum(Context context, String id);

    ArrayList<Track> getAlbumTracks(Context context, String id, int offset, int limit);

    void saveAlbumsForUser(Context context, ArrayList<String> ids);

    void removeAlbumsForUser(Context context, ArrayList<String> ids);

    ArrayList<Boolean> checkUserSavedAlbums(Context context, ArrayList<String> ids);

    boolean promotePremium(final Context context, View root, String token);

    ArrayList<Container>getProfileFollowers(Context context);

    ArrayList<Container>getProfileFollowing(Context context);

    Profile getCurrentUserProfile(Context context, SettingsFragment settingsFragment);
}
