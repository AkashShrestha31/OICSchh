package com.example.user.oicsch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.oicsch.Adapter.NavDrawerListAdapter;
import com.example.user.oicsch.News.newsportal;
import com.example.user.oicsch.Notification.notification;
import com.example.user.oicsch.calender.calender;
import com.example.user.oicsch.setting.Activity;
import com.example.user.oicsch.syllabus.syallabus;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private ListView lv;
    TextView tv;
    private ArrayList<navimage> navigationimage;
    private ImageView navimg;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private AlertDialog alert;
    private Toolbar tb;
    private String check = "oop";
    private SharedPreferences checkstart;
    private ArrayList<Nav_drawer_item> store_item;
    private ArrayList<String> firebasesemisterBSCCSIT;
    private ArrayList<String> firebasesemisterBIM;
    private ArrayList<String> firebasesemisterBSW;
    private DrawerLayout mdrawerlayout;
    private SharedPreferences.Editor editor;
    private NavDrawerListAdapter nav;
    private String[] nevMenuItem;
    AlertDialog.Builder dialog_builder;
    public ProgressDialog mProgressDialog;
    DatabaseReference databaseReference;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    ImageView imagProfile;
    private SharedPreferences token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("storevalue", Context.MODE_PRIVATE);
        checkstart = getSharedPreferences("start", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_main);
        //device token sharedpreference

        token=getSharedPreferences("Token",Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        Button signInButton = (Button) findViewById(R.id.login);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    //login button
                    try {
                        googlesignin();
                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, "Sign in failed!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Connection Failed!!", Toast.LENGTH_LONG).show();
                }
            }
        });


        tb = (Toolbar) findViewById(R.id.view);
        setSupportActionBar(tb);
        setTitle("OICSch");
        //navigation menu item from  resource array
        nevMenuItem = getResources().getStringArray(R.array.nav_drawer_item);
        store_item = new ArrayList<>();
        tv = (TextView) findViewById(R.id.title);
        navigationimage = new ArrayList<>();
        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
        nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
        navimg = (ImageView) findViewById(R.id.navimage);
        for (int i = 0; i < 8; i++) {
            store_item.add(new Nav_drawer_item(nevMenuItem[i]));
            Log.d("log", "" + nevMenuItem[i]);
        }
        createnavitem();
        createspinner("0");
        createdrawer();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();

                try {
                    //Toast.makeText(this, "This is called", Toast.LENGTH_SHORT).show();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Students");
                    databaseReference.child("Students").child(""+account.getDisplayName());
                    databaseReference.child("Students").child(""+account.getDisplayName()).child("Gmail").setValue(""+account.getEmail());
                    databaseReference.child("Students").child(""+account.getDisplayName()).child("Image Url").setValue(""+account.getPhotoUrl());
                    databaseReference.child("Students").child(""+account.getDisplayName()).child("Device Token").setValue(""+token.getString("usertoken",""));

                }
                catch(Exception e) {
                    Log.d("aksh","Exception is "+e);
                }
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                // updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
                            //  Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    // [END auth_with_google]
    public void googlesignin() {
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this/* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            TextView textView = (TextView) findViewById(R.id.name);
            textView.setVisibility(View.VISIBLE);
            String str=user.getDisplayName();
            textView.setText(str);
            findViewById(R.id.login).setVisibility(View.GONE);
            imagProfile = (ImageView) findViewById(R.id.img_profile);
            // Loading profile image
            Glide.with(this).load(user.getPhotoUrl())
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagProfile);

        } else {
           /* mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);*/
        }
    }

    //progressbar for authuntication
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    //end of progress bar authenicitaion
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("failure", "" + connectionResult);
    }

    private void startup(String faculty, String sem, String section) {
        Log.d("poo", " " + faculty + "sem:" + sem + "section:" + section);
        switch (faculty) {
            case "BSC CSIT":
                //editor.clear();
                navigationimage.clear();
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitcolor)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                lv.setAdapter(nav);
                editor.putString("faculty", nevMenuItem[0]);
                editor.apply();
                if (sem.equals("0")) {
                    editor.putString("semister", "Zero");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                if (sem.equals("1")) {
                    editor.putString("semister", "One");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                if (sem.equals("2")) {
                    editor.putString("semister", "Two");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                if (sem.equals("3")) {
                    editor.putString("semister", "Three");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                lv.setItemChecked(0, true);
                break;
            case "BIM":
                navigationimage.clear();
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimcolor)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                lv.setAdapter(nav);
                editor.putString("faculty", nevMenuItem[1]);
                if (sem.equals("0")) {
                    editor.putString("semister", "Zero");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                if (sem.equals("1")) {
                    editor.putString("semister", "One");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                if (sem.equals("2")) {
                    editor.putString("semister", "Two");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                if (sem.equals("3")) {
                    editor.putString("semister", "Three");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                lv.setItemChecked(1, true);
                break;
            case "BSW":
                navigationimage.clear();
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswcolor)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                lv.setAdapter(nav);
                editor.putString("faculty", nevMenuItem[2]);
                if (sem.equals("0")) {
                    editor.putString("semister", "Zero");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                if (sem.equals("1")) {
                    editor.putString("semister", "One");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                if (sem.equals("2")) {
                    editor.putString("semister", "Two");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                if (sem.equals("3")) {
                    editor.putString("semister", "Three");
                    editor.apply();
                    createspinner(section);
                    editor.clear();
                }
                lv.setItemChecked(2, true);
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        createnavitem();
        startup(checkstart.getString("faculty", ""), checkstart.getString("semister", ""), checkstart.getString("section", ""));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        String str=checkstart.getString("faculty", "").toLowerCase().replaceAll(" ","");
        Log.d("akash",""+str);
        switch (str)
        {
            case "bsccsit":
                FirebaseMessaging.getInstance().subscribeToTopic(str);
                FirebaseMessaging.getInstance().unsubscribeFromTopic("bim");
                FirebaseMessaging.getInstance().unsubscribeFromTopic("bsw");
                break;
            case "bim":
                FirebaseMessaging.getInstance().subscribeToTopic(str);
                FirebaseMessaging.getInstance().unsubscribeFromTopic("bsccsit");
                FirebaseMessaging.getInstance().unsubscribeFromTopic("bsw");
                break;
            case "bsw":
                FirebaseMessaging.getInstance().subscribeToTopic(str);
                FirebaseMessaging.getInstance().unsubscribeFromTopic("bsccsit");
                FirebaseMessaging.getInstance().unsubscribeFromTopic("bim");
                break;
        }
        //FirebaseMessaging.getInstance().subscribeToTopic(str+""+ checkstart.getString("semister", ""));

        createnavitem();
        startup(checkstart.getString("faculty", ""), checkstart.getString("semister", ""), checkstart.getString("section", ""));
        Log.d("line", "fuck you bitch");
    }

    private void createdrawer() {
        mdrawerlayout = (DrawerLayout) findViewById(R.id.dlayout);
        ActionBarDrawerToggle mactionBardrawertoogle = new ActionBarDrawerToggle(this, mdrawerlayout, tb, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mdrawerlayout.addDrawerListener(mactionBardrawertoogle);
        mactionBardrawertoogle.syncState();
    }


    private void createnavitem() {
        File SDCardRoot1 = Environment.getExternalStorageDirectory();
        File file = new File(SDCardRoot1.getAbsolutePath() + "/" + "Android/data/com.example.user.oicsch", "SEMISTER.csv");
        firebasesemisterBSCCSIT = new ArrayList<>();
        firebasesemisterBIM = new ArrayList<>();
        firebasesemisterBSW = new ArrayList<>();

        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String show;
                while ((show = bufferedReader.readLine()) != null) {
                    String[] seperate = show.split(",");
                    if (seperate[0].equals("Bsc Csit")) {
                        firebasesemisterBSCCSIT.add(seperate[1]);

                    }
                    if (seperate[0].equals("Bim")) {
                        firebasesemisterBIM.add(seperate[1]);
                    }
                    if (seperate[0].equals("Bsw")) {
                        firebasesemisterBSW.add(seperate[1]);
                    }
                    Log.d("Test", seperate[1]);
                    navigationdraweritems();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

        }


    }

    private void navigationdraweritems() {
        lv = (ListView) findViewById(R.id.list_slidermenu);
        nav = new NavDrawerListAdapter(this, store_item, navigationimage);
        lv.setAdapter(nav);
        lv.setItemChecked(3, true);
        lv.setSelection(3);
        lv.setSelected(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mdrawerlayout.closeDrawer(GravityCompat.START);

                switch (position) {
                    case 0:
                        //getSupportActionBar().setSubtitle("About");
                        //editor.clear();
                        showsemiserCSIT(firebasesemisterBSCCSIT);
                        editor.putString("faculty", nevMenuItem[0]);
                        editor.apply();
                        break;
                    case 1:
                        showsemiserBIM(firebasesemisterBIM);
                        editor.putString("faculty", nevMenuItem[1]);
                        editor.apply();
                        break;
                    case 2:
                        showsemiserBSW(firebasesemisterBSW);
                        editor.putString("faculty", nevMenuItem[2]);
                        editor.apply();
                        break;
                    case 3:
                        navigationimage.clear();
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                        nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                        lv.setAdapter(nav);
                        Intent intent = new Intent(MainActivity.this, newsportal.class);
                        startActivity(intent);
                        break;
                    case 4:
                        navigationimage.clear();
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                        nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                        lv.setAdapter(nav);
                        Intent intent1 = new Intent(getApplicationContext(), syallabus.class);
                        startActivity(intent1);

                        break;
                    case 5:
                        navigationimage.clear();
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                        nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                        lv.setAdapter(nav);
                        Intent intent2 = new Intent(getApplicationContext(), calender.class);
                        startActivity(intent2);

                        break;
                    case 6:
                        navigationimage.clear();
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                        nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                        lv.setAdapter(nav);
                        Intent intent3 = new Intent(getApplicationContext(), notification.class);
                        startActivity(intent3);

                        break;
                    case 7:
                        navigationimage.clear();
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                        navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                        nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                        lv.setAdapter(nav);
                        Intent intent4 = new Intent(getApplicationContext(), about.class);
                        startActivity(intent4);
                        break;

                }

            }

        });

    }

    public String showsemiserCSIT(ArrayList<String> firebasesemister) {
        dialog_builder = new AlertDialog.Builder(MainActivity.this);
        dialog_builder.setCancelable(false);
        LayoutInflater inflat = getLayoutInflater();
        dialog_builder.setCancelable(false);
        View contentView = inflat.inflate(R.layout.dialog, null);
        dialog_builder.setView(contentView);
        ListView llv = (ListView) contentView.findViewById(R.id.listView1);
        ArrayAdapter<String> adap = new ArrayAdapter<>(MainActivity.this, R.layout.dialoglistview, firebasesemister);
        llv.setDivider(null);
        llv.setAdapter(adap);
        alert = dialog_builder.create();
        alert.show();

        llv.setItemChecked(0, true);
        llv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    editor.putString("semister", "Zero");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                if (position == 1) {
                    editor.putString("semister", "One");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                if (position == 2) {
                    editor.putString("semister", "Two");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                if (position == 3) {
                    editor.putString("semister", "Three");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                navigationimage.clear();
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitcolor)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                lv.setAdapter(nav);
                lv.setItemChecked(0, true);

            }

        });
        return check;
    }

    public String showsemiserBIM(ArrayList<String> firebasesemister) {
        dialog_builder = new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme);
        dialog_builder.setCancelable(false);
        LayoutInflater inflat = getLayoutInflater();
        View contentView = inflat.inflate(R.layout.dialog, null);
        dialog_builder.setView(contentView);
        ListView llv = (ListView) contentView.findViewById(R.id.listView1);
        ArrayAdapter<String> adap = new ArrayAdapter<>(MainActivity.this, R.layout.dialoglistview, firebasesemister);
        llv.setDivider(null);
        check = "oop";
        llv.setAdapter(adap);
        alert = dialog_builder.create();
        alert.show();
        llv.setItemChecked(0, true);
        llv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    editor.putString("semister", "Zero");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                if (position == 1) {

                    editor.putString("semister", "One");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                if (position == 2) {

                    editor.putString("semister", "Two");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                if (position == 3) {

                    editor.putString("semister", "Three");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                navigationimage.clear();
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimcolor)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                lv.setAdapter(nav);
                lv.setItemChecked(1, true);
            }
        });
        return check;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            alert.dismiss();
        } catch (Exception e) {

        }
    }

    public void showsemiserBSW(ArrayList<String> firebasesemister) {
        dialog_builder = new AlertDialog.Builder(MainActivity.this);
        dialog_builder.setCancelable(false);
        LayoutInflater inflat = getLayoutInflater();
        View contentView = inflat.inflate(R.layout.dialog, null);
        dialog_builder.setView(contentView);
        ListView llv = (ListView) contentView.findViewById(R.id.listView1);
        ArrayAdapter<String> adap = new ArrayAdapter<>(MainActivity.this, R.layout.dialoglistview, firebasesemister);
        llv.setDivider(null);
        llv.setAdapter(adap);
        alert = dialog_builder.create();
        alert.show();
        llv.setItemChecked(0, true);
        llv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    editor.putString("semister", "Zero");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                if (position == 1) {
                    editor.putString("semister", "One");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                if (position == 2) {
                    editor.putString("semister", "Two");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                if (position == 3) {
                    editor.putString("semister", "Three");
                    editor.apply();
                    createspinner("0");
                    alert.dismiss();
                    editor.clear();
                }
                navigationimage.clear();
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bsccsitblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bimblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.bswcolor)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.newsblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.syllabusblack)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.calender)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.notification)));
                navigationimage.add(new navimage(getResources().getDrawable(R.mipmap.aboutblack)));
                nav = new NavDrawerListAdapter(MainActivity.this, store_item, navigationimage);
                lv.setAdapter(nav);
                lv.setItemChecked(2, true);
            }
        });
    }

    public void createspinner(String section) {
        int select = Integer.parseInt(section);
        String[] spinner = {"Section A", "Section B"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this ,R.layout.spintext, spinner);
        Spinner spin = (Spinner) findViewById(R.id.spin);
        spin.setAdapter(adapter);
        spin.setSelection(select);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_container, new MasterFragment_sectionA());
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.frame_container, new MasterFragment_sectionB());
                        fragmentTransaction1.commit();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, Activity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
