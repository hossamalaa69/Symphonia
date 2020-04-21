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
import com.example.symphonia.Fragments_and_models.home.HomeFragment;
import com.example.symphonia.Fragments_and_models.playlist.PlaylistFragment;
import com.example.symphonia.Fragments_and_models.profile.BottomSheetDialogProfile;
import com.example.symphonia.Fragments_and_models.profile.FragmentProfile;
import com.example.symphonia.Fragments_and_models.profile.ProfileFollowersFragment;
import com.example.symphonia.Fragments_and_models.profile.ProfilePlaylistsFragment;
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


    boolean forgetPassword(final Context context,String email);

    boolean resetPassword(final Context context,final String password,final String token);


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
     * @param context  context of hosting activity
     * @param fragment token of user
     * @return recently-player  playlist
     */
    ArrayList<Playlist> getRecentPlaylists(Context context, HomeFragment fragment);


    /**
     * getter for random playlist
     *
     * @param context      context of hosting activity
     * @param homeFragment token of user
     * @return random  playlist
     */
    ArrayList<Playlist> getRandomPlaylists(Context context, HomeFragment homeFragment);

    /**
     * get the recent searches of the user
     * @param context context of the activity
     * @return ArrayList of Container of recent searches
     */
    ArrayList<Container> getResentResult(Context context);

    /**
     * get seven or less results of search
     * @param context context of the activity
     * @param searchWord  the word which user searched for
     * @return ArrayList of Container of Container
     */
    ArrayList<Container> getResultsOfSearch(Context context, String searchWord);

    /**
     * get a list of user categories
     * @param context context of the activity
     * @return ArrayList of Category of categories
     */
    ArrayList<Category> getCategories(Context context);

    /**
     * get a lsit of user genres
     * @param context context of the activity
     * @return ArrayList of Category of genres
     */
    ArrayList<Category> getGenres(Context context);

    /**
     * get a list of artists of the search results
     * @param context context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of artists
     */
    ArrayList<Container> getArtists(Context context, String searchWord);

    /**
     * get a list of songs of the search results
     * @param context context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of songs
     */
    ArrayList<Container> getSongs(Context context, String searchWord);

    /**
     * get a list of albums of the search results
     * @param context context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of albums
     */
    ArrayList<Container> getAlbums(Context context, String searchWord);

    /**
     * get a list of genres of the search results
     * @param context context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of genres
     */
    ArrayList<Container> getGenresAndMoods(Context context, String searchWord);

    /**
     * get a list of playlists of the search results
     * @param context context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of playlists
     */
    ArrayList<Container> getPlaylists(Context context, String searchWord);

    /**
     * get a list of profiles of the search results
     * @param context context of the activity
     * @param searchWord the word which user searched for
     * @return ArrayList of Container of profiles
     */
    ArrayList<Container> getProfiles(Context context, String searchWord);

    /**
     * ensure that the recent searches won't be returned again
     * @param context context of the activity
     * @param position position of the element which is deleted
     */
    void removeOneRecentSearch(Context context, int position);

    /**
     *ensure to return empty list when recent searches is required
     * @param context context of the activity
     */
    void removeAllRecentSearches(Context context);

    /**
     * return a list of popular playlists
     * @param context context of the activity
     * @return a ArrayList of Container of Popular playlists
     */
    ArrayList<Container> getAllPopularPlaylists(Context context);

    /**
     * return four popular playlists
     * @param context context of the activity
     * @return return four popular playlists
     */
    ArrayList<Container> getFourPlaylists(Context context);

    /**
     * Get information for a single artist identified by their unique ID
     *
     * @param context activity context
     * @param id      artist id
     * @return artist object
     */
    Artist getArtist(Context context, String id);

    /**
     * Get information about artists similar to a given artist.
     *
     * @param context activity context
     * @param id      artist id
     * @return Arraylist of similar artists
     */
    ArrayList<Artist> getArtistRelatedArtists(Context context, String id);

    /**
     * Search for a specific artist
     *
     * @param context Activity context
     * @param q       Query to search for
     * @param offset  The index of the first result to return
     * @param limit   Maximum number of results to return
     * @return List of search result artists
     */
    ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit);

    /**
     * Get a list of the albums saved in the current user’s ‘Your Music’ library
     *
     * @param listener
     * @param offset  The index of the first object to return
     * @param limit   The maximum number of objects to return
     * @return List of saved albums
     */
    ArrayList<Album> getUserSavedAlbums(RestApi.UpdateAlbumsLibrary listener, int offset, int limit);

    /**
     * Get the current user’s followed artists
     *
     * @param listener
     * @param type  true for user and false for artist
     * @param limit he maximum number of items to return
     * @param after the last artist ID retrieved from the previous request
     * @return list of followed artists
     */
    ArrayList<Artist> getFollowedArtists(RestApi.UpdateArtistsLibrary listener, String type, int limit, String after);

    ArrayList<Playlist> getCurrentUserPlaylists(RestApi.UpdatePlaylistsLibrary listener, int offset, int limit);

    /**
     * Add the current user as a followers of one or more artists or other users
     *
     * @param context activity context
     * @param type the type of what will be followed, can be artist or user
     * @param ids array of users or artists ids
     */
    void followArtistsOrUsers(Context context, String type, ArrayList<String> ids);

    /**
     * Remove the current user as a follower of one or more artists or other users
     *
     * @param context activity context
     * @param type the type of what will be unFollowed, can be artist or user
     * @param ids array of users or artists ids
     */
    void unFollowArtistsOrUsers(Context context, String type, ArrayList<String> ids);

    /**
     * Check to see if the current user is following an artist or more or other users
     *
     * @param context activity context
     * @param type the type of the checked objects, can be artist or user
     * @param ids array of users or artists ids
     * @return array of boolean
     */
    ArrayList<Boolean> isFollowing(Context context, String type, ArrayList<String> ids);

    /**
     * Get a list of recommended artist for the current user
     *
     * @param context activity context
     * @param type artist or user
     * @param offset the beginning of the items
     * @param limit the maximum number of items to return
     * @return list of recommended artists
     */
    ArrayList<Artist> getRecommendedArtists(Context context, String type, int offset, int limit);


    /**
     * Get information for a single album.
     *
     * @param context activity context
     * @param id album id
     * @return album object
     */
    Album getAlbum(Context context, String id);

    /**
     * Get information about an album’s tracks.
     * Optional parameters can be used to limit the number of tracks returned.
     *
     * @param context activity context
     * @param id album id
     * @param offset the beginning of the tracks list
     * @param limit the maximum number of tracks to get
     * @return array of album tracks
     */
    ArrayList<Track> getAlbumTracks(Context context, String id, int offset, int limit);

    /**
     * Save one or more albums to the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids array of albums ids
     */
    void saveAlbumsForUser(Context context, ArrayList<String> ids);

    /**
     * Remove one or more albums from the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids array of albums ids
     */
    void removeAlbumsForUser(Context context, ArrayList<String> ids);

    /**
     * Check if one or more albums is already saved in the current user’s ‘Your Music’ library.
     *
     * @param context activity context
     * @param ids array of albums ids
     * @return array of booleans, true for found and false for not found
     */
    ArrayList<Boolean> checkUserSavedAlbums(Context context, ArrayList<String> ids);

    /**
     * handles promoting user to premium
     *
     * @param context holds context of activity
     * @param root    holds root view of fragment
     * @param token   holds token of user
     * @return returns true if promoted
     */
    boolean promotePremium(final Context context, View root, String token);

    /**
     * get users followers
     * @param context context of the activity
     * @return arraylist of container of followers
     */
    ArrayList<Container>getProfileFollowers(Context context);
    /**
     * get users who current user follow them
     * @param context context of the activity
     * @return arraylist of container of users who follow the current user
     */
    ArrayList<Container>getProfileFollowing(Context context);

    /**
     * get current user profile
     * @param context context of the activity
     * @param settingsFragment the fragment which called this function
     * @return user profile
     */
    Profile getCurrentUserProfile(Context context, SettingsFragment settingsFragment);

    /**
     *get current user playlists
     * @param context context of the activity
     * @param fragmentProfile the fragment which called this function
     * @return ArrayList of Container of User's playlists
     */
    ArrayList<Container> getCurrentUserPlaylists(Context context, FragmentProfile fragmentProfile);

    /**
     * get users who the current user follow
     * @param context context of the activity
     * @param profileFollowersFragment the fragment which called this function
     * @return ArrayList of Container current user following
     */
    ArrayList<Container> getCurrentUserFollowing(Context context, ProfileFollowersFragment profileFollowersFragment);

    /**
     * get a list of current user followers
     * @param context context of the activity
     * @param profileFollowersFragment the fragment the function is called from
     * @return ArrayList of Container of Followers
     */
    ArrayList<Container> getCurrentUserFollowers(Context context, ProfileFollowersFragment profileFollowersFragment);

    /**
     * get number of user followers
     * @param context context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @return string of the number of followers
     */
    String getNumbersoUserFollowers(Context context, FragmentProfile fragmentProfile);

    /**
     * get number of users that user follow
     * @param context context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @return string of the number of following
     */
    String getNumbersoUserFollowing(Context context, FragmentProfile fragmentProfile);

    /**
     * get number of playlists of current user
     * @param context context of the activity
     * @param fragmentProfile the fragment the function is called from
     * @return string of the number of playlists
     */
    String getNumberofUserPlaylists(Context context,FragmentProfile fragmentProfile);
    /**
     * get current user playlists
     * @param context context of the activity
     * @param profilePlaylistsFragment the fragment the function is called from
     * @return current user playlists
     */
    ArrayList<Container> getAllCurrentUserPlaylists(Context context, ProfilePlaylistsFragment profilePlaylistsFragment);

    /**
     * follow playlist
     * @param context context of the activity
     * @param bottomSheetDialogProfile the fragment the function is called from
     */
    void followPlaylist(Context context, BottomSheetDialogProfile bottomSheetDialogProfile);

    /**
     * get number of artists in search result
     * @return int artists Count
     */
    int getArtistsCount();

    /**
     * get number of profiles in search result
     * @return int profiles Count
     */
    int getProfilessCount();
    /**
     * get number of playlists in search result
     * @return int playlists Count
     */
    int getPlaylistsCount();
    /**
     * get number of genres in search result
     * @return int genresCount
     */
    int getGenresCount();
    /**
     * get number of songs in search result
     * @return int songs Count
     */
    int getSongsCount();
    /**
     * get number of albums in search result
     * @return int albums Count
     */
    int getAlbumsCount();

    /**
     * this function gets tracks of a certain playlist
     *
     * @param context          context of current activity
     * @param id               id of playlist
     * @param playlistFragment instance of the fragment to make the update in
     * @return array list of tracks
     */
    ArrayList<Track> getTracksOfPlaylist(Context context, String id, PlaylistFragment playlistFragment);

    /**
     * this function initialize the request to stream music
     *
     * @param context      context of current activity
     * @param id           id of track
     * @param context_id   id of context
     * @param context_url  url of context
     * @param context_type type of context
     */
    void playTrack(Context context, String id, String context_id, String context_url, String context_type);
}
