package com.example.symphonia.Utils;

public class Container {
    private String Cat_Name;
    private String Cat_Name2;
    private int Img_Res;

    public Container(String s, int i) {
        Cat_Name = s;
        Img_Res = i;
        Cat_Name2 = null;
    }

    public Container(String s, String s2, int i) {
        Cat_Name = s;
        Img_Res = i;
        Cat_Name2 = s2;
    }

    public String getCat_Name2() {
        return Cat_Name2;
    }

    public String getCat_Name() {
        return Cat_Name;
    }

    public int getImg_Res() {
        return Img_Res;
    }
}
