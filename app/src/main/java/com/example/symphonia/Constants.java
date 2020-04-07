package com.example.symphonia;

import com.example.symphonia.Entities.User;

/**
 * Class that holds all static variables to be used all application activities
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 23-3-2020
 */

public class Constants {

    /**
     * Token that would be used for current user
     */
    public static String currentToken = "";
    /**
     * object of the current user
     */
    public static User currentUser = null;
    /**
     * debug status for app to decide which service mode will be used
     * true for MockService, false for REST APIs mode
     */
    public final static boolean DEBUG_STATUS = true;

    //Base URL
    public final static  String BASE_URL="https://zasymphonia.ddns.net/";

    public final static  String LOG_IN_URL  = BASE_URL+"api/v1/users/login?";

    public final static  String SIGN_UP_URL = BASE_URL+"api/v1/users/signup?";

    public final static String EMAIL_EXISTS_URL = BASE_URL+"api/v1/users/email-exist?";

    public static final String GET_RECENT_PLAYLISTS = BASE_URL+"api/v1/me/recently-played";

    public static  final  String GET_POPULAR_PLAYLISTS = BASE_URL + "api/v1/browse/categories/party/playlists?";

    public static final String GET_ALL_CATEGORIES= BASE_URL+ "api/v1/browse/categories?";

    public static final String Get_Current_User_Profile=BASE_URL+"api/v1/me";

    public static final String Get_Current_User_Profile_playlists=BASE_URL+"api/v1/me/playlists";

    public static final String Get_playlist=BASE_URL+"api/v1/playlists";

    public static final String Get_User_Following=BASE_URL+"api/v1/me/following";

    public static final String Get_User_Followers=BASE_URL+"api/v1/Artists/5e7f76abdb66b448b4356324/followers";

    /**
     * made-for-you playlists link
     */
    public static  final  String GET_M_F_Y_PLAYLISTS = BASE_URL + "api/v1/browse/categories/party/playlists?";

    public static  final  String GET_RANDOM_PLAYLISTS = BASE_URL + "api/v1/playlists/rand?";

    public static final String GET_RECOMMENDED_ARTISTS = BASE_URL + "api/v1/browse/artists";

    public static final String FOLLOW_ARTIST_URL = BASE_URL + "api/v1/me/following";

    public static final  String    GET_PLAYLISTS_TRACKS = BASE_URL +"api/v1/playlists/";
}
