package com.example.symphonia.Activities.User_Management.Notifications;

public class NotificationItem {

    private int mImageResource;
    private String mText1;
    private String mText2;
    private String mText3 = "";
    private String mSenderID ="";
    private String imageUrl = "";

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public NotificationItem(int imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public NotificationItem(int mImageResource, String mText1, String mText2, String mText3) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.mText3 = mText3;
    }

    public void setSenderID(String senderID){this.mSenderID = senderID;}

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public String getSenderID(){return mSenderID;}

    public String getText3() {
        return mText3;
    }

    public void setText3(String mText3) {
        this.mText3 = mText3;
    }
}
