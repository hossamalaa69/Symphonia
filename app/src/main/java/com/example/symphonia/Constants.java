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
    public final static boolean DEBUG_STATUS = false;

    //Base URL
    public final static  String BASE_URL="https://zasymphonia.ddns.net/";

    public final static  String LOG_IN_URL  = BASE_URL+"api/v1/users/login?";

    public final static  String SIGN_UP_URL = BASE_URL+"api/v1/users/signup?";

    public final static String EMAIL_EXISTS_URL = BASE_URL+"api/v1/users/email-exist?";

    public static final String GET_TRACKS_HISTORY = BASE_URL+"api/v1/me/player/tracks/history";

    public static  final  String GET_POPULAR_PLAYLISTS = BASE_URL + "api/v1/browse/categories/party/playlists?";

    public static final String GET_ALL_CATEGORIES= BASE_URL+ "api/v1/browse/categories?";
    /**
     * made-for-you playlists link
     */
    public static  final  String GET_M_F_Y_PLAYLISTS = BASE_URL + "api/v1/browse/categories/party/playlists?";

    public static  final  String GET_RANDOM_PLAYLISTS = BASE_URL + "api/v1/playlists/rand?";

    public static final  String    GET_PLAYLISTS_TRACKS = BASE_URL +"api/v1/playlists/";
}
