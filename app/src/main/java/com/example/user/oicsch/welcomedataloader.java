package com.example.user.oicsch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.oicsch.WelcomeScreen.welcomescreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class welcomedataloader extends AppCompatActivity {
    private static final int REQUEST_WRITE_STORAGE = 112;
    private ProgressBar progressBar;
    private File Dir;
    private ImageView imageView;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
          //  Toast.makeText(this, ""+permissions[0]+ "was "+grantResults[0], Toast.LENGTH_SHORT).show();
            //resume tasks needing this permission
        }
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                    File SDCardRoot = Environment.getExternalStorageDirectory();
                   Dir=new File(SDCardRoot.getAbsolutePath()+ "/" +"Android/data","com.example.user.oicsch");
                    Dir.mkdir();

                    //reload my activity with permission granted or use the features what required the permission
                } else
                {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomedataloader);
        imageView=(ImageView)findViewById(R.id.imageView2);
      // Toast.makeText(this, ""+ android.os.Build.VERSION_CODES.LOLLIPOP, Toast.LENGTH_SHORT).show();
     //   Toast.makeText(this, ""+ android.os.Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();

        if(android.os.Build.VERSION.SDK_INT>android.os.Build.VERSION_CODES.LOLLIPOP) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
            } else {
                Toast.makeText(this, "The app has permission", Toast.LENGTH_LONG).show();
                File SDCardRoot = Environment.getExternalStorageDirectory();
                Dir=new File(SDCardRoot.getAbsolutePath()+ "/" +"Android/data","com.example.user.oicsch");
                if(Dir.exists()){
                  //  Toast.makeText(this, "directory exist", Toast.LENGTH_SHORT).show();
                }
                else{
                    Dir.mkdir();
                   // Toast.makeText(this, "directory is just created", Toast.LENGTH_SHORT).show();
                }

            }
        }
        else
        {

            File SDCardRoot = Environment.getExternalStorageDirectory();
            Dir=new File(SDCardRoot.getAbsolutePath()+ "/" +"Android/data","com.example.user.oicsch");
            if(Dir.exists()){
               // Toast.makeText(this, "directory exist", Toast.LENGTH_SHORT).show();
            }
            else{
                Dir.mkdir();
               // Toast.makeText(this, "directory is just created", Toast.LENGTH_SHORT).show();
            }

        }
    startretry();

    }
private void startretry()
{
    imageView.setBackgroundResource(0);
    imageView.setBackgroundResource(R.mipmap.ic_launcher);
    progressBar=(ProgressBar)findViewById(R.id.progressBar2) ;
    progressBar.setVisibility(View.VISIBLE);
    SharedPreferences sharedPreferences = getSharedPreferences("opt", Context.MODE_PRIVATE);
    CoordinatorLayout coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
    if (sharedPreferences.getString("check", "").isEmpty()) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
          //  Toast.makeText(this, "Network Found", Toast.LENGTH_LONG).show();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://oicsch-213e5.appspot.com").child("semistername1.csv");
          //  Toast.makeText(this, "This is download url: "+storageRef.getDownloadUrl(), Toast.LENGTH_SHORT).show();
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //Toast.makeText(welcomedataloader.this, "Successful"+uri, Toast.LENGTH_LONG).show();
                    senduri(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //Toast.makeText(welcomedataloader.this, "failure: "+exception, Toast.LENGTH_LONG).show();
                }
            });

           /* FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            storageRef.child("/semistername1.csv").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Toast.makeText(welcomedataloader.this, "Successful", Toast.LENGTH_LONG).show();
                    senduri(uri);
                    Log.d("log", "successful" + uri);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(welcomedataloader.this, "failure: "+e, Toast.LENGTH_LONG).show();
                    Log.d("line", "failure" + e);
                }
            });*/
        } else {
            progressBar.setVisibility(View.GONE);
            imageView.setBackgroundResource(0);
           imageView.setBackgroundResource(R.drawable.sad);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
startretry();
                        }
                    });
            snackbar.show();
            //Toast.makeText(this, "No Network Found", Toast.LENGTH_LONG).show();
        }

    } else {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(welcomedataloader.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 0);
    }
}
    private void senduri(Uri uri) {
        String urlarray = uri.toString();
      //  Toast.makeText(this, "This is from send uri", Toast.LENGTH_LONG).show();
        firebaseurldownload firebaseurldownloads = new firebaseurldownload();
        firebaseurldownloads.execute(urlarray);
    }

    private class firebaseurldownload extends AsyncTask<String, Void, Void> {

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //Toast.makeText(welcomedataloader.this, "This is from do in backgorund", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);

            Log.d("log", "outside");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(welcomedataloader.this, welcomescreen.class);
                    startActivity(intent);
                    finish();
                }
            }, 0);
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                //Toast.makeText(welcomedataloader.this, "This is from doinbackgorund", Toast.LENGTH_LONG).show();
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                Log.d("log", "This is time htturlconnection" + httpURLConnection.getContentLength());
                httpURLConnection.connect();
                InputStream input = httpURLConnection.getInputStream();
                if (input != null) {
                    /*File SDCardRoot = Environment.getExternalStorageDirectory();
                    File Dir=new File(SDCardRoot.getAbsolutePath()+"/myappfile");*/
                   // Log.d("View", "This is diaroctyu" + Arrays.toString(SDCardRoot.listFiles()));
                    //Log.d("log","test"+Environment.getExternalStorageDirectory());
                   /* if(!Dir.exists())
                    Dir.mkdirs();*/
                    publishProgress();
                    //create a new file, to "save the downloaded file
                    File SDCardRoot = Environment.getExternalStorageDirectory();
                    Dir=new File(SDCardRoot.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch","SEMISTER.csv");

                    FileOutputStream output = new FileOutputStream(Dir);
                    byte data[] = new byte[1024];
                    int count;
                    while ((count = input.read(data)) != -1) {
                        Log.d("View", "Fucking hell");
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                    httpURLConnection.disconnect();
                }


            } catch (IOException e1) {
                e1.printStackTrace();
            }


            return null;
        }

    }
}
