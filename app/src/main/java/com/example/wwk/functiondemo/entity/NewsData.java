package com.example.wwk.functiondemo.entity;

/**
 * Created by wwk on 17/6/1.
 */

public class NewsData {

    // Title of news
    private String mTitle;
    // Source of news
    private String mSource;
    // Image of news
    private String mImageUrl;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String title) {
        this.mTitle = title;
    }

    public String getmSource() {
        return mSource;
    }

    public void setmSource(String source) {
        this.mSource = source;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

}
