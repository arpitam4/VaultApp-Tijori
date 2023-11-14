package com.example.tijori;

public class NewsModel {

    private String userId; // Add userId field
    private String title, desc, dateandtime, newsimage;

    public NewsModel() {
        // Default constructor required for calls to DataSnapshot.getValue(NewsModel.class)
    }

    public NewsModel(String userId, String newsimage) {
        this.userId = userId;
        this.newsimage = newsimage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getNewsimage() {
        return newsimage;
    }

    public void setNewsimage(String newsimage) {
        this.newsimage = newsimage;
    }
}
