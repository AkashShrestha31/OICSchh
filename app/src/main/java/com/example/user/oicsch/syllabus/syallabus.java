package com.example.user.oicsch.syllabus;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class syallabus extends AppCompatActivity {
    int filecount = 0;
    int S = 0;
    InputStream inputStream;
    StorageReference storageRef;
    FirebaseStorage storage;
    String[] syllabusfilename = {"BscCsit(First)", "BscCsit(Second)", "BscCsit(Third)", "BscCsit(Fourth)", "BscCsit(Fifth)", "BscCsit(Sixth)", "BscCsit(Seventh)", "BscCsit(Eight)", "Bim(First)", "Bim(Second)", "Bim(Third)", "Bim(Fourth)", "Bim(Fifth)", "Bim(Sixth)", "Bim(Seventh)", "Bim(Eight)", "Bsw(First)", "Bsw(Second)6", "Bsw(Third)"};
    private ProgressDialog progressDialog;

    CoordinatorLayout coordinatorLayout;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syallabus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Syllabus");
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewpager);
        check();

    }

    void check() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading file....");
        progressDialog.setCancelable(false);
        Button button = (Button) findViewById(R.id.syllabusbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkconnection();
            }
        });
    }
void checkconnection()
{
    CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.syllabcoordinate);
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
        progressDialog.show();
        downlodsyllabus();
    } else {
        progressDialog.dismiss();
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkconnection();
                    }
                });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.WHITE);
        snackbar.setActionTextColor(Color.BLACK);
        snackbar.show();
    }
}
    void downlodsyllabus() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storage.getReference();
        for (String syllab : syllabusfilename) {
            storageRef = storage.getReferenceFromUrl("gs://oicsch-213e5.appspot.com/Bsc Csit syllabus").child(syllab + ".pdf");
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    firebasepdfdownloader firebasepdfdownloader = new firebasepdfdownloader();
                    firebasepdfdownloader.execute(uri.toString());
                    //Toast.makeText(Activity.this, "This is success from pdf"+uri+"name: "+storageRef.getName(), Toast.LENGTH_SHORT).show();
                    //filecount++;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        }
    }

    class firebasepdfdownloader extends AsyncTask<String, Void, Void> {

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Toast.makeText(syallabus.this, "This is called at the end", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                //Log.d("akash","filename is "+syllabusfilename[filecount]);
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Log.d("ak", "" + S);
                    S++;
                    URL url = new URL(params[0]);
                    Log.d("akash", "url:  " + params[0]);
                    String[] seperate = params[0].split("2F");
                    String[] seperate1 = seperate[1].split(".pdf");
                    String seperate2 = seperate1[0].replaceAll("%29", ")");
                    String s = seperate2.replaceAll("%28", "(");
                    Log.d("akash", "splitvalue:" + s);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    inputStream = httpURLConnection.getInputStream();

                    File SDCardRoot1 = Environment.getExternalStorageDirectory();
                    File SDCard = new File(SDCardRoot1.getAbsolutePath() + "/" + "Android/data/com.example.user.oicsch", "Files");
                    if (!SDCard.exists())
                        SDCard.mkdir();
                    File SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Syllabus");
                    if (!SDCardRoot.exists())
                        SDCardRoot.mkdir();
                    //create a new file, to save the downloaded file
                    File file = new File(SDCardRoot, s + ".pdf");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = inputStream.read(buffer)) != -1) {
                        //Log.d("akash",""+in.read());
                        fileOutputStream.write(buffer, 0, read);
                    }
                    filecount++;
                    Log.d("akash", "" + S);
                } else {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                    snackbar.show();
                }
            } catch (Exception ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (S >= 16) {
                progressDialog.dismiss();

            }
        }
    }

    private void setupViewPager(ViewPager viewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragmeent(new BscCsit(), "Bsc Csit");
        adapter.addFragmeent(new Bim(), "Bim");
        adapter.addFragmeent(new Bsw(), "Bsw");
        viewpager.setAdapter(adapter);
    }
}
