package com.example.symphonia;

import com.example.symphonia.Entities.User;

import java.util.ArrayList;

/**
 * Class that holds all static variables to be used all application activities
 *
 * @author Hossam Alaa
 * @since 23-3-2020
 * @version: 1.0
 */

public class Constants {

    public static int TRACK_LOADER = 0;
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

}
