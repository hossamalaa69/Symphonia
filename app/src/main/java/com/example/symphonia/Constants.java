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
    public static final boolean DEBUG_STATUS = true;

    //Base URL
    public final static  String BASE_URL="http://ec2-52-21-160-186.compute-1.amazonaws.com/";

    public final static  String LOG_IN_URL  = BASE_URL+"api/v1/users/login?";

    public final static  String SIGN_UP_URL = BASE_URL+"api/v1/users/signUp?";

    public final static String EMAIL_EXISTS_URL = BASE_URL+"api/v1/users/email-exist?";

}
