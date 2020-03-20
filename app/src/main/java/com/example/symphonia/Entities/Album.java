package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Album {
    private String mAlbumId;
    private String mAlbumType;
    private ArrayList<Artist> mAlbumArtists;
    private ArrayList<Copyright> mCopyrights;
    private Bitmap mAlbumImage;
    private String mAlbumName;
    private String mReleaseDate;
    private ArrayList<Track> mAlbumTracks;

    public Album(String mAlbumId, String mAlbumType, ArrayList<Artist> mAlbumArtists,
                 ArrayList<Copyright> mCopyrights, Bitmap mAlbumImage, String mAlbumName,
                 String mReleaseDate, ArrayList<Track> mAlbumTracks) {

        this.mAlbumId = mAlbumId;
        this.mAlbumType = mAlbumType;
        this.mAlbumArtists = mAlbumArtists;
        this.mCopyrights = mCopyrights;
        this.mAlbumImage = mAlbumImage;
        this.mAlbumName = mAlbumName;
        this.mReleaseDate = mReleaseDate;
        this.mAlbumTracks = mAlbumTracks;
    }

    public String getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(String mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public String getAlbumType() {
        return mAlbumType;
    }

    public void setAlbumType(String mAlbumType) {
        this.mAlbumType = mAlbumType;
    }

    public ArrayList<Artist> getAlbumArtists() {
        return mAlbumArtists;
    }

    public void setAlbumArtists(ArrayList<Artist> mAlbumArtists) {
        this.mAlbumArtists = mAlbumArtists;
    }

    public ArrayList<Copyright> getCopyrights() {
        return mCopyrights;
    }

    public void setCopyrights(ArrayList<Copyright> mCopyrights) {
        this.mCopyrights = mCopyrights;
    }

    public Bitmap getAlbumImage() {
        return mAlbumImage;
    }

    public void setAlbumImage(Bitmap mAlbumImage) {
        this.mAlbumImage = mAlbumImage;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(String mAlbumName) {
        this.mAlbumName = mAlbumName;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public ArrayList<Track> getAlbumTracks() {
        return mAlbumTracks;
    }

    public void setAlbumTracks(ArrayList<Track> mAlbumTracks) {
        this.mAlbumTracks = mAlbumTracks;
    }
}
