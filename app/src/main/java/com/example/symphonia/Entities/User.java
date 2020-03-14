package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class User {

    private String mEmail;

    // true for listener, Flase for artist
    private boolean mType;

    private Bitmap userImage;

    private String mName;

    private String mDOB;

    private String mGender;

    private boolean isPremuim;

    private int numOfFollowers;

    private int numOfFollowings;

    private ArrayList<User> mFollowings;

    private ArrayList<User> mFollowers;

    private ArrayList<Playlist> favPlaylists;

    private ArrayList<Playlist> madePlaylists;

    private ArrayList<Artist> mFollowingArtists;

    private ArrayList<Track> mPlayedTracks;

    public User(String mName, boolean mType) {
        this.mName = mName;
        this.mType = mType;
    }

    public boolean isListenerType() {
        return mType;
    }

    public void setType(boolean mType) {
        this.mType = mType;
    }

    public User(String mEmail, boolean mType, Bitmap userImage, String mName, String mDOB
            , String mGender, boolean isPremuim, int numOfFollowers, int numOfFollowings
            , ArrayList<User> mFollowings, ArrayList<User> mFollowers
            , ArrayList<Playlist> favPlaylists, ArrayList<Playlist> madePlaylists
            , ArrayList<Artist> mFollowingArtists, ArrayList<Track> mPlayedTracks) {

        this.mEmail = mEmail;
        this.mType = mType;
        this.userImage = userImage;
        this.mName = mName;
        this.mDOB = mDOB;
        this.mGender = mGender;
        this.isPremuim = isPremuim;
        this.numOfFollowers = numOfFollowers;
        this.numOfFollowings = numOfFollowings;
        this.mFollowings = mFollowings;
        this.mFollowers = mFollowers;
        this.favPlaylists = favPlaylists;
        this.madePlaylists = madePlaylists;
        this.mFollowingArtists = mFollowingArtists;
        this.mPlayedTracks = mPlayedTracks;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDOB() {
        return mDOB;
    }

    public void setmDOB(String mDOB) {
        this.mDOB = mDOB;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public boolean isPremuim() {
        return isPremuim;
    }

    public void setPremuim(boolean premuim) {
        isPremuim = premuim;
    }

    public int getNumOfFollowers() {
        return numOfFollowers;
    }

    public void setNumOfFollowers(int numOfFollowers) {
        this.numOfFollowers = numOfFollowers;
    }

    public int getNumOfFollowings() {
        return numOfFollowings;
    }

    public void setNumOfFollowings(int numOfFollowings) {
        this.numOfFollowings = numOfFollowings;
    }

    public ArrayList<User> getmFollowings() {
        return mFollowings;
    }

    public void setmFollowings(ArrayList<User> mFollowings) {
        this.mFollowings = mFollowings;
    }

    public ArrayList<User> getmFollowers() {
        return mFollowers;
    }

    public void setmFollowers(ArrayList<User> mFollowers) {
        this.mFollowers = mFollowers;
    }

    public ArrayList<Playlist> getFavPlaylists() {
        return favPlaylists;
    }

    public void setFavPlaylists(ArrayList<Playlist> favPlaylists) {
        this.favPlaylists = favPlaylists;
    }

    public ArrayList<Playlist> getMadePlaylists() {
        return madePlaylists;
    }

    public void setMadePlaylists(ArrayList<Playlist> madePlaylists) {
        this.madePlaylists = madePlaylists;
    }

    public ArrayList<Artist> getmFollowingArtists() {
        return mFollowingArtists;
    }

    public void setmFollowingArtists(ArrayList<Artist> mFollowingArtists) {
        this.mFollowingArtists = mFollowingArtists;
    }

    public ArrayList<Track> getmPlayedTracks() {
        return mPlayedTracks;
    }

    public void setmPlayedTracks(ArrayList<Track> mPlayedTracks) {
        this.mPlayedTracks = mPlayedTracks;
    }
}
