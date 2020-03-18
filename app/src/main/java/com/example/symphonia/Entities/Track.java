package com.example.symphonia.Entities;

import java.io.Serializable;

public class Track implements Serializable {
    //TODO complete track class
    private String mTitle;
    private String mDescription;
    private String PlaylistName;
    private String mAlbum;
    private String mArtist;
    private String mURL;
    private boolean isHidden;
    private boolean isLiked;
    private int mImageResources;
    private int mDuration;

    //TODO delete this constructor; made for testing and not real testing
    public Track(String mTitle, String mArtist, String playlistName, String mAlbum, int mImageResources) {
        this.mTitle = mTitle;
        this.mArtist = mArtist;
        this.mAlbum = mAlbum;
        this.PlaylistName = playlistName;
        this.mImageResources = mImageResources;
    }

    public void setmAlbum(String mAlbum) {
        this.mAlbum = mAlbum;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }

    public String getmAlbum() {
        return mAlbum;
    }

    public String getmArtist() {
        return mArtist;
    }

    public Track(String mTitle, String mDescription, String playlistName, String mURL, boolean isHidden, boolean isLiked, int mImageResources, int mDuration) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        PlaylistName = playlistName;
        this.mURL = mURL;
        this.isHidden = isHidden;
        this.isLiked = isLiked;
        this.mImageResources = mImageResources;
        this.mDuration = mDuration;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getPlaylistName() {
        return PlaylistName;
    }

    public void setPlaylistName(String playlistName) {
        PlaylistName = playlistName;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getmImageResources() {
        return mImageResources;
    }

    public void setmImageResources(int mImageResources) {
        this.mImageResources = mImageResources;
    }

    public int getmDuration() {
        return mDuration;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }
}
