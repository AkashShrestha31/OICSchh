package com.example.user.oicsch.syllabus;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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

import java.io.File;


public class BscCsit extends Fragment {
    private File SDCardRoot;

    private String[] sem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sem = getResources().getStringArray(R.array.semister);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sunday, container, false);
        RecyclerView rev = (RecyclerView) view.findViewById(R.id.rv);
        rev.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rev.setLayoutManager(llm);
        SyllabusRVAdapter adapter = new SyllabusRVAdapter(getContext(), sem, new SyllabusRVAdapter.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position, String tag) {
                switch (position) {
                    case 0:
                       SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Syllabus/BscCsit(First).pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case 1:
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Syllabus/BscCsit(Second).pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case 2:
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Syllabus/BscCsit(Third).pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case 3:
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Syllabus/BscCsit(Fourth).pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case 4:
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Syllabus/BscCsit(Fifth).pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case 5:
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Syllabus/BscCsit(Sixth).pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case 6:
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Syllabus/BscCsit(Seventh).pdf");
                        if(!SDCardRoot.exists())
                            SDCardRoot=null;
                        break;
                    case 7:
                        SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/Syllabus/BscCsit(Eight).pdf");
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
                    Toast.makeText(getActivity(), "No Syllabus available", Toast.LENGTH_SHORT).show();
                };
            }
        });
        rev.setItemAnimator(new DefaultItemAnimator());
        rev.setAdapter(adapter);

        return view;
    }


}
