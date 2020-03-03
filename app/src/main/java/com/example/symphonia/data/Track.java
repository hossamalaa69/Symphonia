package com.example.symphonia.data;

public class Track {
    //TODO complete track class
    private String mTitle;
    private String mDescription;
    private String PlaylistName;
    private String mURL;
    private boolean isHidded;
    private boolean isLiked;
    private int mImageResources;
    private int mDuration;

    //TODO delete this constructor; made for testing and not real testing
    public Track(String mTitle, String mDescription, String playlistName, int mImageResources) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        PlaylistName = playlistName;
        this.mImageResources = mImageResources;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setPlaylistName(String playlistName) {
        PlaylistName = playlistName;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public void setHidded(boolean hidded) {
        isHidded = hidded;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void setmImageResources(int mImageResources) {
        this.mImageResources = mImageResources;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getPlaylistName() {
        return PlaylistName;
    }

    public String getmURL() {
        return mURL;
    }

    public boolean isHidded() {
        return isHidded;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public int getmImageResources() {
        return mImageResources;
    }

    public int getmDuration() {
        return mDuration;
    }

    public Track(String mTitle, String mDescription, String playlistName, String mURL, boolean isHidded, boolean isLiked, int mImageResources, int mDuration) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        PlaylistName = playlistName;
        this.mURL = mURL;
        this.isHidded = isHidded;
        this.isLiked = isLiked;
        this.mImageResources = mImageResources;
        this.mDuration = mDuration;
    }
}
