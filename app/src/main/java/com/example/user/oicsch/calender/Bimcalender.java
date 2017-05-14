package com.example.user.oicsch.calender;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.oicsch.Adapter.SyllabusRVAdapter;
import com.example.user.oicsch.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Bimcalender extends Fragment {

    File SDCardRoot;
    private String[] sem;
    private ArrayList<String> firebasesemisterBIM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File SDCardRoot1 = Environment.getExternalStorageDirectory();
        File file = new File(SDCardRoot1.getAbsolutePath() + "/" + "Android/data/com.example.user.oicsch", "SEMISTER.csv");
        firebasesemisterBIM = new ArrayList<>();
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String show;
                while ((show = bufferedReader.readLine()) != null) {
                    String[] seperate = show.split(",");
                    if (seperate[0].equals("Bim")) {
                        firebasesemisterBIM.add(seperate[1]);

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sem = new String[firebasesemisterBIM.size()];
        for (int i = 0; i < firebasesemisterBIM.size(); i++) {
            sem[i] = firebasesemisterBIM.get(i);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sunday, container, false);
        RecyclerView rev = (RecyclerView) view.findViewById(R.id.rv);
        rev.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rev.setLayoutManager(llm);
        SyllabusRVAdapter adapter = new SyllabusRVAdapter(getContext(), sem, new SyllabusRVAdapter.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position, String tag) {
                switch (tag) {
                    case "FIRST(I)":
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bim(First)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;

                        break;
                    case "SECOND(II)":

                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bim(Second)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case "THIRD(III)":
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bim(Third)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case "FOURTH(IV)":
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bim(Fourth)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case "FIFTH(V)":
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bim(Fifth)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case "SIXTH(VI)":

                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bim(Sixth)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case "SEVENTH(VII)":

                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bim(Seventh)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case "EIGHTH(VIII)":

                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bim(Eighth)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    default:

                        break;

                }
                if(SDCardRoot!=null)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(SDCardRoot), "application/pdf");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "No calender available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rev.setItemAnimator(new DefaultItemAnimator());
        rev.setAdapter(adapter);

        return view;
    }

}



