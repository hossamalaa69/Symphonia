package com.example.symphonia.Entities;

import android.graphics.Bitmap;

/**
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class Container {
    private String catName;
    private String catName2;
    private Bitmap imgRes;

    public Container(String s, Bitmap i) {
        catName = s;
        imgRes = i;
        catName2 = null;
    }

    public Container(String s, String s2, Bitmap i) {
        catName = s;
        imgRes = i;
        catName2 = s2;
    }
    public Container(String s) {
        catName = s;
    }

    public String getCat_Name2() {
        return catName2;
    }

    public String getCat_Name() {
        return catName;
    }

    public Bitmap getImg_Res() {
        return imgRes;
    }
}
