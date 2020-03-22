package com.example.symphonia.Service;

import android.content.Context;
import android.net.Uri;

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

public class MockService implements APIs {

    private ArrayList<Playlist> mRandomPlaylists;
    private ArrayList<Playlist> mRecentPlaylists;
    private ArrayList<Artist> mArtists;
    private ArrayList<Album> mAlbums;
    private ArrayList<Container> mData;
    private ArrayList<Container> mRecentSearches;
    private ArrayList<Playlist> mPopularPlaylists;

    private ArrayList<User> mListenerArrayList;
    private ArrayList<User> mArtistArrayList;

    public MockService() {
        mListenerArrayList = new ArrayList<>();
        mArtistArrayList = new ArrayList<>();

        mListenerArrayList.add(new User("user1@symphonia.com", "12345678", true));
        mListenerArrayList.add(new User("user2@symphonia.com", "12345678", true));
        mListenerArrayList.add(new User("user3@symphonia.com", "12345678", true));

        mArtistArrayList.add(new User("artist1@symphonia.com", "12345678", false));
        mArtistArrayList.add(new User("artist2@symphonia.com", "12345678", false));
        mArtistArrayList.add(new User("artist3@symphonia.com", "12345678", false));


        mData = new ArrayList<>();
        mData.add(new Container("Quran", "Playlist", R.drawable.download));
        mData.add(new Container("George Wassouf", "Artist", R.drawable.download));
        mData.add(new Container("Get Weird", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("Godzilla", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Believer", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Natural", "Song.Imagine Dragons", R.drawable.abu));
        mData.add(new Container("Love Save Us Once", "Album.Little Mix", R.drawable.download));
        mData.add(new Container("Get Weird", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("Mozart", "Genre", R.drawable.images3));
        mData.add(new Container("Get Weird", "Album.Little Mix", R.drawable.images3));
        mData.add(new Container("Mine", "Playlist", R.drawable.images));
        mData.add(new Container("ALVXARO", "Playlist", R.drawable.adele));

        mData.add(new Container("Amr Diab", "Artist", R.drawable.amr));
        mData.add(new Container("Stop Noise", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("Beautiful Song", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Lose Yourself", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Whatever It Takes", "Song.Imagine Dragons", R.drawable.tamer_ashour));
        mData.add(new Container("Max Payne", "Album.Little Mix", R.drawable.download));
        mData.add(new Container("Rick And Morty", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("Rock", "Genre", R.drawable.images3));
        mData.add(new Container("Elephant", "Album.Little Mix", R.drawable.images3));
        mData.add(new Container("End Of World", "Playlist", R.drawable.images));
        mData.add(new Container("Crazy Love", "Playlist", R.drawable.angham));

        mData.add(new Container("Van Diesel", "Artist", R.drawable.download));
        mData.add(new Container("Wind", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("I Will Survive", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Beautiful Mind", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Sucker For Pain", "Song.Imagine Dragons", R.drawable.billie));
        mData.add(new Container("Pain", "Album.Little Mix", R.drawable.download));
        mData.add(new Container("Quite Place", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("Sleep", "Genre", R.drawable.images3));
        mData.add(new Container("Gamal", "Album.Little Mix", R.drawable.images3));
        mData.add(new Container("Nerds", "Playlist", R.drawable.images));
        mData.add(new Container("Smoke Grenades", "Playlist", R.drawable.jannat));

        mData.add(new Container("Samir Abo Elnil", "Artist", R.drawable.download));
        mData.add(new Container("Sun Rises", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("Cat Sound", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Billie Jean", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Thunder", "Song.Imagine Dragons", R.drawable.loai));
        mData.add(new Container("The Shadows", "Album.Little Mix", R.drawable.download));
        mData.add(new Container("Silence Of Lambs", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("Afro", "Genre", R.drawable.images3));
        mData.add(new Container("Fancy", "Album.Little Mix", R.drawable.images3));
        mData.add(new Container("Jungles", "Playlist", R.drawable.images));
        mData.add(new Container("Oranges", "Playlist", R.drawable.hamza));

        mData.add(new Container("Adele", "Artist", R.drawable.adele));
        mData.add(new Container("Yellow Album", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("Stressed Out", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("ThunderClouds", "Song.Eminem juice WRLD", R.drawable.images));
        mData.add(new Container("Thunder", "Song.Imagine Dragons", R.drawable.halsey));
        mData.add(new Container("High Waves", "Album.Little Mix", R.drawable.download));
        mData.add(new Container("Nice Album", "Album.Little Mix", R.drawable.download1));
        mData.add(new Container("Pop", "Genre", R.drawable.images3));
        mData.add(new Container("Lollipop", "Album.Little Mix", R.drawable.images3));
        mData.add(new Container("Friction", "Playlist", R.drawable.images));
        mData.add(new Container("Playlist", "Playlist", R.drawable.wael));
        mData.add(new Container("Miley Cyrus", "Artist", R.drawable.download));

        mData.add((new Container("my profile", "Profile", R.drawable.assala)));
        mData.add((new Container("mohammed ahmed", "Profile", R.drawable.cairokee)));
        mData.add((new Container("ali saad", "Profile", R.drawable.samira)));


        mRecentSearches = new ArrayList<>();
        mRecentSearches.add(new Container("Quran", "Playlist", R.drawable.images));
        mRecentSearches.add(new Container("George Wassouf", "Artist", R.drawable.download));
        mRecentSearches.add(new Container("Get Weird", "Album.Little Mix", R.drawable.download1));
        mRecentSearches.add(new Container("Godzilla", "Song.Eminem juice WRLD", R.drawable.images));
        mRecentSearches.add(new Container("Lollipop", "Album.Little Mix", R.drawable.images3));
        mRecentSearches.add(new Container("Friction", "Playlist", R.drawable.images));
        mRecentSearches.add(new Container("Playlist", "Playlist", R.drawable.alan));
        mRecentSearches.add(new Container("Miley Cyrus", "Artist", R.drawable.download));

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

        mAlbums.add(new Album("6qqNVTkY8uBg9cP3Jd7DAH", "single",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(7))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 Darkroom/Interscope Records", "C"),
                        new Copyright("2020 Darkroom/Interscope Records", "P"))),
                Utils.convertToBitmap(R.drawable.no_time_to_die), "No Time To Die", "2020-02-13",
                new ArrayList<Track>()));

        mAlbums.add(new Album("7eFyrxZRPqw8yvZXMUm88A", "album",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(37))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2018 Nay", "C"),
                        new Copyright("2018 Nay", "P"))),
                Utils.convertToBitmap(R.drawable.kol_hayaty), "Kol Hayaty", "2018-10-03",
                new ArrayList<Track>()));

        mAlbums.add(new Album("2D1nEskDzLz38JiUeVK5mh", "single",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(29))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 MuzicUp", "C"),
                        new Copyright("2020 MuzicUp", "P"))),
                Utils.convertToBitmap(R.drawable.shamekh), "Shamekh", "2020-01-12",
                new ArrayList<Track>()));

        mAlbums.add(new Album("0hZwt0aSEEiwUByVQuxntK", "album",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(30))),
                new ArrayList<Copyright>(Collections.singletonList(new Copyright("2012 Awakening Worldwide", "C"))),
                Utils.convertToBitmap(R.drawable.fogive_me), "Forgive Me", "2012-04-02",
                new ArrayList<Track>()));

        mAlbums.add(new Album("3JfSxDfmwS5OeHPwLSkrfr", "album",
                new ArrayList<Artist>(Collections.singletonList(mArtists.get(12))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2018 KIDinaKORNER/Interscope Records", "C"),
                        new Copyright("2018 KIDinaKORNER/Interscope Records", "P"))),
                Utils.convertToBitmap(R.drawable.origins), "Origins (Deluxe)", "2018-11-09",
                new ArrayList<Track>()));


        mPopularPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "OneRepublic", "mood booster", "Rescue Me", R.drawable.rescue_me, Uri.parse("https://www.searchgurbani.com/audio/sggs/73.mp3")));
        tracks.add(new Track("Freaking Me Out", "Ava Max", "mood booster", null, R.drawable.freaking_me_out, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        tracks.add(new Track("You Can't Stop The Girl", "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl, Uri.parse("https://www.searchgurbani.com/audio/sggs/1.mp3")));
        mPopularPlaylists.add(new Playlist("mood booster", "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks));

        mRecentPlaylists = new ArrayList<>();
        ArrayList<Track> rTracks = new ArrayList<Track>();
        rTracks.add(new Track("Little Do You Know", "Alex & Sierra", "Rewind-the sound of 2014", null, R.drawable.little_do_you_know, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        rTracks.add(new Track("Wildest Dreams", "Taylor Swift", "Rewind-the sound of 2014", null, R.drawable.wildest_dreams, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        rTracks.add(new Track("One Last Time", "Ariana Grande", "Rewind-the sound of 2014", null, R.drawable.one_last_time, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        mRecentPlaylists.add(new Playlist("Rewind-the sound of 2014", null,
                Utils.convertToBitmap(R.drawable.rewind_the_sound), rTracks));

        mRandomPlaylists = new ArrayList<>();
        ArrayList<Track> ranTracks = new ArrayList<Track>();
        ranTracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        ranTracks.add(new Track("Stupid Love", "Lady Gaga", "Daily Left", null, R.drawable.stupid_love, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        ranTracks.add(new Track("Feel Me", "Selena Gomez", "Daily Left", null, R.drawable.feel_me, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        mRandomPlaylists.add(new Playlist("Daily Left", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), ranTracks));


    }

    @Override
    public ArrayList<Playlist> getMadeForYouPlaylists(Context context, String mToken) {
        return mRandomPlaylists;
    }

    @Override
    public ArrayList<Playlist> getRecentPlaylists(Context context, String mToken) {
        return mRecentPlaylists;
    }

    @Override
    public ArrayList<Playlist> getRandomPlaylists(Context context, String mToken) {
        return mRandomPlaylists;
    }

    @Override
    public ArrayList<Playlist> getPopularPlaylists(Context context, String mToken) {
        return mPopularPlaylists;
    }

    @Override
    public boolean logIn(Context context, String username, String password, boolean mType) {
        int userIndex = -1;

        if (mType) {
            for (int i = 0; i < mListenerArrayList.size(); i++) {
                if (username.equals(mListenerArrayList.get(i).getmEmail()) &&
                        password.equals(mListenerArrayList.get(i).getmPassword())) {
                    userIndex = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < mArtistArrayList.size(); i++) {
                if (username.equals(mArtistArrayList.get(i).getmEmail()) &&
                        password.equals(mArtistArrayList.get(i).getmPassword())) {
                    userIndex = i;
                    break;
                }
            }
        }

        if (userIndex == -1)
            return false;


        if (!mType) {
            Constants.currentToken = "token2";

            ArrayList<Artist> followed = new ArrayList<>();
            for (int i = 5; i < 10; i++) {
                followed.add(mArtists.get(i));
            }

            Constants.currentUser = new User(mArtistArrayList.get(userIndex).getmEmail(), false
                    , Utils.convertToBitmap(R.drawable.download)
                    , "Islam Ahmed", "1998/24/11", "male", true
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                    , followed, mAlbums, new ArrayList<Track>());
        } else {
            Constants.currentToken = "token1";

            ArrayList<Artist> followed = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                followed.add(mArtists.get(i));
            }

            Constants.currentUser = new User(mListenerArrayList.get(userIndex).getmEmail(), true
                    , Utils.convertToBitmap(R.drawable.download)
                    , "Hossam Alaa", "1999/04/06", "male", true
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                    , followed, mAlbums, new ArrayList<Track>());
        }
        return true;
    }


    @Override
    public boolean signUp(Context context, boolean mType, String email, String password,
                          String DOB, String gender, String name) {

        Constants.currentToken = "newToken";
        Constants.currentUser = new User(email, mType, Utils.convertToBitmap(R.drawable.download)
                , name, DOB, gender, false, 0, 0, new ArrayList<User>()
                , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                , new ArrayList<Artist>(), new ArrayList<Album>(), new ArrayList<Track>());
        Constants.currentUser.setmPassword(password);
        return true;
    }

    @Override
    public boolean checkEmailAvailability(Context context, String email, boolean mType) {
        if (mType) {
            for (int i = 0; i < mListenerArrayList.size(); i++)
                if (email.equals(mListenerArrayList.get(i).getmEmail())) return false;
        } else {
            for (int i = 0; i < mArtistArrayList.size(); i++)
                if (email.equals(mArtistArrayList.get(i).getmEmail())) return false;
        }
        return true;
    }

    @Override
    public ArrayList<Container> getResentResult(Context context) {
        return mRecentSearches;
    }

    public ArrayList<Container> getAllResultsOfSearch(Context context, String searchWord) {
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
        categories.add(new Container("New Releases", R.drawable.solid_image));
        categories.add(new Container("Charts", R.drawable.solid_image2));
        categories.add(new Container("Concerts", R.drawable.solid_image3));
        categories.add(new Container("Made for you", R.drawable.solid_image4));
        categories.add(new Container("Arabic", R.drawable.solid_image5));
        categories.add(new Container("Mood", R.drawable.blue_image));
        categories.add(new Container("Decades", R.drawable.solid_image8));
        categories.add(new Container("Gaming", R.drawable.solid_image7));
        categories.add(new Container("Workout", R.drawable.solid_red_image));
        categories.add(new Container("Focus", R.drawable.multicolor_image));
        categories.add(new Container("Party", R.drawable.solidcolor_image));
        categories.add(new Container("Dinner", R.drawable.purble_image));
        categories.add(new Container("Jazz", R.drawable.grey_image));
        categories.add(new Container("Soul", R.drawable.lightpurble_image));
        categories.add(new Container("Kids", R.drawable.lemongreen_image));
        categories.add(new Container("Blues", R.drawable.lightgrey_image));
        categories.add(new Container("Punk", R.drawable.lightbrown_image));
        categories.add(new Container("Travel", R.drawable.gradientgreen_image));
        return categories;
    }

    @Override
    public ArrayList<Container> getGenres(Context context) {
        ArrayList<Container> genres = new ArrayList<>();
        genres.add(new Container("Pop", R.drawable.blue_image2));
        genres.add(new Container("Hip-Hop", R.drawable.yellow_image));
        genres.add(new Container("Rock", R.drawable.image_pink));
        genres.add(new Container("Sleep", R.drawable.skyblue_image));
        genres.add(new Container("Chill", R.drawable.purble_image));
        return genres;
    }

    @Override
    public ArrayList<Container> getArtists(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> artists = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith("Artist"))
                artists.add(temp.get(i));
        }
        return artists;
    }

    @Override
    public ArrayList<Container> getSongs(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> songs = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith("Song"))
                songs.add(temp.get(i));
        }
        return songs;
    }

    @Override
    public ArrayList<Container> getAlbums(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> albums = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith("Album"))
                albums.add(temp.get(i));
        }
        return albums;
    }

    @Override
    public ArrayList<Container> getGenresAndMoods(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> genres = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith("Genre"))
                genres.add(temp.get(i));
        }
        return genres;
    }

    @Override
    public ArrayList<Container> getPlaylists(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> playlists = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith("Playlist"))
                playlists.add(temp.get(i));
        }
        return playlists;
    }

    @Override
    public ArrayList<Container> getProfiles(Context context, String searchWord) {
        ArrayList<Container> temp = getAllResultsOfSearch(context, searchWord);
        ArrayList<Container> profiles = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getCat_Name2().startsWith("Profile"))
                profiles.add(temp.get(i));
        }
        return profiles;
    }

    @Override
    public void removeOneRecentSearch(Context context, int position) {
        mRecentSearches.remove(position);
    }

    @Override
    public void removeAllRecentSearches(Context context) {

        mRecentSearches = new ArrayList<Container>();
    }

    @Override
    public ArrayList<Container> getAllPopularPlaylists(Context context) {
        ArrayList<Container> playlists=new ArrayList<>();
        playlists.add(new Container("greate playlist","2,700 followers",R.drawable.adele));
        playlists.add(new Container("Amr Diab","5,200 followers",R.drawable.amr));
        playlists.add(new Container("beautiful","3,300 followers",R.drawable.imagine));
        playlists.add(new Container("simple","800 followers",R.drawable.alan));
        playlists.add(new Container("nice songs","1200 followers",R.drawable.ed));
        playlists.add(new Container("araby","3000 followers",R.drawable.assala));
        playlists.add(new Container("Bahaa Sultan","2,100 followers",R.drawable.bahaa));
        playlists.add(new Container("anghami","1,100 followers",R.drawable.angham));
        playlists.add(new Container("Thunder", "2100 followers", R.drawable.halsey));
        playlists.add(new Container("selena","2800 followers",R.drawable.selena));
        playlists.add(new Container("Smoke Grenades", "500 followers", R.drawable.jannat));
        playlists.add(new Container("Playlist", "26 followers", R.drawable.wael));
        playlists.add(new Container("3 d2at", "773 followers", R.drawable.abu));
        playlists.add(new Container("Oranges", "470 followers", R.drawable.hamza));

        return playlists;
    }

    @Override
    public ArrayList<Container> getFourPlaylists(Context context) {
        ArrayList<Container>data=new ArrayList<>();
        ArrayList<Container>playlists=getAllPopularPlaylists(context);
        int n=4;
        if(playlists.size()<n) n=playlists.size();
        for(int i=0 ; i<n ; i++){
            data.add(playlists.get(i));
        }
        return data;
    }

    @Override
    public Artist getArtist(Context context, String mToken, String id) {
        for (Artist artist: mArtists) {
            if(artist.getId().equals(id))
                return artist;
        }

        return null;
    }

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

    @Override
    public ArrayList<Artist> getFollowedArtists(Boolean type, String mToken, int limit) {
        ArrayList<Artist> followedArtists = Constants.currentUser.getFollowingArtists();
        ArrayList<Artist> returnedArtists = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, followedArtists.size()); i++) {
            returnedArtists.add(followedArtists.get(i));
        }

        return returnedArtists;
    }

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

    @Override
    public Boolean isFollowing(Boolean type, String mToken, String id) {
        ArrayList<Artist> mFollowingArtists = Constants.currentUser.getFollowingArtists();
        for (Artist artist : mFollowingArtists) {
            if (artist.getId().equals(id))
                return true;
        }
        return false;
    }

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

    @Override
    public ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit) {
        ArrayList<Artist> found = new ArrayList<>();
        for (Artist artist : mArtists) {
            if (artist.getArtistName().contains(q))
                found.add(artist);
        }

        ArrayList<Artist> searchResult = new ArrayList<>();
        for (int i = offset; i < Math.min(found.size(), offset + limit); i++) {
            searchResult.add(found.get(i));
        }

        return searchResult;
    }

    @Override
    public ArrayList<Album> getUserSavedAlbums(Context context, String mToken, int offset, int limit) {
        ArrayList<Album> savedAlbums = Constants.currentUser.getSavedAlbums();
        ArrayList<Album> returnedAlbums = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, savedAlbums.size()); i++) {
            returnedAlbums.add(savedAlbums.get(i));
        }

        return returnedAlbums;
    }
}
