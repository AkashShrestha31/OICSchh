package com.example.user.oicsch.Notification;

/**
 * Created by user on 5/15/2017.
 */

class BscCsit {
    private final String Title;
    private final String Message;
    private final String Image_url;

    public BscCsit(String image_url, String message, String title) {
        Image_url = image_url;
        Message = message;
        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public String getMessage() {
        return Message;
    }

    public String getImage_url() {
        return Image_url;
    }
}
