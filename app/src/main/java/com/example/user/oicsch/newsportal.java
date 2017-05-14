package com.example.user.oicsch;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.oicsch.setting.Activity;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class newsportal extends Activity{
    TextView textView;
    ImageView imageView;
    RecyclerView recyclerView;
    newadapter mAdapter;
    ArrayList<getResponseData> sendData;
    API api;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsportal);
      internetcheck();
    }
void internetcheck(){
    progressBar = (ProgressBar) findViewById(R.id.progressbar);
    CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
        progressBar.setVisibility(View.VISIBLE);
        connecttoapi();
    } else {
        progressBar.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        internetcheck();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.WHITE);
snackbar.setActionTextColor(Color.BLACK);
        snackbar.show();
    }
}
    void connecttoapi() {
        sendData = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        api = new Retrofit.Builder().baseUrl("https://newsapi.org/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build()
                .create(API.class);
        getTechCunch();
        getendgate();
        getTechnica();
        mAdapter = new newadapter(sendData, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(newsportal.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public void getTechCunch() {
        api.getTechCrunchData().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseModel>() {
                    @Override
                    public void call(ResponseModel responseModel) {

                        for (ResponseModel.ArticlesBean model : responseModel.getArticles()) {
                            String s = "TechCrunch";
                            String description = model.getPublishedAt();
                            String[] splitdescription = description.split("T");
                            String[] timesplit = splitdescription[1].split(":");
                            try {
                                String _24HourTime = timesplit[0] + ":" + timesplit[1];
                                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                                sendData.add(new getResponseData(model.getUrlToImage(), model.getTitle(), model.getUrl(), (model.getAuthor() == null) ? "Unknown" : "" + model.getAuthor(), splitdescription[0] + "," + _12HourSDF.format(_24HourDt), s));
                                // Toast.makeText(MainActivity.this,_12HourSDF.format(_24HourDt), Toast.LENGTH_SHORT).show();
                            } catch (Exception ignored) {

                            }


                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        progressBar.setVisibility(View.GONE);
                        internetcheck();
                       // Toast.makeText(newsportal.this, "Network failed" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void getTechnica() {
        api.getTechnica().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseModel>() {
                    @Override
                    public void call(ResponseModel responseModel) {
                        //Toast.makeText(MainActivity.this, responseModel.getArticles().get(1).getDescription(), Toast.LENGTH_SHORT).show();
                        for (ResponseModel.ArticlesBean model : responseModel.getArticles()) {
                            String s = "Ars Technica";
                            String description = model.getPublishedAt();
                            String[] splitdescription = description.split("T");
                            String[] timesplit = splitdescription[1].split(":");
                            try {
                                String _24HourTime = timesplit[0] + ":" + timesplit[1];
                                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                                sendData.add(new getResponseData(model.getUrlToImage(), model.getTitle(), model.getUrl(), (model.getAuthor() == null) ? "Unknown" : "" + model.getAuthor(), splitdescription[0] + "," + _12HourSDF.format(_24HourDt), s));
                                //Toast.makeText(MainActivity.this,_12HourSDF.format(_24HourDt), Toast.LENGTH_SHORT).show();
                            } catch (Exception ignored) {

                            }
                            //Toast.makeText(MainActivity.this, model.getDescription(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        progressBar.setVisibility(View.GONE);
                        internetcheck();
                        //Toast.makeText(newsportal.this, "Network failed" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void getendgate() {
        api.getEngadget().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseModel>() {
                    @Override
                    public void call(ResponseModel responseModel) {
                        for (ResponseModel.ArticlesBean model : responseModel.getArticles()) {
                            String s = "Engadget";
                            String description = model.getPublishedAt();
                            String[] splitdescription = description.split("T");
                            String[] timesplit = splitdescription[1].split(":");
                            try {
                                //time formatter form 24 to 12 hour
                                String _24HourTime = timesplit[0] + ":" + timesplit[1];
                                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                                sendData.add(new getResponseData(model.getUrlToImage(), model.getTitle(), model.getUrl(), (model.getAuthor() == null) ? "Unknown" : "" + model.getAuthor(), splitdescription[0] + "," + _12HourSDF.format(_24HourDt), s));

                            } catch (Exception ignored) {

                            }
                            //Toast.makeText(MainActivity.this, model.getDescription(), Toast.LENGTH_SHORT).show();
                        }

                        if (sendData != null) {
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setAdapter(mAdapter);
                        }

                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        progressBar.setVisibility(View.GONE);
                        internetcheck();
                        Log.d("akash", "" + throwable.getMessage());
                        //Toast.makeText(newsportal.this, "Network failed" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}

