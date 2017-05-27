package com.example.user.oicsch.sectionB;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

/**
 * Created by user on 2/4/2017.
 */
public class TuesdayB extends Fragment {
    private ArrayList<schedule> array;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private File SDCardRoot;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("storevalue", Context.MODE_PRIVATE);
        String faculty = sharedPreferences.getString("faculty", "");
        String semister = sharedPreferences.getString("semister", "");
        String combineboth=faculty.concat(semister);
        Log.d("line",combineboth);
        array = new ArrayList<>();
        switch (combineboth)
        {
            case "Bsc CsitZero":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BSCCSIT1BTUE.csv");
                Log.d("log",""+SDCardRoot);
                break;
            case "Bsc CsitOne":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BSCCSIT2BTUE.csv");
                break;
            case "Bsc CsitTwo":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BSCCSIT3BTUE.csv");
                break;
            case "Bsc CsitThree":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BSCCSIT4BTUE.csv");
                break;
            case "BimZero":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BIM1BTUE.csv");
                break;
            case "BimOne":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BIM2BTUE.csv");
                break;
            case "BimTwo":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BIM3BTUE.csv");
                break;
            case "BimThree":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BIM4BTUE.csv");
                break;
            case "BswZero":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BSW1BTUE.csv");
                break;
            case "BswOne":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BSW2BTUE.csv");
                break;
            case "BswTwo":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BSW3BTUE.csv");
                break;
            case "BswThree":
                SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/BSW4BTUE.csv");
        }
        try {
            if (SDCardRoot.exists()) {
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
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this.getActivity(), "File Doesnot Exists", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view=null;
        try {
            if (SDCardRoot.exists()) {
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
        }
        catch (Exception ignored){}

        return view;
    }
}
