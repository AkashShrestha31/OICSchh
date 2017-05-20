package com.example.user.oicsch.Notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.user.oicsch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class notification extends AppCompatActivity {

    private NotificationRvadapter notificationRvadapter;
    private SharedPreferences checkstart;
    ArrayList<Notify> notifyArrayList;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        checkstart = getSharedPreferences("start", Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.notifytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notifyArrayList = new ArrayList<>();
        internetcheck();

    }

    void internetcheck() {
        progressbar = (ProgressBar) findViewById(R.id.notifyprogressbar);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.notifycoordinatorLayout);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            progressbar.setVisibility(View.VISIBLE);
            fetchnotification();
        } else {
            progressbar.setVisibility(View.GONE);
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

    public void fetchnotification() {
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("oop", "test: " + checkstart.getString("faculty", "").toUpperCase());
        DatabaseReference ref = database.getReference("Notification" + "/" + "" + checkstart.getString("faculty", "").toUpperCase());
        getSupportActionBar().setTitle("" + checkstart.getString("faculty", "").toUpperCase());
// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                    };
                    Map<String, String> map = child.getValue(genericTypeIndicator);
                    String title = map.get("Title");
                    String message = map.get("Message");
                    String image_url = map.get("Image_url");
                    String time = map.get("Time");
                    String date = map.get("Date");
                    notifyArrayList.add(new Notify(time, date, title, message, image_url));
                    Log.d("oop", "Title: " + title + "Message: " + message + "Image url: " + image_url);
                }
                if (notifyArrayList != null) {
                    progressbar.setVisibility(View.GONE);
                    Log.d("oop", "This if called notifyarraylist");
                    /* Sorting in decreasing order*/
                 //   Collections.sort(notifyArrayList, Collections.reverseOrder());
                    notificationRvadapter = new NotificationRvadapter(notifyArrayList, getApplicationContext());
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notificationrecycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(notification.this));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(notificationRvadapter);
                } else {
                    Log.d("oop", "listview null");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("oop", "Thsi is notification error" + databaseError);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
