package com.example.symphonia.Entities;

import android.graphics.Bitmap;

import com.example.symphonia.Helpers.SerialBitmap;

import java.io.Serializable;


public class Artist implements Serializable {
    private SerialBitmap mImage;
    private String mArtistName;
    private String id;

    public Artist(String id, Bitmap mImage, String mArtistName) {
        this.id = id;
        this.mImage = new SerialBitmap(mImage);
        this.mArtistName = mArtistName;
    }

    public Bitmap getImage(){
        return mImage.getBitmap();
    }

    public void setImage(Bitmap mImage) {
        this.mImage.setBitmap(mImage);
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
