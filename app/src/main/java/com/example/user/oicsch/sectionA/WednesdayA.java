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

/**
 * Created by user on 2/4/2017.
 */
public class WednesdayA extends Fragment {
    private ArrayList<schedule> array;
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

    private void showdata()
  {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("storevalue", Context.MODE_PRIVATE);
        String faculty = sharedPreferences.getString("faculty", "");
        String semister = sharedPreferences.getString("semister", "");
        String combineboth = faculty.concat(semister);
        SDCardRoot1 = Environment.getExternalStorageDirectory();
        Log.d("line", combineboth);
        array = new ArrayList<>();
        switch (combineboth) {
            case "Bsc CsitZero":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSCCSIT1AWED.csv");
                break;
            case "Bsc CsitOne":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSCCSIT2AWED.csv");
                break;
            case "Bsc CsitTwo":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSCCSIT3AWED.csv");
                break;
            case "Bsc CsitThree":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSCCSIT4AWED.csv");
                break;
            case "BimZero":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BIM1AWED.csv");
                break;
            case "BimOne":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BIM2AWED.csv");
                break;
            case "BimTwo":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BIM3AWED.csv");
                break;
            case "BimThree":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BIM4AWED.csv");
                break;
            case "BswZero":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSW1AWED.csv");
                break;
            case "BswOne":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSW2AWED.csv");
                break;
            case "BswTwo":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSW3AWED.csv");
                break;
            case "BswThree":
                SDCardRoot = new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch/Files","BSW4AWED.csv");
                break;
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
                        schedule send = new schedule(row[0].toString(), row[1].toString(), row[2].toString());
                        array.add(send);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
                view = inflater.inflate(R.layout.fragment_sunday, container, false);
                RecyclerView rev = (RecyclerView) view.findViewById(R.id.rv);
                rev.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                rev.setLayoutManager(llm);
                RVAdapter adapter = new RVAdapter(getContext(), array);
                rev.setItemAnimator(new DefaultItemAnimator());
                rev.setAdapter(adapter);
            }
            else {
                view = inflater.inflate(R.layout.errorlayout, container, false);
            }
        } catch (Exception ignored) {}
        return view;
    }
}
