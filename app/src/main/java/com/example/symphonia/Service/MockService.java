package com.example.symphonia.Service;

import android.content.Context;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.User;
import com.example.symphonia.R;

import java.util.ArrayList;

public class MockService implements APIs {

    ArrayList<Container>data;
    ArrayList<Container>recentSearches;

    MockService(){
        data=new ArrayList<>();
        data.add(new Container("Quran","Playlist",R.drawable.images2));
        data.add(new Container("George Wassouf","Artist",R.drawable.download));
        data.add(new Container("Get Weird","Album.Little Mix",R.drawable.download1));
        data.add(new Container("Godzilla","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("Believer","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("Natural","Song.Imagine Dragons",R.drawable.images2));
        data.add(new Container("Love Save Us Once","Album.Little Mix",R.drawable.download));
        data.add(new Container("Get Weird","Album.Little Mix",R.drawable.download1));
        data.add(new Container("Mozart","Genre",R.drawable.images3));
        data.add(new Container("Get Weird","Album.Little Mix",R.drawable.images3));
        data.add(new Container("Mine","Playlist",R.drawable.images));
        data.add(new Container("ALVXARO","Playlist",R.drawable.images2));

        data.add(new Container("Amr Diab","Artist",R.drawable.download));
        data.add(new Container("Stop Noise","Album.Little Mix",R.drawable.download1));
        data.add(new Container("Beautiful Song","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("Lose Yourself","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("Whatever It Takes","Song.Imagine Dragons",R.drawable.images2));
        data.add(new Container("Max Payne","Album.Little Mix",R.drawable.download));
        data.add(new Container("Rick And Morty","Album.Little Mix",R.drawable.download1));
        data.add(new Container("Rock","Genre",R.drawable.images3));
        data.add(new Container("Elephant","Album.Little Mix",R.drawable.images3));
        data.add(new Container("End Of World","Playlist",R.drawable.images));
        data.add(new Container("Crazy Love","Playlist",R.drawable.images2));

        data.add(new Container("Van Diesel","Artist",R.drawable.download));
        data.add(new Container("Wind","Album.Little Mix",R.drawable.download1));
        data.add(new Container("I Will Survive","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("Beautiful Mind","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("Sucker For Pain","Song.Imagine Dragons",R.drawable.images2));
        data.add(new Container("Pain","Album.Little Mix",R.drawable.download));
        data.add(new Container("Quite Place","Album.Little Mix",R.drawable.download1));
        data.add(new Container("Sleep","Genre",R.drawable.images3));
        data.add(new Container("Gamal","Album.Little Mix",R.drawable.images3));
        data.add(new Container("Nerds","Playlist",R.drawable.images));
        data.add(new Container("Smoke Grenades","Playlist",R.drawable.images2));

        data.add(new Container("Samir Abo Elnil","Artist",R.drawable.download));
        data.add(new Container("Sun Rises","Album.Little Mix",R.drawable.download1));
        data.add(new Container("Cat Sound","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("Billie Jean","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("Thunder","Song.Imagine Dragons",R.drawable.images2));
        data.add(new Container("The Shadows","Album.Little Mix",R.drawable.download));
        data.add(new Container("Silence Of Lambs","Album.Little Mix",R.drawable.download1));
        data.add(new Container("Afro","Genre",R.drawable.images3));
        data.add(new Container("Fancy","Album.Little Mix",R.drawable.images3));
        data.add(new Container("Jungles","Playlist",R.drawable.images));
        data.add(new Container("Oranges","Playlist",R.drawable.images2));

        data.add(new Container("Adele","Artist",R.drawable.download));
        data.add(new Container("Yellow Album","Album.Little Mix",R.drawable.download1));
        data.add(new Container("Stressed Out","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("ThunderClouds","Song.Eminem juice WRLD",R.drawable.images));
        data.add(new Container("Thunder","Song.Imagine Dragons",R.drawable.images2));
        data.add(new Container("High Waves","Album.Little Mix",R.drawable.download));
        data.add(new Container("Nice Album","Album.Little Mix",R.drawable.download1));
        data.add(new Container("Pop","Genre",R.drawable.images3));
        data.add(new Container("Lollipop","Album.Little Mix",R.drawable.images3));
        data.add(new Container("Friction","Playlist",R.drawable.images));
        data.add(new Container("Playlist","Playlist",R.drawable.images2));
        data.add(new Container("Miley Cyrus","Artist",R.drawable.download));

        recentSearches=new ArrayList<>();
        recentSearches.add(new Container("Quran","Playlist",R.drawable.images2));
        recentSearches.add(new Container("George Wassouf","Artist",R.drawable.download));
        recentSearches.add(new Container("Get Weird","Album.Little Mix",R.drawable.download1));
        recentSearches.add(new Container("Godzilla","Song.Eminem juice WRLD",R.drawable.images));
        recentSearches.add(new Container("Lollipop","Album.Little Mix",R.drawable.images3));
        recentSearches.add(new Container("Friction","Playlist",R.drawable.images));
        recentSearches.add(new Container("Playlist","Playlist",R.drawable.images2));
        recentSearches.add(new Container("Miley Cyrus","Artist",R.drawable.download));

    }

    @Override
    public boolean logInAsListener(Context context, String username, String password) {

        if ( (username.equals("user1") || username.equals("user@symphonia.com")) && password.equals("12345678") ) {
            Constants.mToken = "token1";
            Constants.user=new User(username);
            return true;
        }
        return false;
    }

    @Override
    public boolean logInAsArtist(Context context, String username, String password) {
        if ( (username.equals("Artist1") || username.equals("artist@symphonia.com")) && password.equals("123456789") ) {
            Constants.mToken = "token2";
            Constants.user=new User(username);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Container> getResentResult(Context context) {
        return recentSearches;
    }

    public ArrayList<Container> getAllResultsOfSearch(Context context, String searchWord) {
        ArrayList<Container> results=new ArrayList<>();
        ArrayList<Container> results2=new ArrayList<>();
        for(int i=0;i<data.size();i++){
            if(data.get(i).getCat_Name().startsWith(searchWord))
                results.add(data.get(i));
            else if(data.get(i).getCat_Name().contains(searchWord))
                results2.add(data.get(i));
        }
        results.addAll(results2);
        return results;
    }

    @Override
    public ArrayList<Container> getResultsOfSearch(Context context, String searchWord) {
        ArrayList<Container> results=new ArrayList<>();
        ArrayList<Container> temp=new ArrayList<>();
        temp=getAllResultsOfSearch(context,searchWord);
        for(int i=0;i<7;i++){
            if(temp.get(i)!=null)
                results.add(temp.get(i));
        }
        return results;
    }

    @Override
    public ArrayList<Container> getCategories(Context context) {
        ArrayList<Container> categories=new ArrayList<>();
        categories.add(new Container("New Releases",R.drawable.download));
        categories.add(new Container("Charts",R.drawable.download1));
        categories.add(new Container("Concerts",R.drawable.images3));
        categories.add(new Container("Made for you",R.drawable.images));
        categories.add(new Container("Arabic",R.drawable.images2));
        categories.add(new Container("Mood",R.drawable.download));
        categories.add(new Container("Decades",R.drawable.download1));
        categories.add(new Container("Gaming",R.drawable.images2));
        categories.add(new Container("Workout",R.drawable.images3));
        categories.add(new Container("Focus",R.drawable.images));
        categories.add(new Container("Party",R.drawable.download));
        categories.add(new Container("Dinner",R.drawable.download1));
        categories.add(new Container("Jazz",R.drawable.images));
        categories.add(new Container("Soul",R.drawable.images2));
        categories.add(new Container("Kids",R.drawable.images3));
        categories.add(new Container("Blues",R.drawable.download));
        categories.add(new Container("Punk",R.drawable.download1));
        categories.add(new Container("Travel",R.drawable.images3));
        return categories;
    }

    @Override
    public ArrayList<Container> getGenres(Context context) {
        ArrayList<Container> genres=new ArrayList<>();
        genres.add(new Container("Pop", R.drawable.download));
        genres.add(new Container("Hip-Hop", R.drawable.download1));
        genres.add(new Container("Rock", R.drawable.images2));
        genres.add(new Container("Sleep", R.drawable.images));
        genres.add(new Container("Chill", R.drawable.images3));
        return genres;
    }

    @Override
    public ArrayList<Container> getArtists(Context context, String searchWord) {
        ArrayList<Container> temp=getAllResultsOfSearch(context,searchWord);
        ArrayList<Container> artists=new ArrayList<>();
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getCat_Name2().startsWith("Artist"))
                artists.add(temp.get(i));
        }
        return artists;
    }

    @Override
    public ArrayList<Container> getSongs(Context context, String searchWord) {
        ArrayList<Container> temp=getAllResultsOfSearch(context,searchWord);
        ArrayList<Container> songs=new ArrayList<>();
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getCat_Name2().startsWith("Song"))
                songs.add(temp.get(i));
        }
        return songs;
    }

    @Override
    public ArrayList<Container> getAlbums(Context context, String searchWord) {
        ArrayList<Container> temp=getAllResultsOfSearch(context,searchWord);
        ArrayList<Container> albums=new ArrayList<>();
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getCat_Name2().startsWith("Album"))
                albums.add(temp.get(i));
        }
        return albums;
    }

    @Override
    public ArrayList<Container> getGenresAndMoods(Context context, String searchWord) {
        ArrayList<Container> temp=getAllResultsOfSearch(context,searchWord);
        ArrayList<Container> genres=new ArrayList<>();
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getCat_Name2().startsWith("Artist"))
                genres.add(temp.get(i));
        }
        return genres;
    }

    @Override
    public ArrayList<Container> getPlaylists(Context context, String searchWord) {
        ArrayList<Container> temp=getAllResultsOfSearch(context,searchWord);
        ArrayList<Container> playlists=new ArrayList<>();
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getCat_Name2().startsWith("Playlist"))
                playlists.add(temp.get(i));
        }
        return playlists;
    }

    @Override
    public ArrayList<Container> getProfiles(Context context, String searchWord) {
        ArrayList<Container> temp=getAllResultsOfSearch(context,searchWord);
        ArrayList<Container> profiles=new ArrayList<>();
        for(int i=0;i<temp.size();i++){
            if(temp.get(i).getCat_Name2().startsWith("Profile"))
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

        recentSearches=new ArrayList<Container>();
    }
}
