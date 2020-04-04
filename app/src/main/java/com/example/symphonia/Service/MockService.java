package com.example.symphonia.Service;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import com.example.symphonia.Activities.User_Management.LoginActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Copyright;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Entities.User;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Class that holds all functions to be used to fill metadata of application
 * using dependency injection, which implements APIs interface
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 23-3-2020
 */

public class MockService implements APIs {

    private Boolean firstTimeToGetRecentSearches;
    private ArrayList<Playlist> mRandomPlaylists;
    private ArrayList<Playlist> mRecentPlaylists;
    private ArrayList<Artist> mArtists;
    private ArrayList<Album> mAlbums;
    private ArrayList<Container> mData;
    private ArrayList<Container> mRecentSearches;
    private ArrayList<Playlist> mPopularPlaylists;

    /**
     * Array that holds some users of type listener to be mimic
     */
    private ArrayList<User> mListenerArrayList;
    /**
     * Array that holds some users of type artist to be mimic
     */
    private ArrayList<User> mArtistArrayList;

    /**
     * constructor of mockService class that initializes all mimic data
     */
    public MockService() {
        firstTimeToGetRecentSearches = true;
        mRecentSearches = new ArrayList<>();
        mListenerArrayList = new ArrayList<>();
        mArtistArrayList = new ArrayList<>();

        //add 3 users of type listeners
        mListenerArrayList.add(new User("user1@symphonia.com", "12345678", true, false));
        mListenerArrayList.add(new User("user2@symphonia.com", "12345678", true, true));
        mListenerArrayList.add(new User("user3@symphonia.com", "12345678", true, true));

        //add 3 users of type artist
        mArtistArrayList.add(new User("artist1@symphonia.com", "12345678", false, false));
        mArtistArrayList.add(new User("artist2@symphonia.com", "12345678", false, true));
        mArtistArrayList.add(new User("artist3@symphonia.com", "12345678", false, true));

        mArtists = new ArrayList<>();
        mArtists.add(new Artist("1", Utils.convertToBitmap(R.drawable.ragheb), "Ragheb Alama"));
        mArtists.add(new Artist("2", Utils.convertToBitmap(R.drawable.elissa), "Elissa"));
        mArtists.add(new Artist("3", Utils.convertToBitmap(R.drawable.angham), "Angham"));
        mArtists.add(new Artist("4", Utils.convertToBitmap(R.drawable.wael), "Wael Kfoury"));
        mArtists.add(new Artist("5", Utils.convertToBitmap(R.drawable.wael_gassar), "Wael Jassar"));
        mArtists.add(new Artist("6", Utils.convertToBitmap(R.drawable.shawn), "Shawn Mendes"));
        mArtists.add(new Artist("7", Utils.convertToBitmap(R.drawable.bts), "BTS"));
        mArtists.add(new Artist("8", Utils.convertToBitmap(R.drawable.billie), "Billie Eilish"));
        mArtists.add(new Artist("9", Utils.convertToBitmap(R.drawable.alan), "Alan Walker"));
        mArtists.add(new Artist("10", Utils.convertToBitmap(R.drawable.saaad), "Saad Lamjarred"));
        mArtists.add(new Artist("11", Utils.convertToBitmap(R.drawable.ed), "Ed Sheeran"));
        mArtists.add(new Artist("12", Utils.convertToBitmap(R.drawable.halsey), "Halsey"));
        mArtists.add(new Artist("13", Utils.convertToBitmap(R.drawable.imagine), "Imagine Dragons"));
        mArtists.add(new Artist("14", Utils.convertToBitmap(R.drawable.ariana), "Ariana Grande"));
        mArtists.add(new Artist("15", Utils.convertToBitmap(R.drawable.aseel), "Aseel Hameem"));
        mArtists.add(new Artist("16", Utils.convertToBitmap(R.drawable.maroon), "Maroon 5"));
        mArtists.add(new Artist("17", Utils.convertToBitmap(R.drawable.ali), "Ali Gatie"));
        mArtists.add(new Artist("18", Utils.convertToBitmap(R.drawable.zayn), "ZAYN"));
        mArtists.add(new Artist("19", Utils.convertToBitmap(R.drawable.cairokee), "Cairokee"));
        mArtists.add(new Artist("20", Utils.convertToBitmap(R.drawable.rauf_fayk), "Rauf & Faik"));
        mArtists.add(new Artist("21", Utils.convertToBitmap(R.drawable.adham), "Adham Nabulsi"));
        mArtists.add(new Artist("22", Utils.convertToBitmap(R.drawable.abu), "Abu"));
        mArtists.add(new Artist("23", Utils.convertToBitmap(R.drawable.eminem), "Eminem"));
        mArtists.add(new Artist("24", Utils.convertToBitmap(R.drawable.hussien), "Hussain Al Jassmi"));
        mArtists.add(new Artist("25", Utils.convertToBitmap(R.drawable.nancy), "Nancy Ajram"));
        mArtists.add(new Artist("26", Utils.convertToBitmap(R.drawable.havana), "Camila Cabello"));
        mArtists.add(new Artist("27", Utils.convertToBitmap(R.drawable.selena), "Selena Gomez"));
        mArtists.add(new Artist("28", Utils.convertToBitmap(R.drawable.taylor), "Taylor Swift"));
        mArtists.add(new Artist("29", Utils.convertToBitmap(R.drawable.tamer_ashour), "Tamer Ashour"));
        mArtists.add(new Artist("30", Utils.convertToBitmap(R.drawable.assala), "Assala Nasri"));
        mArtists.add(new Artist("31", Utils.convertToBitmap(R.drawable.maher), "Maher Zain"));
        mArtists.add(new Artist("32", Utils.convertToBitmap(R.drawable.adele), "Adele"));
        mArtists.add(new Artist("33", Utils.convertToBitmap(R.drawable.hamza), "Hamza Namira"));
        mArtists.add(new Artist("34", Utils.convertToBitmap(R.drawable.justin), "Justin Bieber"));
        mArtists.add(new Artist("35", Utils.convertToBitmap(R.drawable.jannat), "Jannat"));
        mArtists.add(new Artist("36", Utils.convertToBitmap(R.drawable.samira), "Samira Said"));
        mArtists.add(new Artist("37", Utils.convertToBitmap(R.drawable.maryam), "Myriam Fares"));
        mArtists.add(new Artist("38", Utils.convertToBitmap(R.drawable.amr), "Amr Diab"));
        mArtists.add(new Artist("39", Utils.convertToBitmap(R.drawable.bahaa), "Bahaa Sultan"));
        mArtists.add(new Artist("40", Utils.convertToBitmap(R.drawable.loai), "Loai"));


        mAlbums = new ArrayList<>();

        mAlbums.add(new Album("6qqNVTkY8uBg9cP3Jd7DAH",
                "single",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(7))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 Darkroom/Interscope Records", "C"),
                        new Copyright("2020 Darkroom/Interscope Records", "P"))),
                Utils.convertToBitmap(R.drawable.no_time_to_die),
                "No Time To Die",
                "2020-02-13",
                new ArrayList<Track>()));

        mAlbums.add(new Album("7eFyrxZRPqw8yvZXMUm88A",
                "album",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(37))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2018 Nay", "C"),
                        new Copyright("2018 Nay", "P"))),
                Utils.convertToBitmap(R.drawable.kol_hayaty),
                "Kol Hayaty",
                "2018-10-03",
                new ArrayList<Track>()));

        mAlbums.add(new Album("2D1nEskDzLz38JiUeVK5mh",
                "single",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(29))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 MuzicUp", "C"),
                        new Copyright("2020 MuzicUp", "P"))),
                Utils.convertToBitmap(R.drawable.shamekh),
                "Shamekh",
                "2020-01-12",
                new ArrayList<Track>()));

        mAlbums.add(new Album("0hZwt0aSEEiwUByVQuxntK",
                "album",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(30))),
                new ArrayList<Copyright>(Collections.singletonList(new Copyright("2012 Awakening Worldwide", "C"))),
                Utils.convertToBitmap(R.drawable.fogive_me),
                "Forgive Me",
                "2012-04-02",
                new ArrayList<Track>()));

        mAlbums.add(new Album("3JfSxDfmwS5OeHPwLSkrfr",
                "album",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(12))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2018 KIDinaKORNER/Interscope Records", "C"),
                        new Copyright("2018 KIDinaKORNER/Interscope Records", "P"))),
                Utils.convertToBitmap(R.drawable.origins),
                "Origins (Deluxe)",
                "2018-11-09",
                new ArrayList<Track>()));


        mPopularPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "OneRepublic", "mood booster",
                "Rescue Me", R.drawable.rescue_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"),false));
        tracks.add(new Track("Freaking Me Out", "Ava Max",
                "mood booster", null, R.drawable.freaking_me_out, Settings.System.DEFAULT_RINGTONE_URI,true));
        tracks.add(new Track("You Can't Stop The Girl",
                "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"),false));
        mPopularPlaylists.add(new Playlist("mood booster",
                "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks));

        mRecentPlaylists = new ArrayList<>();
        ArrayList<Track> rTracks = new ArrayList<Track>();
        rTracks.add(new Track("Little Do You Know", "Alex & Sierra", "Rewind-the sound of 2014", null, R.drawable.little_do_you_know, Settings.System.DEFAULT_RINGTONE_URI,true));
        rTracks.add(new Track("Wildest Dreams", "Taylor Swift", "Rewind-the sound of 2014", null, R.drawable.wildest_dreams, Uri.parse("http://stream.radiosai.net:8002/"),false));
        rTracks.add(new Track("One Last Time", "Ariana Grande", "Rewind-the sound of 2014", null, R.drawable.one_last_time, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"),false));
        mRecentPlaylists.add(new Playlist("Rewind-the sound of 2014", null,
                Utils.convertToBitmap(R.drawable.rewind_the_sound), rTracks));

        mRandomPlaylists = new ArrayList<>();
        ArrayList<Track> ranTracks = new ArrayList<Track>();
        ranTracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, Settings.System.DEFAULT_RINGTONE_URI,false));
        ranTracks.add(new Track("Stupid Love", "Lady Gaga", "Daily Left", null, R.drawable.stupid_love, Uri.parse("http://stream.radiosai.net:8002/"),false));
        ranTracks.add(new Track("Feel Me", "Selena Gomez", "Daily Left", null, R.drawable.feel_me, Uri.parse("http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3"),true));
        mRandomPlaylists.add(new Playlist("Daily Left", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), ranTracks));


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
        return mRandomPlaylists;
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
        return mRecentPlaylists;
    }

    /**
     * getter for Random playlist
     *
     * @param context context of hosting activity
     * @param mToken  token of user
     * @return Random  playlist
     */
    @Override
    public ArrayList<Playlist> getRandomPlaylists(Context context, String mToken) {
        return mRandomPlaylists;
    }

    @Override
    public ArrayList<Playlist> getPopularPlaylists(Context context, String mToken) {
        return mPopularPlaylists;
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
    @Override
    public boolean logIn(final Context context, String username, String password, boolean mType) {
        //initial index of user in users list
        int userIndex = -1;

        //checks if user is listener
        if (mType) {

            //search in list of user's email & password
            for (int i = 0; i < mListenerArrayList.size(); i++) {
                if (username.equals(mListenerArrayList.get(i).getmEmail()) &&
                        password.equals(mListenerArrayList.get(i).getmPassword())) {
                    //if found, then store index and stop search
                    userIndex = i;
                    break;
                }
            }
        } else {

            //in list of artist user
            for (int i = 0; i < mArtistArrayList.size(); i++) {
                if (username.equals(mArtistArrayList.get(i).getmEmail()) &&
                        password.equals(mArtistArrayList.get(i).getmPassword())) {
                    userIndex = i;
                    break;
                }
            }
        }

        //checks if data isn't in list
        if (userIndex == -1)
            return false;

        //if user is artist
        if (!mType) {

            //set token
            Constants.currentToken = "token2";

            //add some artists
            ArrayList<Artist> followed = new ArrayList<>();
            for (int i = 5; i < 10; i++) {
                followed.add(mArtists.get(i));
            }

            //creates object of user to store logged in user's data
            Constants.currentUser = new User(mArtistArrayList.get(userIndex).getmEmail(),"2030k", false
                    , Utils.convertToBitmap(R.drawable.download)
                    , "Islam Ahmed", "1998/24/11", "male"
                    , mArtistArrayList.get(userIndex).isPremuim()
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                    , followed, mAlbums, new ArrayList<Track>());
        } else {

            //for listener type, set different token
            Constants.currentToken = "token1";

            ArrayList<Artist> followed = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                followed.add(mArtists.get(i));
            }

            Constants.currentUser = new User(mListenerArrayList.get(userIndex).getmEmail(), "2030k",true
                    , Utils.convertToBitmap(R.drawable.download)
                    , "Hossam Alaa", "1999/04/06", "male"
                    , mListenerArrayList.get(userIndex).isPremuim()
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                    , followed, mAlbums, new ArrayList<Track>());
        }
        return true;
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
    public boolean signUp(final Context context, boolean mType, String email, String password,
                          String DOB, String gender, String name) {

        //set new token for current new user
        Constants.currentToken = "newToken";

        //creates new object of user and fill data
        Constants.currentUser = new User(email, "2030k", mType, Utils.convertToBitmap(R.drawable.download)
                , name, DOB, gender, false, 0, 0, new ArrayList<User>()
                , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                , new ArrayList<Artist>(), new ArrayList<Album>(), new ArrayList<Track>());
        Constants.currentUser.setmPassword(password);
        return true;
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
    public boolean checkEmailAvailability(final Context context, String email, boolean mType) {
        if (mType) {
            //if user is listener, then check in listeners list
            for (int i = 0; i < mListenerArrayList.size(); i++)
                if (email.equals(mListenerArrayList.get(i).getmEmail())) return false;
        } else {
            //if user is artist, then check artists list
            for (int i = 0; i < mArtistArrayList.size(); i++)
                if (email.equals(mArtistArrayList.get(i).getmEmail())) return false;
        }
        return true;
    }

    @Override
    public ArrayList<Container> getResentResult(Context context) {

        if (firstTimeToGetRecentSearches) {
            mRecentSearches = new ArrayList<>();
            mRecentSearches.add(new Container("Quran", context.getResources().getString(R.string.Playlist), R.drawable.images));
            mRecentSearches.add(new Container("George Wassouf", context.getResources().getString(R.string.Artist), R.drawable.download));
            mRecentSearches.add(new Container("Get Weird", context.getResources().getString(R.string.Album) + ".Little Mix", R.drawable.download1));
            mRecentSearches.add(new Container("Godzilla", context.getResources().getString(R.string.Song) + ".Eminem juice WRLD", R.drawable.images));
            mRecentSearches.add(new Container("Lollipop", context.getResources().getString(R.string.Album) + ".Little Mix", R.drawable.images3));
            mRecentSearches.add(new Container("Friction", context.getResources().getString(R.string.Playlist), R.drawable.images));
            mRecentSearches.add(new Container("Playlist", context.getResources().getString(R.string.Playlist), R.drawable.alan));
            mRecentSearches.add(new Container("Miley Cyrus", context.getResources().getString(R.string.Artist), R.drawable.download));
        }
        return mRecentSearches;
    }

    public ArrayList<Container> getAllResultsOfSearch(Context context, String searchWord) {
        String song = context.getResources().getString(R.string.Song);
        String artist = context.getResources().getString(R.string.Artist);
        String album = context.getResources().getString(R.string.Album);
        String playlist = context.getResources().getString(R.string.Playlist);
        String profile = context.getResources().getString(R.string.Profile);
        String genre = context.getResources().getString(R.string.Genre);

        mData = new ArrayList<>();
        mData.add(new Container("Quran", playlist, R.drawable.download));
        mData.add(new Container("George Wassouf", artist, R.drawable.download));
        mData.add(new Container("Get Weird", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("Godzilla", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Believer", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Natural", song + ".Imagine Dragons", R.drawable.abu));
        mData.add(new Container("Love Save Us Once", album + ".Little Mix", R.drawable.download));
        mData.add(new Container("Get Weird", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("Mozart", genre, R.drawable.images3));
        mData.add(new Container("Get Weird", album + ".Little Mix", R.drawable.images3));
        mData.add(new Container("Mine", playlist, R.drawable.images));
        mData.add(new Container("ALVXARO", playlist, R.drawable.adele));

        mData.add(new Container("Amr Diab", artist, R.drawable.amr));
        mData.add(new Container("Stop Noise", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("Beautiful Song", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Lose Yourself", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Whatever It Takes", song + ".Imagine Dragons", R.drawable.tamer_ashour));
        mData.add(new Container("Max Payne", album + ".Little Mix", R.drawable.download));
        mData.add(new Container("Rick And Morty", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("Rock", genre, R.drawable.images3));
        mData.add(new Container("Elephant", album + ".Little Mix", R.drawable.images3));
        mData.add(new Container("End Of World", playlist, R.drawable.images));
        mData.add(new Container("Crazy Love", playlist, R.drawable.angham));

        mData.add(new Container("Van Diesel", artist, R.drawable.download));
        mData.add(new Container("Wind", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("I Will Survive", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Beautiful Mind", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Sucker For Pain", song + ".Imagine Dragons", R.drawable.billie));
        mData.add(new Container("Pain", album + ".Little Mix", R.drawable.download));
        mData.add(new Container("Quite Place", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("Sleep", genre, R.drawable.images3));
        mData.add(new Container("Gamal", album + ".Little Mix", R.drawable.images3));
        mData.add(new Container("Nerds", playlist, R.drawable.images));
        mData.add(new Container("Smoke Grenades", playlist, R.drawable.jannat));

        mData.add(new Container("Samir Abo Elnil", artist, R.drawable.download));
        mData.add(new Container("Sun Rises", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("Cat Sound", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Billie Jean", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Thunder", song + ".Imagine Dragons", R.drawable.loai));
        mData.add(new Container("The Shadows", album + ".Little Mix", R.drawable.download));
        mData.add(new Container("Silence Of Lambs", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("Afro", genre, R.drawable.images3));
        mData.add(new Container("Fancy", album + ".Little Mix", R.drawable.images3));
        mData.add(new Container("Jungles", playlist, R.drawable.images));
        mData.add(new Container("Oranges", playlist, R.drawable.hamza));

        mData.add(new Container("Adele", artist, R.drawable.adele));
        mData.add(new Container("Yellow Album", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("Stressed Out", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("ThunderClouds", song + ".Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Thunder", song + ".Imagine Dragons", R.drawable.halsey));
        mData.add(new Container("High Waves", "Album.Little Mix", R.drawable.download));
        mData.add(new Container("Nice Album", album + ".Little Mix", R.drawable.download1));
        mData.add(new Container("Pop", genre, R.drawable.images3));
        mData.add(new Container("Lollipop", album + ".Little Mix", R.drawable.images3));
        mData.add(new Container("Friction", playlist, R.drawable.images));
        mData.add(new Container("Playlist", playlist, R.drawable.wael));
        mData.add(new Container("Miley Cyrus", artist, R.drawable.download));

        mData.add((new Container("my profile", profile, R.drawable.assala)));
        mData.add((new Container("mohammed ahmed", profile, R.drawable.cairokee)));
        mData.add((new Container("ali saad", profile, R.drawable.samira)));


        LinkedList<Container> resultsLinked = new LinkedList<>();
        ArrayList<Container> results = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getCat_Name().startsWith(searchWord))
                resultsLinked.addFirst(mData.get(i));
            else if (mData.get(i).getCat_Name().contains(searchWord))
                resultsLinked.addLast(mData.get(i));
        }
        results.addAll(resultsLinked);
        return results;
    }

    @Override
    public ArrayList<Container> getResultsOfSearch(Context context, String searchWord) {
        ArrayList<Container> results = new ArrayList<>();
        ArrayList<Container> temp;
        temp = getAllResultsOfSearch(context, searchWord);
        int size = 7;
        if (temp.size() < 7) size = temp.size();
        for (int i = 0; i < size; i++) {
            results.add(temp.get(i));
        }
        return results;
    }

    @Override
    public ArrayList<Container> getCategories(Context context) {
        ArrayList<Container> categories = new ArrayList<>();
        categories.add(new Container(context.getResources().getString(R.string.New_Releases), R.drawable.solid_image));
        categories.add(new Container(context.getResources().getString(R.string.Charts), R.drawable.solid_image2));
        categories.add(new Container(context.getResources().getString(R.string.Concerts), R.drawable.solid_image3));
        categories.add(new Container(context.getResources().getString(R.string.Made_for_you), R.drawable.solid_image4));
        categories.add(new Container(context.getResources().getString(R.string.Arabic), R.drawable.solid_image5));
        categories.add(new Container(context.getResources().getString(R.string.Mood), R.drawable.blue_image));
        categories.add(new Container(context.getResources().getString(R.string.Decades), R.drawable.solid_image8));
        categories.add(new Container(context.getResources().getString(R.string.Gaming), R.drawable.solid_image7));
        categories.add(new Container(context.getResources().getString(R.string.Workout), R.drawable.solid_red_image));
        categories.add(new Container(context.getResources().getString(R.string.Focus), R.drawable.multicolor_image));
        categories.add(new Container(context.getResources().getString(R.string.Party), R.drawable.solidcolor_image));
        categories.add(new Container(context.getResources().getString(R.string.Dinner), R.drawable.purble_image));
        categories.add(new Container(context.getResources().getString(R.string.Jazz), R.drawable.grey_image));
        categories.add(new Container(context.getResources().getString(R.string.Soul), R.drawable.lightpurble_image));
        categories.add(new Container(context.getResources().getString(R.string.Kids), R.drawable.lemongreen_image));
        categories.add(new Container(context.getResources().getString(R.string.Blues), R.drawable.lightgrey_image));
        categories.add(new Container(context.getResources().getString(R.string.Punk), R.drawable.lightbrown_image));
        categories.add(new Container(context.getResources().getString(R.string.Travel), R.drawable.gradientgreen_image));
        return categories;
    }

    @Override
    public ArrayList<Container> getGenres(Context context) {
        ArrayList<Container> genres = new ArrayList<>();
        genres.add(new Container(context.getResources().getString(R.string.Pop), R.drawable.blue_image2));
        genres.add(new Container(context.getResources().getString(R.string.HipHop), R.drawable.yellow_image));
        genres.add(new Container(context.getResources().getString(R.string.Rock), R.drawable.image_pink));
        genres.add(new Container(context.getResources().getString(R.string.Sleep), R.drawable.skyblue_image));
        genres.add(new Container(context.getResources().getString(R.string.Chill), R.drawable.purble_image));
        return genres;
    }

    @Override
    public ArrayList<Container> getArtists(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> artists = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Artist)))
                artists.add(temp.get(i));
        }
        return artists;
    }

    @Override
    public ArrayList<Container> getSongs(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> songs = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Song)))
                songs.add(temp.get(i));
        }
        return songs;
    }

    @Override
    public ArrayList<Container> getAlbums(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> albums = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Album)))
                albums.add(temp.get(i));
        }
        return albums;
    }

    @Override
    public ArrayList<Container> getGenresAndMoods(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> genres = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Genre)))
                genres.add(temp.get(i));
        }
        return genres;
    }

    @Override
    public ArrayList<Container> getPlaylists(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> playlists = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Playlist)))
                playlists.add(temp.get(i));
        }
        return playlists;
    }

    @Override
    public ArrayList<Container> getProfiles(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> profiles = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith(context.getResources().getString(R.string.Profile)))
                profiles.add(temp.get(i));
        }
        return profiles;
    }

    @Override
    public void removeOneRecentSearch(Context context, int position) {
        //mRecentSearches.remove(position);
        firstTimeToGetRecentSearches = false;
    }

    @Override
    public void removeAllRecentSearches(Context context) {

        firstTimeToGetRecentSearches = false;
        mRecentSearches = new ArrayList<Container>();
    }

    @Override
    public ArrayList<Container> getAllPopularPlaylists(Context context) {
        ArrayList<Container> playlists = new ArrayList<>();
        playlists.add(new Container("greate playlist", "2,700 " + context.getResources().getString(R.string.Followers), R.drawable.adele));
        playlists.add(new Container("Amr Diab", "5,200 " + context.getResources().getString(R.string.Followers), R.drawable.amr));
        playlists.add(new Container("beautiful", "3,300 " + context.getResources().getString(R.string.Followers), R.drawable.imagine));
        playlists.add(new Container("simple", "800 " + context.getResources().getString(R.string.Followers), R.drawable.alan));
        playlists.add(new Container("nice songs", "1200 " + context.getResources().getString(R.string.Followers), R.drawable.ed));
        playlists.add(new Container("araby", "3000 " + context.getResources().getString(R.string.Followers), R.drawable.assala));
        playlists.add(new Container("Bahaa Sultan", "2,100 " + context.getResources().getString(R.string.Followers), R.drawable.bahaa));
        playlists.add(new Container("anghami", "1,100 " + context.getResources().getString(R.string.Followers), R.drawable.angham));
        playlists.add(new Container("Thunder", "2100 " + context.getResources().getString(R.string.Followers), R.drawable.halsey));
        playlists.add(new Container("selena", "2800 " + context.getResources().getString(R.string.Followers), R.drawable.selena));
        playlists.add(new Container("Smoke Grenades", "500 " + context.getResources().getString(R.string.Followers), R.drawable.jannat));
        playlists.add(new Container("Playlist", "26 " + context.getResources().getString(R.string.Followers), R.drawable.wael));
        playlists.add(new Container("3 d2at", "773 " + context.getResources().getString(R.string.Followers), R.drawable.abu));
        playlists.add(new Container("Oranges", "470 " + context.getResources().getString(R.string.Followers), R.drawable.hamza));

        return playlists;
    }

    @Override
    public ArrayList<Container> getFourPlaylists(Context context) {
        ArrayList<Container> data = new ArrayList<>();
        ArrayList<Container> playlists = getAllPopularPlaylists(context);
        int n = 4;
        if (playlists.size() < n) n = playlists.size();
        for (int i = 0; i < n; i++) {
            data.add(playlists.get(i));
        }
        return data;
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
        for (Artist artist : mArtists) {
            if (artist.getId().equals(id))
                return artist;
        }

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
        ArrayList<Artist> related;
        if (id.equals("1") || id.equals("6"))
            related = new ArrayList<>(mArtists.subList(10, 16));
        else if (id.equals("2") || id.equals("7"))
            related = new ArrayList<>(mArtists.subList(16, 22));
        else if (id.equals("3") || id.equals("8"))
            related = new ArrayList<>(mArtists.subList(22, 28));
        else if (id.equals("4") || id.equals("9"))
            related = new ArrayList<>(mArtists.subList(28, 34));
        else if (id.equals("5") || id.equals("10"))
            related = new ArrayList<>(mArtists.subList(34, 40));
        else
            related = new ArrayList<>();
        return related;
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
        ArrayList<Artist> followedArtists = Constants.currentUser.getFollowingArtists();
        ArrayList<Artist> returnedArtists = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, followedArtists.size()); i++) {
            returnedArtists.add(followedArtists.get(i));
        }

        return returnedArtists;
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
        for (Artist artist : mArtists) {
            if (artist.getId().equals(id)) {
                if (!isFollowing(type, mToken, id))
                    Constants.currentUser.followArtist(artist);
                return;
            }
        }
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
        for (Artist artist : mArtists) {
            if (artist.getId().equals(id)) {
                if (isFollowing(type, mToken, id))
                    Constants.currentUser.unFollowArtist(artist);
                return;
            }
        }
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
        ArrayList<Artist> mFollowingArtists = Constants.currentUser.getFollowingArtists();
        for (Artist artist : mFollowingArtists) {
            if (artist.getId().equals(id))
                return true;
        }
        return false;
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
        ArrayList<Artist> mRecommendedArtists = new ArrayList<>();
        if (type) {
            for (int i = 5; i < Math.min(10, limit + 5); i++) {
                Artist artist = mArtists.get(i);
                mRecommendedArtists.add(artist);
            }
        } else {
            for (int i = 0; i < Math.min(5, limit); i++) {
                Artist artist = mArtists.get(i);
                mRecommendedArtists.add(artist);
            }
        }
        return mRecommendedArtists;
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
        ArrayList<Artist> found = new ArrayList<>();

        for (Artist artist : mArtists) {
            if (Pattern.compile(Pattern.quote(q), Pattern.CASE_INSENSITIVE).matcher(artist.getArtistName()).find())
                found.add(artist);
        }

        ArrayList<Artist> searchResult = new ArrayList<>();
        for (int i = offset; i < Math.min(found.size(), offset + limit); i++) {
            searchResult.add(found.get(i));
        }

        return searchResult;
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
        ArrayList<Album> savedAlbums = Constants.currentUser.getSavedAlbums();
        ArrayList<Album> returnedAlbums = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, savedAlbums.size()); i++) {
            returnedAlbums.add(savedAlbums.get(i));
        }

        return returnedAlbums;
    }

    @Override
    public boolean promotePremium(final Context context, View root, String token){
        Constants.currentUser.setPremuim(true);
        return true;
    }

    @Override
    public ArrayList<Container> getProfileFollowers(Context context) {
        ArrayList<Container> followers=new ArrayList<>();
        followers.add(new Container("HuffPost", "2,700 " + context.getResources().getString(R.string.Followers), R.drawable.images));
        followers.add(new Container("Majeestic Casual", "5,200 " + context.getResources().getString(R.string.Followers), R.drawable.images3));
        followers.add(new Container("beautiful", "3,300 " + context.getResources().getString(R.string.Followers), R.drawable.download));
        followers.add(new Container("Anders Stab Nielson", "800 " + context.getResources().getString(R.string.Followers), R.drawable.download1));
        followers.add(new Container("Simon Hoffman", "1200 " + context.getResources().getString(R.string.Followers), R.drawable.images2));
        followers.add(new Container("HouseNation", "3000 " + context.getResources().getString(R.string.Followers), R.drawable.abu));
        followers.add(new Container("Chan Jun Jie", "2,100 " + context.getResources().getString(R.string.Followers), R.drawable.halsey));
        followers.add(new Container("Efal Sayed", "1,100 " + context.getResources().getString(R.string.Followers), R.drawable.bahaa));
        followers.add(new Container("Michelle Choi", "2100 " + context.getResources().getString(R.string.Followers), R.drawable.halsey));
        followers.add(new Container("Brandon Bay", "2800 " + context.getResources().getString(R.string.Followers), R.drawable.selena));
        followers.add(new Container("Ryan Jones", "500 " + context.getResources().getString(R.string.Followers), R.drawable.hamza));
        followers.add(new Container("MTV Brasil", "26 " + context.getResources().getString(R.string.Followers), R.drawable.jannat));
        followers.add(new Container("Sonny Lind", "773 " + context.getResources().getString(R.string.Followers), R.drawable.wael));
        followers.add(new Container("Oranges", "470 " + context.getResources().getString(R.string.Followers), R.drawable.angham));
        followers.add(new Container("greate Artist", "1,200 " + context.getResources().getString(R.string.Followers), R.drawable.images));
        followers.add(new Container("Kevin Cross", "1,523 " + context.getResources().getString(R.string.Followers), R.drawable.images3));
        followers.add(new Container("Craige Dewart", "4,444 " + context.getResources().getString(R.string.Followers), R.drawable.download));
        followers.add(new Container("Wes Ley", "832 " + context.getResources().getString(R.string.Followers), R.drawable.download1));
        followers.add(new Container("Jasper Oh", "1328 " + context.getResources().getString(R.string.Followers), R.drawable.images2));
        followers.add(new Container("Kevin Enhorn", "3034 " + context.getResources().getString(R.string.Followers), R.drawable.abu));
        followers.add(new Container("Mark Springer", "6,100 " + context.getResources().getString(R.string.Followers), R.drawable.halsey));
        followers.add(new Container("anghami", "11,000 " + context.getResources().getString(R.string.Followers), R.drawable.bahaa));
        followers.add(new Container("Ahmed Ali Sheikh", "110 " + context.getResources().getString(R.string.Followers), R.drawable.halsey));
        followers.add(new Container("selena", "28,000 " + context.getResources().getString(R.string.Followers), R.drawable.selena));
        followers.add(new Container("Martin kepic", "0 " + context.getResources().getString(R.string.Followers), R.drawable.hamza));
        followers.add(new Container("An ther", "26,000 " + context.getResources().getString(R.string.Followers), R.drawable.jannat));
        followers.add(new Container("Ketan Patel", "773 " + context.getResources().getString(R.string.Followers), R.drawable.wael));
        followers.add(new Container("Dani Hecht", "470 " + context.getResources().getString(R.string.Followers), R.drawable.angham));

        return followers;
    }

    @Override
    public ArrayList<Container> getProfileFollowing(Context context) {
        ArrayList<Container> followers=new ArrayList<>();
        followers.add(new Container("Adele", "200,700 " + context.getResources().getString(R.string.Followers), R.drawable.adele));
        followers.add(new Container("Amr Diab", "500,200 " + context.getResources().getString(R.string.Followers), R.drawable.amr));
        followers.add(new Container("Imagine dragons", "300,300 " + context.getResources().getString(R.string.Followers), R.drawable.imagine));
        followers.add(new Container("Alan Walker", "80,000 " + context.getResources().getString(R.string.Followers), R.drawable.alan));
        followers.add(new Container("ED Sheeran", "12,000 " + context.getResources().getString(R.string.Followers), R.drawable.ed));
        followers.add(new Container("Asala ", "30,000 " + context.getResources().getString(R.string.Followers), R.drawable.assala));
        followers.add(new Container("Bahaa Sultan", "21,000 " + context.getResources().getString(R.string.Followers), R.drawable.bahaa));
        followers.add(new Container("angham", "11,000 " + context.getResources().getString(R.string.Followers), R.drawable.angham));
        followers.add(new Container("Thunder and Tomorrow", "2100 " + context.getResources().getString(R.string.Followers), R.drawable.halsey));
        followers.add(new Container("Selena", "28,000 " + context.getResources().getString(R.string.Followers), R.drawable.selena));
        followers.add(new Container("Smoke Grenades", "5,000 " + context.getResources().getString(R.string.Followers), R.drawable.jannat));
        followers.add(new Container("Wael Jassar", "260 " + context.getResources().getString(R.string.Followers), R.drawable.wael));
        followers.add(new Container("Abo", "7,730 " + context.getResources().getString(R.string.Followers), R.drawable.abu));
        followers.add(new Container("Hamza Nemra", "4,700 " + context.getResources().getString(R.string.Followers), R.drawable.hamza));
        return followers;
    }
}
