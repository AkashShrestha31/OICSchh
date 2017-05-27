package com.example.user.oicsch.sectionA;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.oicsch.R;

import com.example.user.oicsch.Adapter.RVAdapter;
import com.example.user.oicsch.schedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SundayA extends Fragment {
    private ArrayList<schedule> array;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private File SDCardRoot;
    private File SDCardRoot1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showdata();

    }

    @Override
    public void onResume() {
        super.onResume();
        showdata();
    }

    private void showdata(){
        sharedPreferences = getActivity().getSharedPreferences("storevalue", Context.MODE_PRIVATE);
        String faculty = sharedPreferences.getString("faculty", "");
        String semister = sharedPreferences.getString("semister", "");
        String combineboth = faculty.concat(semister);
        SDCardRoot1 = Environment.getExternalStorageDirectory();
        Log.d("line", combineboth);
        array = new ArrayList<>();
        Log.d("log", "combineboth" + combineboth);
        switch (combineboth) {
            case "Bsc CsitZero":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSCCSIT1ASUN.csv");
                Log.d("log", "" + SDCardRoot);
                break;
            case "Bsc CsitOne":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSCCSIT2ASUN.csv");
                break;
            case "Bsc CsitTwo":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSCCSIT3ASUN.csv");
                break;
            case "Bsc CsitThree":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSCCSIT4ASUN.csv");
                break;
            case "BimZero":
                Log.d("log", "This is from BimZero");
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BIM1ASUN.csv");
                break;
            case "BimOne":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BIM2ASUN.csv");
                break;
            case "BimTwo":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BIM3ASUN.csv");
                break;
            case "BimThree":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BIM4ASUN.csv");
                break;
            case "BswZero":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSW1ASUN.csv");
                break;
            case "BswOne":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSW2ASUN.csv");
                break;
            case "BswTwo":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSW3ASUN.csv");
                break;
            case "BswThree":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSW4ASUN.csv");
            default:
                Log.d("log", "This is from default");
                break;

        }
        try {
            Log.d("log", "file check" + SDCardRoot.exists());
            if (SDCardRoot.exists()) {
                Log.d("log", "fuckker");
                try {
                    FileInputStream fileInputStream = new FileInputStream(SDCardRoot);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String newline;
                    while ((newline = reader.readLine()) != null) {
                        String[] row = newline.split(",");
                        schedule send = new schedule(row[0], row[1], row[2]);
                        array.add(send);
                    }
                } catch (IOException e) {
                    Log.d("log", "inside file check" + e);
                }
            } else {
                Toast.makeText(this.getActivity(), "File Doesnot Exists", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            if (SDCardRoot.exists()) {
                Log.d("log", "fuck");
                view = inflater.inflate(R.layout.fragment_sunday, container, false);
                RecyclerView rev = (RecyclerView) view.findViewById(R.id.rv);
                rev.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                rev.setLayoutManager(llm);
                RVAdapter adapter = new RVAdapter(getContext(), array);
                rev.setItemAnimator(new DefaultItemAnimator());
                rev.setAdapter(adapter);
            } else {
                view = inflater.inflate(R.layout.errorlayout, container, false);
            }
        } catch (Exception e) {
        }
        return view;
    }
}
