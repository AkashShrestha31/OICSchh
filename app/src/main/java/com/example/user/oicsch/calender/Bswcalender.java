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


public class Bswcalender extends Fragment {

    File SDCardRoot;
    private String[] sem={"First Year","Second Year","Third Year"};
    private ArrayList<String> firebasesemisterBSW;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bsw(First)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case "SECOND(II)":

                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bsw(Second)Calender.pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case "THIRD(III)":
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Calender/Bsw(Third)Calender.pdf");
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
