package com.example.user.oicsch;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


public class sendata extends Fragment {

    URL url;
    int filename=0;
    EditText editText;
    EditText editText1,editText2,editText3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File show=new File("/sdcard/Android/data/com.example.user.oicsch/Files/downloaded_file0.csv");
        if(show.exists())
        {
            Log.d("Show","Exist");
        }
        else
           Log.d("Show","Doesnot exit");
        /*databaseReference.child("Details").child("college").setValue("orchid international college");
        databaseReference.child("Details").child("location").setValue("pepsikhola");
        databaseReference.child("Details").child("phoneno").setValue("9805360520");*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dummylayout, container, false);
        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String[] urlarray={"https://firebasestorage.googleapis.com/v0/b/oicsch-213e5.appspot.com/o/4th%20sem%2Fmonday.csv?alt=media&token=d59be39f-d5c0-4efa-ad84-ff40cb5f992c","https://firebasestorage.googleapis.com/v0/b/oicsch-213e5.appspot.com/o/4th%20sem%2Fsunday.csv?alt=media&token=05c2295b-aa4b-42c8-bd20-5d589e07d090","https://firebasestorage.googleapis.com/v0/b/oicsch-213e5.appspot.com/o/4th%20sem%2Ftuesday.csv?alt=media&token=c601cd93-5d47-4402-bd3b-2105e6dffbeb","https://firebasestorage.googleapis.com/v0/b/oicsch-213e5.appspot.com/o/4th%20sem%2Fwednesday.csv?alt=media&token=c425b5c2-4ea4-48c4-b3a7-935f40a0057f"};
        download down=new download();
        down.execute(urlarray);
        return view;
    }
    class download extends AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(String... params) {
            Log.d("View","aljfllajl"+ params[0]);
            for (String param : params) {
                try {
                    url = new URL(param);
                    downloadurl(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }
        void downloadurl(URL url)

        {
            int count;
            try {

                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                int lenghtOfFile = httpURLConnection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = httpURLConnection.getInputStream();
                // Output stream to write file
                File SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files");
                Log.d("View","This is diaroctyu"+ Arrays.toString(SDCardRoot.listFiles()));
                SDCardRoot.mkdirs();
                //create a new file, to save the downloaded file
                File file = new File(SDCardRoot,"downloaded_file"+filename+".csv");
                filename++;
                FileOutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    Log.d("View","Fucking hell");
                    //total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    //  publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void publishProgress(String s) {
        }
    }
}
