package com.example.symphonia.Utils;

public class Artist {
    private int mImageResourceId;
    private String mArtistName;

    public Artist(int mImageResourceId, String mArtistName) {
        this.mImageResourceId = mImageResourceId;
        this.mArtistName = mArtistName;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public void setImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }
}
