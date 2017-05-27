package com.example.user.oicsch.News;

/**
 * Created by user on 2/25/2017.
 */

class getResponseData {
    private final String author;
    private String title;
    private final String description;
    private final String url;
    private final String urlToImage;
    private final String publishedAt;
    private final String pagename;

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
