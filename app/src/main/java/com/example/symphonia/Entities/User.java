package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class User {

    private String mEmail;

    private String mPassword;
    // true for listener, false for artist
    private boolean mType;

    private Bitmap mUserImage;

    private String mName;

    private String mDOB;

    private String mGender;

    private boolean mIsPremuim;

    private int mNumOfFollowers;

    private int mNumOfFollowings;

    private ArrayList<User> mFollowings;

    private ArrayList<User> mFollowers;

    private ArrayList<Playlist> mFavPlaylists;

    private ArrayList<Playlist> mMadePlaylists;

    private ArrayList<Artist> mFollowingArtists;

    private ArrayList<Track> mPlayedTracks;

    private ArrayList<Album> mSavedAlbums;

    public User(String mName, boolean mType) {
        this.mName = mName;
        this.mType = mType;
    }

    public User(String email, String password, boolean mType){
        this.mEmail=email;
        this.mPassword=password;
        this.mType=mType;
    }

    public User(String mEmail, boolean mType, Bitmap mUserImage, String mName, String mDOB
            , String mGender, boolean mIsPremuim, int mNumOfFollowers, int mNumOfFollowings
            , ArrayList<User> mFollowings, ArrayList<User> mFollowers
            , ArrayList<Playlist> mFavPlaylists, ArrayList<Playlist> mMadePlaylists
            , ArrayList<Artist> mFollowingArtists, ArrayList<Album> mSavedAlbums , ArrayList<Track> mPlayedTracks) {

        this.mEmail = mEmail;
        this.mType = mType;
        this.mUserImage = mUserImage;
        this.mName = mName;
        this.mDOB = mDOB;
        this.mGender = mGender;
        this.mIsPremuim = mIsPremuim;
        this.mNumOfFollowers = mNumOfFollowers;
        this.mNumOfFollowings = mNumOfFollowings;
        this.mFollowings = mFollowings;
        this.mFollowers = mFollowers;
        this.mFavPlaylists = mFavPlaylists;
        this.mMadePlaylists = mMadePlaylists;
        this.mFollowingArtists = mFollowingArtists;
        this.mPlayedTracks = mPlayedTracks;
        this.mSavedAlbums = mSavedAlbums;
    }

    public ArrayList<Album> getSavedAlbums() {
        return mSavedAlbums;
    }

    public void setSavedAlbums(ArrayList<Album> mSavedAlbums) {
        this.mSavedAlbums = mSavedAlbums;
    }

    public String getmPassword() {return mPassword;}

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public Bitmap getUserImage() {
        return mUserImage;
    }

    public void setUserImage(Bitmap userImage) {
        this.mUserImage = userImage;
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
        return mIsPremuim;
    }

    public void setPremuim(boolean premuim) {
        mIsPremuim = premuim;
    }

    public int getNumOfFollowers() {
        return mNumOfFollowers;
    }

    public void setNumOfFollowers(int numOfFollowers) {
        this.mNumOfFollowers = numOfFollowers;
    }

    public int getNumOfFollowings() {
        return mNumOfFollowings;
    }

    public void setNumOfFollowings(int numOfFollowings) {
        this.mNumOfFollowings = numOfFollowings;
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
        return mFavPlaylists;
    }

    public void setFavPlaylists(ArrayList<Playlist> favPlaylists) {
        this.mFavPlaylists = favPlaylists;
    }

    public ArrayList<Playlist> getMadePlaylists() {
        return mMadePlaylists;
    }

    public void setMadePlaylists(ArrayList<Playlist> madePlaylists) {
        this.mMadePlaylists = madePlaylists;
    }

    public ArrayList<Artist> getFollowingArtists() {
        return mFollowingArtists;
    }

    public void setmFollowingArtists(ArrayList<Artist> mFollowingArtists) {
        this.mFollowingArtists = mFollowingArtists;
    }

    public void followArtist(Artist artist)
    {
        mFollowingArtists.add(artist);
    }

    public void unFollowArtist(Artist artist)
    {
        mFollowingArtists.remove(artist);
    }


    public ArrayList<Track> getmPlayedTracks() {
        return mPlayedTracks;
    }

    public void setmPlayedTracks(ArrayList<Track> mPlayedTracks) {
        this.mPlayedTracks = mPlayedTracks;
    }

    public boolean isListenerType() {
        return mType;
    }

    public void setType(boolean mType) {
        this.mType = mType;
    }

}
