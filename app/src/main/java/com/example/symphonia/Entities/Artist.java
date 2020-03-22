package com.example.symphonia.Entities;

import android.graphics.Bitmap;


public class Artist {
    private Bitmap mImage;
    private String mArtistName;
    private String id;

    public Artist(String id, Bitmap mImage, String mArtistName) {
        this.id = id;
        this.mImage = mImage;
        this.mArtistName = mArtistName;
    }

    public Bitmap getImage(){
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    public String getId() {
        return id;
    }



}
