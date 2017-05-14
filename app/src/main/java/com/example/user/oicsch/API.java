package com.example.user.oicsch;

import retrofit2.http.GET;
import rx.Single;

/**
 * Created by user on 2/25/2017.
 */

public interface API {
    @GET("articles?source=techcrunch&sortBy=latest&apiKey=756d1b64573b4385a78d8fcea1063227")
    Single<ResponseModel> getTechCrunchData();
    @GET(" articles?source=ars-technica&sortBy=latest&apiKey=756d1b64573b4385a78d8fcea1063227")
    Single<ResponseModel> getTechnica();
    @GET("articles?source=engadget&sortBy=latest&apiKey=756d1b64573b4385a78d8fcea1063227")
    Single<ResponseModel> getEngadget();
}
