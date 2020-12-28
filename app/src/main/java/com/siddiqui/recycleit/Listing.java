package com.siddiqui.recycleit;

import java.io.Serializable;

// Created by Taha Siddiqui
// 2020-05-24
public class Listing implements Serializable {
    private String title;
    private String type;
    private String moreInformation;
    private String date;
    private int resorurce;

    public Listing(){

    }

    public Listing(String title, String type, String moreInformation, String date, int resorurce) {
        this.title = title;
        this.type = type;
        this.moreInformation = moreInformation;
        this.date = date;
        this.resorurce = resorurce;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        this.moreInformation = moreInformation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getResorurce() {
        return resorurce;
    }

    public void setResorurce(int resorurce) {
        this.resorurce = resorurce;
    }
}
