package com.example.user.oicsch;

import android.graphics.Bitmap;

/**
 * Created by user on 11/27/2016.
 */

class imagedata {
    private final Bitmap imagemap;
    public imagedata(Bitmap imagemap)
    {
     this.imagemap=imagemap;
    }
    Bitmap getImagemap()
    {
        return imagemap;
    }
}
