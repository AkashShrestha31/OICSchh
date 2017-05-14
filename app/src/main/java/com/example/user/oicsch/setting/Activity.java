package com.example.user.oicsch.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.oicsch.Adapter.NavDrawerListAdapter;
import com.example.user.oicsch.MainActivity;
import com.example.user.oicsch.Nav_drawer_item;
import com.example.user.oicsch.R;
import com.example.user.oicsch.firebaseurl;
import com.example.user.oicsch.log;
import com.example.user.oicsch.navimage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Activity extends AppCompatActivity {
    private String[] settingitem = {"Update"};
    private String[] headerurl = {"/BSCCSITDATAURL/BSCCSITDATA.csv", "/BIMDATAURL/BIMDATA.csv", "/BSWDATAURL/BSWDATA.csv"};
    private URL url;
    ArrayList<String> semisterBSCCSIT;
    ArrayList<String> semisterBIM;
    ArrayList<String> semisterBSW;
    ArrayList<Nav_drawer_item> items;
    ArrayList<navimage> navimages;
    Spinner spinner1;
    Spinner spinner2;
    long total = 0;
    int count;
    int S=1;
    ProgressDialog progressDialog;
    Spinner spinner3;
    ArrayAdapter<String> adapter1;
    SharedPreferences checkstart;
    SharedPreferences.Editor checkeditor;
    ArrayList<firebaseurl> firebaseurls;

    String[] filename;
    String check;
    File SDCardRoot1;
    int filecount=0;
    File file;
    File SDCardRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);
        //just test
        SDCardRoot1 = Environment.getExternalStorageDirectory();
        SDCardRoot=new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch","Files");
        if(!SDCardRoot.exists())
        SDCardRoot.mkdir();
        filename=new String[9];
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait this may take few minute....");

       // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        selectiondata();
        items=new ArrayList<>();
        navimages=new ArrayList<>();
        items.add(new Nav_drawer_item("Update"));
        navimages.add(new navimage(getResources().getDrawable(R.mipmap.update)));
        NavDrawerListAdapter navDrawerListAdapter=new NavDrawerListAdapter(this,items,navimages);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(navDrawerListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                progressDialog.show();
                switch (position) {
                    case 0:
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        for(String header:headerurl) {
                            storageRef.child(header).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    senduri(uri);
                                    Log.d("line", "successful" + uri);
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("line", "failure" + e);
                                }
                            });
                        }
                        /*storageRef.child(headerurl[1]).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                S++;
                                senduri(uri);
                                Log.d("line", "successful" + uri);
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("line", "failure" + e);
                            }
                        });
                        storageRef.child(headerurl[2]).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                S++;
                                senduri(uri);
                                Log.d("line", "successful" + uri);
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("line", "failure" + e);
                            }
                        });*/

                        break;
                }
            }

            private void senduri(Uri uri) {
                String urlarray = uri.toString();
                firebaseurldownload firebaseurldownloads = new firebaseurldownload();
                firebaseurldownloads.execute(urlarray);
                download down = new download();
                down.execute(urlarray);
            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //selectiondata();
    }

    private void selectiondata() {
        spinner1 = (Spinner) findViewById(R.id.generalspinner1);
        spinner2 = (Spinner) findViewById(R.id.generalspinner2);
        spinner3 = (Spinner) findViewById(R.id.generalspinner3);
        checkstart = getSharedPreferences("start", Context.MODE_PRIVATE);
        checkeditor = checkstart.edit();
        semisterBSCCSIT = new ArrayList<>();
        semisterBIM = new ArrayList<>();
        semisterBSW = new ArrayList<>();
       // File SDCardRoot1 = Environment.getExternalStorageDirectory();
        SDCardRoot=new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch","SEMISTER.csv");
        if (SDCardRoot.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(SDCardRoot);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String newline;
                while ((newline = bufferedReader.readLine()) != null) {
                    String[] row = newline.split(",");
                    switch (row[0]) {
                        case "Bsc Csit":
                            semisterBSCCSIT.add(row[1]);
                            Log.d("line", "csit" + row[1]);
                            break;
                        case "Bim":
                            semisterBIM.add(row[1]);
                            Log.d("line", "bim" + row[1]);
                            break;
                        case "Bsw":
                            semisterBSW.add(row[1]);
                            Log.d("line", "bsw" + row[1]);
                            break;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("line", "NO data find");
        }
        //Faculty
        String[] spin = {"BSC CSIT", "BIM", "BSW"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.welcometext, spin);
        spinner1.setAdapter(adapter);
        switch (checkstart.getString("faculty", "")) {
            case "BSC CSIT":
                spinner1.setSelection(0);
                break;
            case "BIM":
                spinner1.setSelection(1);
                break;
            case "BSW":
                spinner1.setSelection(2);
                break;
        }
        spinner1.setSelected(true);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        checkeditor.remove("faculty");
                        checkeditor.putString("faculty",""+spinner1.getItemAtPosition(position).toString());
                        checkeditor.apply();
                        adapter1 = new ArrayAdapter<String>(Activity.this, R.layout.welcometext, semisterBSCCSIT);
                        spinner2.setAdapter(adapter1);
                        spinner2.setSelection(Integer.parseInt(checkstart.getString("semister","")));
                        break;
                    case 1:
                        checkeditor.remove("faculty");
                        checkeditor.putString("faculty",""+spinner1.getItemAtPosition(position).toString());
                        checkeditor.apply();
                        adapter1 = new ArrayAdapter<String>(Activity.this, R.layout.welcometext, semisterBIM);
                        spinner2.setAdapter(adapter1);
                        spinner2.setSelection(Integer.parseInt(checkstart.getString("semister","")));
                        break;
                    case 2:
                        checkeditor.remove("faculty");
                        checkeditor.putString("faculty",""+spinner1.getItemAtPosition(position).toString());
                        checkeditor.apply();
                        adapter1 = new ArrayAdapter<String>(Activity.this, R.layout.welcometext, semisterBSW);
                        spinner2.setAdapter(adapter1);
                        spinner2.setSelection(Integer.parseInt(checkstart.getString("semister","")));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //semister
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        checkeditor.remove("semister");
                        checkeditor.putString("semister","0");
                        checkeditor.apply();
                        break;
                    case 1:
                        checkeditor.remove("semister");
                        checkeditor.putString("semister","1");
                        checkeditor.apply();
                        log.message("hey fuck");
                        break;
                    case 2:
                        checkeditor.remove("semister");
                        checkeditor.putString("semister","2");
                        checkeditor.apply();
                        break;
                    case 3:
                        checkeditor.remove("semister");
                        checkeditor.putString("semister","3");
                        checkeditor.apply();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //section
        String[] Section = {"A", "B"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.welcometext, Section);
        spinner3.setAdapter(adapter2);
        spinner3.setSelection(Integer.parseInt(checkstart.getString("section","")));
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        checkeditor.remove("section");
                        checkeditor.putString("section","0");
                        checkeditor.apply();
                        break;
                    case 1:
                        checkeditor.remove("section");
                        checkeditor.putString("section","1");
                        checkeditor.apply();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


/*class firebasepdfdownloader extends  AsyncTask<String,Void,Void>{

    @Override
    protected Void doInBackground(String... params) {
        try {
            //Log.d("akash","filename is "+syllabusfilename[filecount]);
            Log.d("ak",""+S);
            S++;
            URL url=new URL(params[0]);
            Log.d("akash","url:  "+params[0]);
            String[] seperate=params[0].split("2F");
            String[] seperate1=seperate[1].split(".pdf");
            String seperate2=seperate1[0].replaceAll("%29",")");
            String s=seperate2.replaceAll("%28","(");
            Log.d("akash","splitvalue:"+s);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            File SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files");
            //create a new file, to save the downloaded file
            File file = new File(SDCardRoot,   s+".pdf");
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1)
            {
                //Log.d("akash",""+in.read());
                fileOutputStream.write(buffer, 0, read);
            }
            filecount++;
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(S>160) {
            progressDialog.dismiss();
            selectiondata();
        }
    }
}*/

    class firebaseurldownload extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                for (String param : params) {
                    url = new URL(param);
                    downloadfirebaseurl(url);
                }

            } catch (Exception ignored) {

            }
            return null;
        }

        private void downloadfirebaseurl(URL url) throws IOException {
            firebaseurls = new ArrayList<>();
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            int lenghtOfFile = httpURLConnection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = httpURLConnection.getInputStream();
            // Output stream to write file
            if (input != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(input);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    String[] split = s.split(",");
                    firebaseurls.add(new firebaseurl(split[4], split[5]));
                }
            }

            httpURLConnection.disconnect();
        }
    }

    class download extends AsyncTask<String, String,String> {
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
           // progressDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          progressDialog.show();
        }

               @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(S>143) {
                progressDialog.dismiss();
                selectiondata();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            // Log.d("line", "aljfllajl" + params[0]);
            for (int i = 0; i < firebaseurls.size(); i++) {
                Log.d("ak",""+S);
                S++;
                if (!firebaseurls.get(i).getUrl().equals("NULL")) {
                    Log.d("line", "dat found " + firebaseurls.get(i).getFilename());
                    try {
                        url = new URL(firebaseurls.get(i).getUrl());
                        String filename=firebaseurls.get(i).getFilename();
                        //downloadurl(url, firebaseurls.get(i).getFilename());
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.connect();
                        int lenghtOfFile = httpURLConnection.getContentLength();
                        // input stream to read file - with 8k buffer
                        InputStream input = httpURLConnection.getInputStream();
                        // Output stream to write file

                        //File SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files");
                        Log.d("View", "This is diaroctyu" + Arrays.toString(SDCardRoot.listFiles()));
                        //SDCardRoot.mkdirs();
                        //create a new file, to save the downloaded file
                        if(!filename.equals("SEMISTER"))
                        file = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files", filename + ".csv");
                        else
                            file = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch", filename + ".csv");
                        FileOutputStream output = new FileOutputStream(file);

                        byte data[] = new byte[1024];


                        while ((count = input.read(data)) != -1) {
                            Log.d("View", "Fucking hell");
                            total += count;
                            // publishing the progress....
                            // After this onProgressUpdate will be called
                            publishProgress(""+(int)((total*100)/lenghtOfFile));
                           // progressDialog.setProgress((int)((total*100)/lenghtOfFile));

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
                else
                {
                    Log.d("line","no data url "+firebaseurls.get(i).getFilename());
                }
            }

            return null;
        }

        void downloadurl(URL url, String filename) {
            try {

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                int lenghtOfFile = httpURLConnection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = httpURLConnection.getInputStream();
                // Output stream to write file
                File SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files");
                Log.d("View", "This is diaroctyu" + Arrays.toString(SDCardRoot.listFiles()));
                SDCardRoot.mkdirs();
                //create a new file, to save the downloaded file
                File file = new File(SDCardRoot, filename + ".csv");
                FileOutputStream output = new FileOutputStream(file);
                byte data[] = new byte[1024];
                 long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                      //publishProgress(""+(int)((total*100)/lenghtOfFile));
                    progressDialog.setProgress((int)((total*100)/lenghtOfFile));
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


    }
}