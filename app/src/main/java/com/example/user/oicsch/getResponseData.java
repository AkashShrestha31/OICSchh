package com.example.user.oicsch;

/**
 * Created by user on 2/25/2017.
 */

public class getResponseData {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String pagename;

    public getResponseData(String urlToImage, String description, String url, String author,String publishedAt, String pagename) {
        this.urlToImage = urlToImage;
        this.description = description;
        this.url = url;
        this.author=author;
        this.publishedAt = publishedAt;
        this.pagename = pagename;
    }


    public String getAuthor() {
        return author;
    }

    public String getPagename() {
        return pagename;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description+".";
    }


    public String getUrl() {
        return url;
    }


    public String getUrlToImage() {
        return urlToImage;
    }


    public String getPublishedAt() {
        return publishedAt;
    }


}
