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

    private ArrayList<Playlist> randomPlaylists;
    private ArrayList<Playlist> recentPlaylists;
    private ArrayList<Artist> artists;
    private ArrayList<Album> albums;
    private ArrayList<Container> data;
    private ArrayList<Container> recentSearches;
    private ArrayList<Playlist> popularPlaylists;
    private ArrayList<User> listenerArrayList;

    private ArrayList<User> artistArrayList;
    public MockService() {
        listenerArrayList = new ArrayList<>();
        artistArrayList = new ArrayList<>();

        listenerArrayList.add(new User("user1@symphonia.com", "12345678", true));
        listenerArrayList.add(new User("user2@symphonia.com", "12345678", true));
        listenerArrayList.add(new User("user3@symphonia.com", "12345678", true));

        artistArrayList.add(new User("artist1@symphonia.com", "12345678", false));
        artistArrayList.add(new User("artist2@symphonia.com", "12345678", false));
        artistArrayList.add(new User("artist3@symphonia.com", "12345678", false));



        artists = new ArrayList<>();
        artists.add(new Artist("1", Utils.convertToBitmap(R.drawable.ragheb), "Ragheb Alama"));
        artists.add(new Artist("2", Utils.convertToBitmap(R.drawable.elissa), "Elissa"));
        artists.add(new Artist("3", Utils.convertToBitmap(R.drawable.angham), "Angham"));
        artists.add(new Artist("4", Utils.convertToBitmap(R.drawable.wael), "Wael Kfoury"));
        artists.add(new Artist("5", Utils.convertToBitmap(R.drawable.wael_gassar), "Wael Jassar"));
        artists.add(new Artist("6", Utils.convertToBitmap(R.drawable.shawn), "Shawn Mendes"));
        artists.add(new Artist("7", Utils.convertToBitmap(R.drawable.bts), "BTS"));
        artists.add(new Artist("8", Utils.convertToBitmap(R.drawable.billie), "Billie Eilish"));
        artists.add(new Artist("9", Utils.convertToBitmap(R.drawable.alan), "Alan Walker"));
        artists.add(new Artist("10", Utils.convertToBitmap(R.drawable.saaad), "Saad Lamjarred"));
        artists.add(new Artist("11", Utils.convertToBitmap(R.drawable.ed), "Ed Sheeran"));
        artists.add(new Artist("12", Utils.convertToBitmap(R.drawable.halsey), "Halsey"));
        artists.add(new Artist("13", Utils.convertToBitmap(R.drawable.imagine), "Imagine Dragons"));
        artists.add(new Artist("14", Utils.convertToBitmap(R.drawable.ariana), "Ariana Grande"));
        artists.add(new Artist("15", Utils.convertToBitmap(R.drawable.aseel), "Aseel Hameem"));
        artists.add(new Artist("16", Utils.convertToBitmap(R.drawable.maroon), "Maroon 5"));
        artists.add(new Artist("17", Utils.convertToBitmap(R.drawable.ali), "Ali Gatie"));
        artists.add(new Artist("18", Utils.convertToBitmap(R.drawable.zayn), "ZAYN"));
        artists.add(new Artist("19", Utils.convertToBitmap(R.drawable.cairokee), "Cairokee"));
        artists.add(new Artist("20", Utils.convertToBitmap(R.drawable.rauf_fayk), "Rauf & Faik"));
        artists.add(new Artist("21", Utils.convertToBitmap(R.drawable.adham), "Adham Nabulsi"));
        artists.add(new Artist("22", Utils.convertToBitmap(R.drawable.abu), "Abu"));
        artists.add(new Artist("23", Utils.convertToBitmap(R.drawable.eminem), "Eminem"));
        artists.add(new Artist("24", Utils.convertToBitmap(R.drawable.hussien), "Hussain Al Jassmi"));
        artists.add(new Artist("25", Utils.convertToBitmap(R.drawable.nancy), "Nancy Ajram"));
        artists.add(new Artist("26", Utils.convertToBitmap(R.drawable.havana), "Camila Cabello"));
        artists.add(new Artist("27", Utils.convertToBitmap(R.drawable.selena), "Selena Gomez"));
        artists.add(new Artist("28", Utils.convertToBitmap(R.drawable.taylor), "Taylor Swift"));
        artists.add(new Artist("29", Utils.convertToBitmap(R.drawable.tamer_ashour), "Tamer Ashour"));
        artists.add(new Artist("30", Utils.convertToBitmap(R.drawable.assala), "Assala Nasri"));
        artists.add(new Artist("31", Utils.convertToBitmap(R.drawable.maher), "Maher Zain"));
        artists.add(new Artist("32", Utils.convertToBitmap(R.drawable.adele), "Adele"));
        artists.add(new Artist("33", Utils.convertToBitmap(R.drawable.hamza), "Hamza Namira"));
        artists.add(new Artist("34", Utils.convertToBitmap(R.drawable.justin), "Justin Bieber"));
        artists.add(new Artist("35", Utils.convertToBitmap(R.drawable.jannat), "Jannat"));
        artists.add(new Artist("36", Utils.convertToBitmap(R.drawable.samira), "Samira Said"));
        artists.add(new Artist("37", Utils.convertToBitmap(R.drawable.maryam), "Myriam Fares"));
        artists.add(new Artist("38", Utils.convertToBitmap(R.drawable.amr), "Amr Diab"));
        artists.add(new Artist("39", Utils.convertToBitmap(R.drawable.bahaa), "Bahaa Sultan"));
        artists.add(new Artist("40", Utils.convertToBitmap(R.drawable.loai), "Loai"));


        albums = new ArrayList<>();

        albums.add(new Album("6qqNVTkY8uBg9cP3Jd7DAH", "single",
                new ArrayList<Artist>(Collections.singletonList(artists.get(7))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 Darkroom/Interscope Records", "C"),
                        new Copyright("2020 Darkroom/Interscope Records", "P"))),
                Utils.convertToBitmap(R.drawable.no_time_to_die), "No Time To Die", "2020-02-13",
                new ArrayList<Track>()));

        albums.add(new Album("7eFyrxZRPqw8yvZXMUm88A", "album",
                new ArrayList<Artist>(Collections.singletonList(artists.get(37))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2018 Nay", "C"),
                        new Copyright("2018 Nay", "P"))),
                Utils.convertToBitmap(R.drawable.kol_hayaty), "Kol Hayaty", "2018-10-03",
                new ArrayList<Track>()));

        albums.add(new Album("2D1nEskDzLz38JiUeVK5mh", "single",
                new ArrayList<Artist>(Collections.singletonList(artists.get(29))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2020 MuzicUp", "C"),
                        new Copyright("2020 MuzicUp", "P"))),
                Utils.convertToBitmap(R.drawable.shamekh), "Shamekh", "2020-01-12",
                new ArrayList<Track>()));

        albums.add(new Album("0hZwt0aSEEiwUByVQuxntK", "album",
                new ArrayList<Artist>(Collections.singletonList(artists.get(30))),
                new ArrayList<Copyright>(Collections.singletonList(new Copyright("2012 Awakening Worldwide", "C"))),
                Utils.convertToBitmap(R.drawable.fogive_me), "Forgive Me", "2012-04-02",
                new ArrayList<Track>()));

        albums.add(new Album("3JfSxDfmwS5OeHPwLSkrfr", "album",
                new ArrayList<Artist>(Collections.singletonList(artists.get(12))),
                new ArrayList<Copyright>(Arrays.asList(new Copyright("2018 KIDinaKORNER/Interscope Records", "C"),
                        new Copyright("2018 KIDinaKORNER/Interscope Records", "P"))),
                Utils.convertToBitmap(R.drawable.origins), "Origins (Deluxe)", "2018-11-09",
                new ArrayList<Track>()));


        popularPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "OneRepublic", "mood booster", "Rescue Me", R.drawable.rescue_me, Uri.parse("https://www.searchgurbani.com/audio/sggs/73.mp3")));
        tracks.add(new Track("Freaking Me Out", "Ava Max", "mood booster", null, R.drawable.freaking_me_out, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        tracks.add(new Track("You Can't Stop The Girl", "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl, Uri.parse("https://www.searchgurbani.com/audio/sggs/1.mp3")));
        popularPlaylists.add(new Playlist("mood booster", "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks));

        recentPlaylists = new ArrayList<>();
        ArrayList<Track> rTracks = new ArrayList<Track>();
        rTracks.add(new Track("Little Do You Know", "Alex & Sierra", "Rewind-the sound of 2014", null, R.drawable.little_do_you_know, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        rTracks.add(new Track("Wildest Dreams", "Taylor Swift", "Rewind-the sound of 2014", null, R.drawable.wildest_dreams, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        rTracks.add(new Track("One Last Time", "Ariana Grande", "Rewind-the sound of 2014", null, R.drawable.one_last_time, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        recentPlaylists.add(new Playlist("Rewind-the sound of 2014", null,
                Utils.convertToBitmap(R.drawable.rewind_the_sound), rTracks));

        randomPlaylists = new ArrayList<>();
        ArrayList<Track> ranTracks = new ArrayList<Track>();
        ranTracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        ranTracks.add(new Track("Stupid Love", "Lady Gaga", "Daily Left", null, R.drawable.stupid_love, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        ranTracks.add(new Track("Feel Me", "Selena Gomez", "Daily Left", null, R.drawable.feel_me, Uri.parse("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3")));
        randomPlaylists.add(new Playlist("Daily Left", "Sia, J Balvin, Bad Bunny, Justin Bieber, Drake",
                Utils.convertToBitmap(R.drawable.daily_left), ranTracks));


    }

    @Override
    public ArrayList<Playlist> getMadeForYouPlaylists(Context context, String mToken) {
        return randomPlaylists;
    }

    @Override
    public ArrayList<Playlist> getRecentPlaylists(Context context, String mToken) {
        return recentPlaylists;
    }

    @Override
    public ArrayList<Playlist> getRandomPlaylists(Context context, String mToken) {
        return randomPlaylists;
    }

    @Override
    public ArrayList<Playlist> getPopularPlaylists(Context context, String mToken) {
        return popularPlaylists;
    }

    @Override
    public boolean logIn(Context context, String username, String password, boolean mType) {
        int userIndex = -1;
        if (mType) {
            for (int i = 0; i < listenerArrayList.size(); i++) {
                if (username.equals(listenerArrayList.get(i).getmEmail()) &&
                        password.equals(listenerArrayList.get(i).getmPassword())) {
                    userIndex = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < artistArrayList.size(); i++) {
                if (username.equals(artistArrayList.get(i).getmEmail()) &&
                        password.equals(artistArrayList.get(i).getmPassword())) {
                    userIndex = i;
                    break;
                }
            }
        }

        if (userIndex == -1)
            return false;


        if (!mType) {
            Constants.mToken = "token2";

            ArrayList<Artist> followed = new ArrayList<>();
            for (int i = 5; i < 10; i++) {
                followed.add(artists.get(i));
            }

            Constants.user = new User(artistArrayList.get(userIndex).getmEmail(), false
                    , Utils.convertToBitmap(R.drawable.download)
                    , "Islam Ahmed", "1998/24/11", "male", true
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                    , followed, albums, new ArrayList<Track>());
        } else {
            Constants.mToken = "token1";

            ArrayList<Artist> followed = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                followed.add(artists.get(i));
            }

            Constants.user = new User(listenerArrayList.get(userIndex).getmEmail(), true
                    , Utils.convertToBitmap(R.drawable.download)
                    , "Hossam Alaa", "1999/04/06", "male", true
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                    , followed, albums, new ArrayList<Track>());
        }
        return true;
    }


    @Override
    public boolean signUp(Context context, boolean mType, String email, String password,
                          String DOB, String gender, String name) {

        Constants.mToken = "newToken";
        Constants.user = new User(email, mType, Utils.convertToBitmap(R.drawable.download)
                , name, DOB, gender, false, 0, 0, new ArrayList<User>()
                , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                , new ArrayList<Artist>(), new ArrayList<Album>(), new ArrayList<Track>());
        Constants.user.setmPassword(password);
        return true;
    }

    @Override
    public boolean checkEmailAvailability(Context context, String email, boolean mType) {
        if (mType) {
            for (int i = 0; i < listenerArrayList.size(); i++)
                if (email.equals(listenerArrayList.get(i).getmEmail()))
                    return false;
        } else {
            for (int i = 0; i < artistArrayList.size(); i++)
                if (email.equals(artistArrayList.get(i).getmEmail()))
                    return false;
        }
        return true;
    }


    @Override
    public ArrayList<Container> getResentResult(Context context) {
        recentSearches = new ArrayList<>();
        recentSearches.add(new Container("Quran", context.getResources().getString(R.string.Playlist), R.drawable.images));
        recentSearches.add(new Container("George Wassouf", "Artist", R.drawable.download));
        recentSearches.add(new Container("Get Weird", context.getResources().getString(R.string.Album)+".Little Mix", R.drawable.download1));
        recentSearches.add(new Container("Godzilla",context.getResources().getString(R.string.Song)+ ".Eminem juice WRLD", R.drawable.images));
        recentSearches.add(new Container("Lollipop", context.getResources().getString(R.string.Album)+".Little Mix", R.drawable.images3));
        recentSearches.add(new Container("Friction", context.getResources().getString(R.string.Playlist), R.drawable.images));
        recentSearches.add(new Container("Playlist", context.getResources().getString(R.string.Playlist), R.drawable.alan));
        recentSearches.add(new Container("Miley Cyrus", context.getResources().getString(R.string.Artist), R.drawable.download));

        return recentSearches;
    }

    public ArrayList<Container> getAllResultsOfSearch(Context context, String searchWord) {
        String song=context.getResources().getString(R.string.Song);
        String artist=context.getResources().getString(R.string.Artist);
        String album=context.getResources().getString(R.string.Album);
        String playlist=context.getResources().getString(R.string.Playlist);
        String profile=context.getResources().getString(R.string.Profile);
        String genre=context.getResources().getString(R.string.Genre);

        data = new ArrayList<>();
        data.add(new Container("Quran", playlist, R.drawable.download));
        data.add(new Container("George Wassouf", artist, R.drawable.download));
        data.add(new Container("Get Weird", album+".Little Mix", R.drawable.download1));
        data.add(new Container("Godzilla", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Believer", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Natural", song+".Imagine Dragons", R.drawable.abu));
        data.add(new Container("Love Save Us Once", album+".Little Mix", R.drawable.download));
        data.add(new Container("Get Weird", album+".Little Mix", R.drawable.download1));
        data.add(new Container("Mozart", genre, R.drawable.images3));
        data.add(new Container("Get Weird", album+".Little Mix", R.drawable.images3));
        data.add(new Container("Mine", playlist, R.drawable.images));
        data.add(new Container("ALVXARO", playlist, R.drawable.adele));

        data.add(new Container("Amr Diab", artist, R.drawable.amr));
        data.add(new Container("Stop Noise", album+".Little Mix", R.drawable.download1));
        data.add(new Container("Beautiful Song", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Lose Yourself", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Whatever It Takes", song+".Imagine Dragons", R.drawable.tamer_ashour));
        data.add(new Container("Max Payne", album+".Little Mix", R.drawable.download));
        data.add(new Container("Rick And Morty", album+".Little Mix", R.drawable.download1));
        data.add(new Container("Rock", genre, R.drawable.images3));
        data.add(new Container("Elephant", album+".Little Mix", R.drawable.images3));
        data.add(new Container("End Of World", playlist, R.drawable.images));
        data.add(new Container("Crazy Love", playlist, R.drawable.angham));

        data.add(new Container("Van Diesel", artist, R.drawable.download));
        data.add(new Container("Wind", album+".Little Mix", R.drawable.download1));
        data.add(new Container("I Will Survive", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Beautiful Mind", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Sucker For Pain", song+".Imagine Dragons", R.drawable.billie));
        data.add(new Container("Pain", album+".Little Mix", R.drawable.download));
        data.add(new Container("Quite Place", album+".Little Mix", R.drawable.download1));
        data.add(new Container("Sleep", genre, R.drawable.images3));
        data.add(new Container("Gamal", album+".Little Mix", R.drawable.images3));
        data.add(new Container("Nerds", playlist, R.drawable.images));
        data.add(new Container("Smoke Grenades", playlist, R.drawable.jannat));

        data.add(new Container("Samir Abo Elnil", artist, R.drawable.download));
        data.add(new Container("Sun Rises", album+".Little Mix", R.drawable.download1));
        data.add(new Container("Cat Sound", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Billie Jean", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Thunder", song+".Imagine Dragons", R.drawable.loai));
        data.add(new Container("The Shadows", album+".Little Mix", R.drawable.download));
        data.add(new Container("Silence Of Lambs", album+".Little Mix", R.drawable.download1));
        data.add(new Container("Afro", genre, R.drawable.images3));
        data.add(new Container("Fancy", album+".Little Mix", R.drawable.images3));
        data.add(new Container("Jungles", playlist, R.drawable.images));
        data.add(new Container("Oranges", playlist, R.drawable.hamza));

        data.add(new Container("Adele", artist, R.drawable.adele));
        data.add(new Container("Yellow Album", album+".Little Mix", R.drawable.download1));
        data.add(new Container("Stressed Out", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("ThunderClouds", song+".Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Thunder", song+".Imagine Dragons", R.drawable.halsey));
        data.add(new Container("High Waves", "Album.Little Mix", R.drawable.download));
        data.add(new Container("Nice Album", album+".Little Mix", R.drawable.download1));
        data.add(new Container("Pop", genre, R.drawable.images3));
        data.add(new Container("Lollipop", album+".Little Mix", R.drawable.images3));
        data.add(new Container("Friction", playlist, R.drawable.images));
        data.add(new Container("Playlist", playlist, R.drawable.wael));
        data.add(new Container("Miley Cyrus", artist, R.drawable.download));

        data.add((new Container("my profile", profile, R.drawable.assala)));
        data.add((new Container("mohammed ahmed", profile, R.drawable.cairokee)));
        data.add((new Container("ali saad", profile, R.drawable.samira)));


        LinkedList<Container> resultsLinked = new LinkedList<>();
        ArrayList<Container> results = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCat_Name().startsWith(searchWord))
                resultsLinked.addFirst(data.get(i));
            else if (data.get(i).getCat_Name().contains(searchWord))
                resultsLinked.addLast(data.get(i));
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
        recentSearches.remove(position);
    }

    @Override
    public void removeAllRecentSearches(Context context) {

        recentSearches = new ArrayList<Container>();
    }

    @Override
    public ArrayList<Container> getAllPopularPlaylists(Context context) {
        ArrayList<Container> playlists=new ArrayList<>();
        playlists.add(new Container("greate playlist","2,700 "+context.getResources().getString(R.string.Followers),R.drawable.adele));
        playlists.add(new Container("Amr Diab","5,200 "+context.getResources().getString(R.string.Followers),R.drawable.amr));
        playlists.add(new Container("beautiful","3,300 "+context.getResources().getString(R.string.Followers),R.drawable.imagine));
        playlists.add(new Container("simple","800 "+context.getResources().getString(R.string.Followers),R.drawable.alan));
        playlists.add(new Container("nice songs","1200 "+context.getResources().getString(R.string.Followers),R.drawable.ed));
        playlists.add(new Container("araby","3000 "+context.getResources().getString(R.string.Followers),R.drawable.assala));
        playlists.add(new Container("Bahaa Sultan","2,100 "+context.getResources().getString(R.string.Followers),R.drawable.bahaa));
        playlists.add(new Container("anghami","1,100 "+context.getResources().getString(R.string.Followers),R.drawable.angham));
        playlists.add(new Container("Thunder", "2100 "+context.getResources().getString(R.string.Followers), R.drawable.halsey));
        playlists.add(new Container("selena","2800 "+context.getResources().getString(R.string.Followers),R.drawable.selena));
        playlists.add(new Container("Smoke Grenades", "500 "+context.getResources().getString(R.string.Followers), R.drawable.jannat));
        playlists.add(new Container("Playlist", "26 "+context.getResources().getString(R.string.Followers), R.drawable.wael));
        playlists.add(new Container("3 d2at", "773 "+context.getResources().getString(R.string.Followers), R.drawable.abu));
        playlists.add(new Container("Oranges", "470 "+context.getResources().getString(R.string.Followers), R.drawable.hamza));

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
        for (Artist artist: artists) {
            if(artist.getId().equals(id))
                return artist;
        }

        return null;
    }

    @Override
    public ArrayList<Artist> getArtistRelatedArtists(Context context, String id) {
        ArrayList<Artist> related;
        if (id.equals("1") || id.equals("6"))
            related = new ArrayList<>(artists.subList(10, 16));
        else if (id.equals("2") || id.equals("7"))
            related = new ArrayList<>(artists.subList(16, 22));
        else if (id.equals("3") || id.equals("8"))
            related = new ArrayList<>(artists.subList(22, 28));
        else if (id.equals("4") || id.equals("9"))
            related = new ArrayList<>(artists.subList(28, 34));
        else if (id.equals("5") || id.equals("10"))
            related = new ArrayList<>(artists.subList(34, 40));
        else
            related = new ArrayList<>();
        return related;
    }

    @Override
    public ArrayList<Artist> getFollowedArtists(Boolean type, String mToken, int limit) {
        ArrayList<Artist> followedArtists = Constants.user.getFollowingArtists();
        ArrayList<Artist> returnedArtists = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, followedArtists.size()); i++) {
            returnedArtists.add(followedArtists.get(i));
        }

        return returnedArtists;
    }

    @Override
    public void followArtistOrUser(Boolean type, String mToken, String id) {
        for (Artist artist : artists) {
            if (artist.getId().equals(id)) {
                if (!isFollowing(type, mToken, id))
                    Constants.user.followArtist(artist);
                return;
            }
        }
    }

    @Override
    public void unFollowArtistOrUser(Boolean type, String mToken, String id) {
        for (Artist artist : artists) {
            if (artist.getId().equals(id)) {
                if (isFollowing(type, mToken, id))
                    Constants.user.unFollowArtist(artist);
                return;
            }
        }
    }

    @Override
    public Boolean isFollowing(Boolean type, String mToken, String id) {
        ArrayList<Artist> mFollowingArtists = Constants.user.getFollowingArtists();
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
                Artist artist = artists.get(i);
                mRecommendedArtists.add(artist);
            }
        } else {
            for (int i = 0; i < Math.min(5, limit); i++) {
                Artist artist = artists.get(i);
                mRecommendedArtists.add(artist);
            }
        }
        return mRecommendedArtists;
    }

    @Override
    public ArrayList<Artist> searchArtist(Context context, String q, int offset, int limit) {
        ArrayList<Artist> found = new ArrayList<>();
        for (Artist artist : artists) {
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
        ArrayList<Album> savedAlbums = Constants.user.getSavedAlbums();
        ArrayList<Album> returnedAlbums = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, savedAlbums.size()); i++) {
            returnedAlbums.add(savedAlbums.get(i));
        }

        return returnedAlbums;
    }
}
