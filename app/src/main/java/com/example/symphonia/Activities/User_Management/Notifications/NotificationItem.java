package com.example.symphonia.Activities.User_Management.Notifications;

public class NotificationItem {

    private int mImageResource;
    private String mText1;
    private String mText2;

    public NotificationItem(int imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}
