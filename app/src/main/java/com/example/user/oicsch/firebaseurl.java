package com.example.user.oicsch;

/**
 * Created by user on 3/5/2017.
 */

public class firebaseurl {
    private final String filename;
    private final String url;


    public firebaseurl(String filename, String url)
    {
        this.filename=filename;
        this.url=url;

    }

    public String getUrl() {
        return url;
    }

    public String getFilename() {
        return filename;
    }
}
