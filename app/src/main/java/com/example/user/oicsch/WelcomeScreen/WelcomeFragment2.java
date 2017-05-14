package com.example.user.oicsch.WelcomeScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.oicsch.MainActivity;
import com.example.user.oicsch.MasterFragment_sectionA;
import com.example.user.oicsch.MasterFragment_sectionB;
import com.example.user.oicsch.R;
import com.example.user.oicsch.firebaseurl;
import com.example.user.oicsch.schedule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class WelcomeFragment2 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("line","fuckafafaf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_welcome_fragment2, container, false);
        /*SharedPreferences checkstart = getActivity().getSharedPreferences("start", Context.MODE_PRIVATE);
        SharedPreferences.Editor checkeditor = checkstart.edit();
        checkeditor.putString("faculty","Bsc Csit");
                checkeditor.putString("semister","0");
        checkeditor.putString("section","0");
        checkeditor.apply();*/
        Button button=(Button)getActivity().findViewById(R.id.startbutton);
        button.setVisibility(View.GONE);
        return  view;
    }


}

