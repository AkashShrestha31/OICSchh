package com.example.user.oicsch;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class webview extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
      internetcheck();

    }
    private void internetcheck()
    {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        progressBar=(ProgressBar)findViewById(R.id.progressBar1);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            showinwebview();
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
        private void showinwebview()
    {

        sharedPreferences=this.getSharedPreferences("url", Context.MODE_PRIVATE);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new mywebviewclient());
        webView.loadUrl(sharedPreferences.getString("url",""));
    }
    private class mywebviewclient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url) {
            progressBar.setVisibility(View.GONE);
            view.loadUrl(url);
            return true;
        }
    }
}
