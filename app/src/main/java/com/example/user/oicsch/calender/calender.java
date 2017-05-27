package com.example.user.oicsch.calender;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.user.oicsch.Adapter.ViewPagerAdapter;
import com.example.user.oicsch.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class calender extends AppCompatActivity {
    private int filecount=0;
    private int S;
    StorageReference storageRef;
    FirebaseStorage storage;
    private File SDCardRoot1;
    private final String[] calenderfilename={"BscCsit(First)Calender","BscCsit(Second)Calender","BscCsit(Third)Calender","BscCsit(Fourth)Calender","BscCsit(Fifth)Calender","BscCsit(Sixth)Calender","BscCsit(Seventh)Calender","BscCsit(Eight)Calender","Bim(First)Calender","Bim(Second)Calender","Bim(Third)Calender","Bim(Fourth)Calender","Bim(Fifth)Calender","Bim(Sixth)Calender","Bim(Seventh)Calender","Bim(Eight)Calender"};
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Downloading file....");
        progressDialog.setCancelable(false);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Calender");
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewpager);
        Button button=(Button)findViewById(R.id.buttoncalender);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                S=0;
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef=storage.getReference();
                storage.getReference();
                for(String cal:calenderfilename) {
                    storageRef = storage.getReferenceFromUrl("gs://oicsch-213e5.appspot.com/Calender").child(cal+".pdf");
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("cal","success "+uri);
                            Log.d("ak",""+S);
                            S++;
                           firebasepdfdownloader firebasepdfdownloader = new firebasepdfdownloader();
                            firebasepdfdownloader.execute(uri.toString());
                            //Toast.makeText(Activity.this, "This is success from pdf"+uri+"name: "+storageRef.getName(), Toast.LENGTH_SHORT).show();
                            //filecount++;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("ak",""+S);
                            S++;
                            Log.d("cal","failed "+exception);
                        }
                    });
                }
            }
        });
    }
    private class firebasepdfdownloader extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                //Log.d("akash","filename is "+syllabusfilename[filecount]);

                URL url=new URL(params[0]);
                Log.d("akash","url:  "+params[0]);
                String[] seperate=params[0].split("2F");
                String[] seperate1=seperate[1].split(".pdf");
                String seperate2=seperate1[0].replaceAll("%29",")");
                String s=seperate2.replaceAll("%28","(");
                Log.d("akash","splitvalue:"+s);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                SDCardRoot1 = Environment.getExternalStorageDirectory();
                File SDCard=new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch","Files");
                if(!SDCard.exists())
                    SDCard.mkdir();
                File SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender");
                if(!SDCardRoot.exists()) {
                    SDCardRoot.mkdir();
                    Log.d("ak","directory created");
                }
                else
                Log.d("ak","Directory not created");
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
                //Log.d("akash",""+S);
            }
            catch (Exception ignored) {}
            return null;
        }

       @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           Log.d("ak","final value: "+S);
           S++;
            if(S>=16) {
                progressDialog.dismiss();

            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void setupViewPager(ViewPager viewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragmeent(new BscCsitcalender(), "BSC CSIT");
        adapter.addFragmeent(new Bimcalender(), "BIM");
        adapter.addFragmeent(new Bswcalender(), "BSW");
        viewpager.setAdapter(adapter);
    }
}
