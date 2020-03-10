package com.example.symphonia.data;

public class Container {
    private String Cat_Name;
    private int Img_Res;
    public Container(String s, int i){
        Cat_Name=s;
        Img_Res=i;
    }
    public String getCat_Name(){
        return Cat_Name;
    }
    public int getImg_Res(){
        return Img_Res;
    }
}
