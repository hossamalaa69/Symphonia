package com.example.symphonia.Utils;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Playlist {
    private String mPlaylistTitle;

    private String mPlaylistDescription;
    private Bitmap mPlaylistImage;
    private ArrayList<Track> tracks;

    public Playlist(String mPlaylistTitle, String mPlaylistDescription, Bitmap mPlaylistImage, ArrayList<Track> tracks) {
        this.mPlaylistTitle = mPlaylistTitle;
        this.mPlaylistDescription = mPlaylistDescription;
        this.mPlaylistImage = mPlaylistImage;
        this.tracks = tracks;
    }

    public String getmPlaylistTitle() {
        return mPlaylistTitle;
    }

    public void setmPlaylistTitle(String mPlaylistTitle) {
        this.mPlaylistTitle = mPlaylistTitle;
    }

    public String getmPlaylistDescription() {
        return mPlaylistDescription;
    }

    public void setmPlaylistDescription(String mPlaylistDescription) {
        this.mPlaylistDescription = mPlaylistDescription;
    }

    public Bitmap getmPlaylistImage() {
        return mPlaylistImage;
    }

    public void setmPlaylistImage(Bitmap mPlaylistImage) {
        this.mPlaylistImage = mPlaylistImage;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
}
