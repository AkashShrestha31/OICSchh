package com.example.user.oicsch;

import android.os.Parcel;
import android.os.Parcelable;


class jsondata implements Parcelable{
    private final String image;
    private final String description;
    private final String url;
    private final String publishedat;
    jsondata(String image, String description, String url, String publishedat)
    {
        //Log.d("hey","imageurl :"+image);
        this.image=image;
        this.description=description;
        this.url=url;
        this.publishedat=publishedat;
    }
    private jsondata(Parcel input)
    {
        image=input.readString();
        description=input.readString();
        url=input.readString();
        publishedat=input.readString();
    }
    String getimage()
    {
        return image;
    }

    String getDescription() {
        return description;
    }

    String geturl() {
        return url;
    }

String getPublishedat()
{
    return publishedat;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(publishedat);

    }
    public static final Creator<jsondata> CREATOR
            = new Creator<jsondata>() {
        public jsondata createFromParcel(Parcel in) {
            return new jsondata(in);
        }

        public jsondata[] newArray(int size) {
            return new jsondata[size];
        }
    };
}
