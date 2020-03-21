package com.example.symphonia.Entities;

public class Copyright {
     private String mCopyrightText;
     private String mType;

    public Copyright(String mCopyrightText, String mType) {
        this.mCopyrightText = mCopyrightText;
        this.mType = mType;
    }

    public String getCopyrightText() {
        return mCopyrightText;
    }

    public void setCopyrightText(String mCopyrightText) {
        this.mCopyrightText = mCopyrightText;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
