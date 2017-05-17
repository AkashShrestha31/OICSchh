package com.example.user.oicsch.Notification;

/**
 * Created by user on 5/15/2017.
 */
public class Notify {
    String Title,Message,Image_url,Time,Date;

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public Notify(String Time, String Date, String Title, String Message, String Image_url)
    {
        this.Time=Time;
        this.Date=Date;
        this.Title=Title;
        this.Message=Message;
        this.Image_url=Image_url;

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
