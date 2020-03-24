package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * class that holds playlist info.
 *
 * @author Khaled Ali
 * @version 1.0
 */
public class Playlist {
    /**
     * playlist name
     */
    private String mPlaylistTitle;
    /**
     * playlist description
     */
    private String mPlaylistDescription;
    /**
     * playlist image
     */
    private Bitmap mPlaylistImage;
    /**
     * ArrayList of playlist tracks
     */
    private ArrayList<Track> tracks;

    /**
     * non empty constructor
     *
     * @param mPlaylistTitle       playlist title
     * @param mPlaylistDescription playlist description
     * @param mPlaylistImage       playlist image
     * @param tracks               playlist tracks
     */
    public Playlist(String mPlaylistTitle, String mPlaylistDescription, Bitmap mPlaylistImage, ArrayList<Track> tracks) {
        this.mPlaylistTitle = mPlaylistTitle;
        this.mPlaylistDescription = mPlaylistDescription;
        this.mPlaylistImage = mPlaylistImage;
        this.tracks = tracks;
    }

    /**
     * getter for playlist title
     *
     * @return playlist title
     */
    public String getmPlaylistTitle() {
        return mPlaylistTitle;
    }

    /**
     * getter for playlist description
     *
     * @return playlist description
     */
    public String getmPlaylistDescription() {
        return mPlaylistDescription;
    }

    /**
     * getter for playlist image
     *
     * @return Bitmap of playlist image
     */
    public Bitmap getmPlaylistImage() {
        return mPlaylistImage;
    }

    /**
     * getter for playlist tracks
     *
     * @return ArrayList of playlist tracks
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }

    /**
     * setter for playlist tracks
     *
     * @param tracks playlsit tracks
     */
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
}
