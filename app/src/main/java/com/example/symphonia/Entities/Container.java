package com.example.symphonia.Entities;

/**
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class Container {
    private String catName;
    private String catName2;
    private int imgRes;

    public Container(String s, int i) {
        catName = s;
        imgRes = i;
        catName2 = null;
    }

    public Container(String s, String s2, int i) {
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

    public int getImg_Res() {
        return imgRes;
    }
}
