package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Entities.User;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class MockService implements APIs {

    private ArrayList<Playlist> randomPlaylists;
    private ArrayList<Playlist> recentPlaylists;
    private ArrayList<Artist> artists;
    private ArrayList<Container> data;
    private ArrayList<Container> recentSearches;
    private ArrayList<Playlist> popularPlaylists;

    public MockService() {
        data = new ArrayList<>();
        data.add(new Container("Quran", "Playlist", R.drawable.selena));
        data.add(new Container("George Wassouf", "Artist", R.drawable.download));
        data.add(new Container("Get Weird", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("Godzilla", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Believer", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Natural", "Song.Imagine Dragons", R.drawable.abu));
        data.add(new Container("Love Save Us Once", "Album.Little Mix", R.drawable.download));
        data.add(new Container("Get Weird", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("Mozart", "Genre", R.drawable.images3));
        data.add(new Container("Get Weird", "Album.Little Mix", R.drawable.images3));
        data.add(new Container("Mine", "Playlist", R.drawable.images));
        data.add(new Container("ALVXARO", "Playlist", R.drawable.adele));

        data.add(new Container("Amr Diab", "Artist", R.drawable.amr));
        data.add(new Container("Stop Noise", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("Beautiful Song", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Lose Yourself", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Whatever It Takes", "Song.Imagine Dragons", R.drawable.tamer_ashour));
        data.add(new Container("Max Payne", "Album.Little Mix", R.drawable.download));
        data.add(new Container("Rick And Morty", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("Rock", "Genre", R.drawable.images3));
        data.add(new Container("Elephant", "Album.Little Mix", R.drawable.images3));
        data.add(new Container("End Of World", "Playlist", R.drawable.images));
        data.add(new Container("Crazy Love", "Playlist", R.drawable.angham));

        data.add(new Container("Van Diesel", "Artist", R.drawable.download));
        data.add(new Container("Wind", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("I Will Survive", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Beautiful Mind", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Sucker For Pain", "Song.Imagine Dragons", R.drawable.billie));
        data.add(new Container("Pain", "Album.Little Mix", R.drawable.download));
        data.add(new Container("Quite Place", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("Sleep", "Genre", R.drawable.images3));
        data.add(new Container("Gamal", "Album.Little Mix", R.drawable.images3));
        data.add(new Container("Nerds", "Playlist", R.drawable.images));
        data.add(new Container("Smoke Grenades", "Playlist", R.drawable.jannat));

        data.add(new Container("Samir Abo Elnil", "Artist", R.drawable.download));
        data.add(new Container("Sun Rises", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("Cat Sound", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Billie Jean", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Thunder", "Song.Imagine Dragons", R.drawable.loai));
        data.add(new Container("The Shadows", "Album.Little Mix", R.drawable.download));
        data.add(new Container("Silence Of Lambs", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("Afro", "Genre", R.drawable.images3));
        data.add(new Container("Fancy", "Album.Little Mix", R.drawable.images3));
        data.add(new Container("Jungles", "Playlist", R.drawable.images));
        data.add(new Container("Oranges", "Playlist", R.drawable.hamza));

        data.add(new Container("Adele", "Artist", R.drawable.adele));
        data.add(new Container("Yellow Album", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("Stressed Out", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("ThunderClouds", "Song.Eminem juice WRLD", R.drawable.images));
        data.add(new Container("Thunder", "Song.Imagine Dragons", R.drawable.halsey));
        data.add(new Container("High Waves", "Album.Little Mix", R.drawable.download));
        data.add(new Container("Nice Album", "Album.Little Mix", R.drawable.download1));
        data.add(new Container("Pop", "Genre", R.drawable.images3));
        data.add(new Container("Lollipop", "Album.Little Mix", R.drawable.images3));
        data.add(new Container("Friction", "Playlist", R.drawable.images));
        data.add(new Container("Playlist", "Playlist", R.drawable.wael));
        data.add(new Container("Miley Cyrus", "Artist", R.drawable.download));

        data.add((new Container("my profile", "Profile", R.drawable.assala)));
        data.add((new Container("mohammed ahmed", "Profile", R.drawable.cairokee)));
        data.add((new Container("ali saad", "Profile", R.drawable.samira)));


        recentSearches = new ArrayList<>();
        recentSearches.add(new Container("Quran", "Playlist", R.drawable.images));
        recentSearches.add(new Container("George Wassouf", "Artist", R.drawable.download));
        recentSearches.add(new Container("Get Weird", "Album.Little Mix", R.drawable.download1));
        recentSearches.add(new Container("Godzilla", "Song.Eminem juice WRLD", R.drawable.images));
        recentSearches.add(new Container("Lollipop", "Album.Little Mix", R.drawable.images3));
        recentSearches.add(new Container("Friction", "Playlist", R.drawable.images));
        recentSearches.add(new Container("Playlist", "Playlist", R.drawable.alan));
        recentSearches.add(new Container("Miley Cyrus", "Artist", R.drawable.download));

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


        popularPlaylists = new ArrayList<>();
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("Rescue Me", "OneRepublic", "mood booster", "Rescue Me", R.drawable.rescue_me));
        tracks.add(new Track("Freaking Me Out", "Ava Max", "mood booster", null, R.drawable.freaking_me_out));
        tracks.add(new Track("You Can't Stop The Girl", "Bebe Rexha", "mood booster", null, R.drawable.you_cant_stop_the_girl));
        popularPlaylists.add(new Playlist("mood booster", "Get happy with this pick-up playlist full of current feel-good songs",
                Utils.convertToBitmap(R.drawable.mood_booster), tracks));

        recentPlaylists = new ArrayList<>();
        ArrayList<Track> rTracks = new ArrayList<Track>();
        rTracks.add(new Track("Little Do You Know", "Alex & Sierra", "Rewind-the sound of 2014", null, R.drawable.little_do_you_know));
        rTracks.add(new Track("Wildest Dreams", "Taylor Swift", "Rewind-the sound of 2014", null, R.drawable.wildest_dreams));
        rTracks.add(new Track("One Last Time", "Ariana Grande", "Rewind-the sound of 2014", null, R.drawable.one_last_time));
        recentPlaylists.add(new Playlist("Rewind-the sound of 2014", null,
                Utils.convertToBitmap(R.drawable.rewind_the_sound), rTracks));

        randomPlaylists = new ArrayList<>();
        ArrayList<Track> ranTracks = new ArrayList<Track>();
        ranTracks.add(new Track("Intentions", "Justing Bieber, Quavo", "Daily Left", null, R.drawable.intentions));
        ranTracks.add(new Track("Stupid Love", "Lady Gaga", "Daily Left", null, R.drawable.stupid_love));
        ranTracks.add(new Track("Feel Me", "Selena Gomez", "Daily Left", null, R.drawable.feel_me));
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

        if ((username.equals("artist1") || username.equals("artist@symphonia.com"))
                && password.equals("12345678") && !mType) {
            Constants.mToken = "token2";

            ArrayList<Artist> followed = new ArrayList<>();
            for (int i = 5; i < 10; i++) {
                followed.add(artists.get(i));
            }

            Constants.user = new User(username, mType, Utils.convertToBitmap(R.drawable.download)
                    , "Islam Ahmed", "1998/24/11", "male", true
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                    , followed, new ArrayList<Track>());
            return true;
        } else if ((username.equals("user1") || username.equals("user@symphonia.com"))
                && password.equals("12345678") && mType) {
            Constants.mToken = "token1";

            ArrayList<Artist> followed = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                followed.add(artists.get(i));
            }

            Constants.user = new User(username, mType, Utils.convertToBitmap(R.drawable.download)
                    , "Islam Ahmed", "1998/24/11", "male", true
                    , 65500, 40, new ArrayList<User>()
                    , new ArrayList<User>(), new ArrayList<Playlist>(), new ArrayList<Playlist>()
                    , followed, new ArrayList<Track>());
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Container> getResentResult(Context context) {
        return recentSearches;
    }

    public ArrayList<Container> getAllResultsOfSearch(Context context, String searchWord) {
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
        categories.add(new Container("New Releases", R.drawable.download));
        categories.add(new Container("Charts", R.drawable.download1));
        categories.add(new Container("Concerts", R.drawable.images3));
        categories.add(new Container("Made for you", R.drawable.images));
        categories.add(new Container("Arabic", R.drawable.images2));
        categories.add(new Container("Mood", R.drawable.download));
        categories.add(new Container("Decades", R.drawable.download1));
        categories.add(new Container("Gaming", R.drawable.images2));
        categories.add(new Container("Workout", R.drawable.images3));
        categories.add(new Container("Focus", R.drawable.images));
        categories.add(new Container("Party", R.drawable.download));
        categories.add(new Container("Dinner", R.drawable.download1));
        categories.add(new Container("Jazz", R.drawable.images));
        categories.add(new Container("Soul", R.drawable.images2));
        categories.add(new Container("Kids", R.drawable.images3));
        categories.add(new Container("Blues", R.drawable.download));
        categories.add(new Container("Punk", R.drawable.download1));
        categories.add(new Container("Travel", R.drawable.images3));
        return categories;
    }

    @Override
    public ArrayList<Container> getGenres(Context context) {
        ArrayList<Container> genres = new ArrayList<>();
        genres.add(new Container("Pop", R.drawable.download));
        genres.add(new Container("Hip-Hop", R.drawable.download1));
        genres.add(new Container("Rock", R.drawable.images2));
        genres.add(new Container("Sleep", R.drawable.images));
        genres.add(new Container("Chill", R.drawable.images3));
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
        recentSearches.remove(position);
    }

    @Override
    public void removeAllRecentSearches(Context context) {

        recentSearches = new ArrayList<Container>();
    }

    @Override
    public ArrayList<Artist> getArtistRelatedArtists(Context context, String id) {
        ArrayList<Artist> related;
        if(id.equals("1") || id.equals("6"))
            related = new ArrayList<>(artists.subList(10, 16));
        else if(id.equals("2") || id.equals("7"))
            related = new ArrayList<>(artists.subList(16, 22));
        else if(id.equals("3") || id.equals("8"))
            related = new ArrayList<>(artists.subList(22, 28));
        else if(id.equals("4") || id.equals("9"))
            related = new ArrayList<>(artists.subList(28, 34));
        else if(id.equals("5") || id.equals("10"))
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
}
