package com.example.symphonia.Entities;

import android.net.Uri;

import java.io.Serializable;

/**
 * class that holds track info.
 *
 * @author Khaled Ali
 * @version 1.0
 * @since 22-3-2020
 */
public class Track implements Serializable {
    /**
     * title of tack
     */
    private String mTitle;
    /**
     * Description of tack
     */
    private String mDescription;

    /**
     * playlist  name  of tack
     */
    private String PlaylistName;
    /**
     * album name of tack
     */
    private String mAlbum;

    /**
     * artist name of tack
     */
    private String mArtist;
    /**
     * Uri of tack
     */
    private Uri uri;

    /**
     * indicates if track is hidden
     */
    private boolean isHidden;

    /**
     * @return Uri of track
     */
    public Uri getUri() {
        return uri;
    }

    /**
     * indicates if track is favourite
     */
    private boolean isLiked;
    /**
     * image resource of track
     */
    private int mImageResources;
    /**
     * Duration of Track
     */
    private int mDuration;

    /**
     * non empty constructor
     *
     * @param mTitle          title of track
     * @param playlistName    name of playlsit
     * @param mImageResources image resources of track
     * @param mArtist         artist name
     * @param mAlbum          album name
     * @param uri             Uri of track
     */
    public Track(String mTitle, String mArtist, String playlistName, String mAlbum, int mImageResources, Uri uri) {
        this.mTitle = mTitle;
        this.mArtist = mArtist;
        this.mAlbum = mAlbum;
        this.PlaylistName = playlistName;
        this.mImageResources = mImageResources;
        this.uri = uri;
    }

    /**
     * non empty constructor
     *
     * @param mTitle          title of track
     * @param mArtist         name of artist
     * @param playlistName    name of playlsit
     * @param mAlbum          name of album
     * @param mImageResources image resources of track
     */
    public Track(String mTitle, String mArtist, String playlistName, String mAlbum, int mImageResources) {
        this.mTitle = mTitle;
        this.mArtist = mArtist;
        this.mAlbum = mAlbum;
        this.PlaylistName = playlistName;
        this.mImageResources = mImageResources;
    }

    /**
     * non empty constructor
     *
     * @param mTitle          title of track
     * @param playlistName    name of playlsit
     * @param mImageResources image resources of track
     * @param mDescription    description of track
     * @param mURL            string value of the URL of track
     * @param isHidden        is track hidden
     * @param isLiked         is track favourite
     * @param mDuration       duration of track
     */
    public Track(String mTitle, String mDescription, String playlistName, String mURL, boolean isHidden, boolean isLiked, int mImageResources, int mDuration) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        PlaylistName = playlistName;
        this.isHidden = isHidden;
        this.isLiked = isLiked;
        this.mImageResources = mImageResources;
        this.mDuration = mDuration;
    }

    /**
     * getter for title
     *
     * @return title of track
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * getter for description
     *
     * @return description
     */
    public String getmDescription() {
        return mDescription;
    }

    /**
     * getter for playlist name
     *
     * @return playlist name
     */
    public String getPlaylistName() {
        return PlaylistName;
    }

    /**
     * getter for if track is hidden
     *
     * @return is hidden
     */
    public boolean isHidden() {
        return isHidden;
    }

    /**
     * setter for if track is hidden
     *
     * @param hidden if track is hidden
     */
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    /**
     * getter for if track is favourite
     *
     * @return if is favourite
     */
    public boolean isLiked() {
        return isLiked;
    }

    /**
     * setter for if track is favourite
     *
     * @param liked if track is favourite
     */
    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    /**
     * getter for track image
     *
     * @return track image resources
     */
    public int getmImageResources() {
        return mImageResources;
    }


}
