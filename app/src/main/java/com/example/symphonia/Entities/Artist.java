package com.example.symphonia.Entities;

import android.graphics.Bitmap;


/**
 * A class to hold the data of Artist object
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class Artist {
    /**
     * image of the artist
     */
    private Bitmap mArtistImage;
    /**
     * name of the artist
     */
    private String mArtistName;
    /**
     * artist's special identifier
     */
    private String mArtistId;

    /**
     * constructor for the artist object
     *
     * @param mArtistId   artist id
     * @param mImage      artist image
     * @param mArtistName artist name
     */
    public Artist(String mArtistId, Bitmap mImage, String mArtistName) {
        this.mArtistId = mArtistId;
        this.mArtistImage = mImage;
        this.mArtistName = mArtistName;
    }

    /**
     * @return artist image
     */
    public Bitmap getImage() {
        return mArtistImage;
    }

    /**
     * @param mImage artist image
     */
    public void setImage(Bitmap mImage) {
        this.mArtistImage = mImage;
    }

    /**
     * @return artist name
     */
    public String getArtistName() {
        return mArtistName;
    }

    /**
     * @param mArtistName artist name
     */
    public void setArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    /**
     * @return artist id
     */
    public String getId() {
        return mArtistId;
    }


}
