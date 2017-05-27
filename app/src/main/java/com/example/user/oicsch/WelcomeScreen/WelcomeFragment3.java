package com.example.user.oicsch.WelcomeScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.oicsch.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WelcomeFragment3 extends Fragment {
    private ArrayList<String> semisterBSCCSIT;
    private ArrayList<String> semisterBIM;



    private ArrayList<String> semisterBSW;
    private Spinner spinner1;
    private Spinner spinner;
    private Spinner spinner2;
    private ArrayAdapter<String> adapter1;
    private SharedPreferences checkstart;
    private SharedPreferences.Editor checkeditor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_welcome_fragment3, container, false);

        checkstart=getActivity().getSharedPreferences("start",Context.MODE_PRIVATE);
        checkeditor=checkstart.edit();
        semisterBSCCSIT = new ArrayList<>();
        semisterBIM = new ArrayList<>();
        semisterBSW = new ArrayList<>();
        File SDCardRoot1 = Environment.getExternalStorageDirectory();
        File SDCardRoot=new File(SDCardRoot1.getAbsolutePath()+ "/" +"Android/data/com.example.user.oicsch","SEMISTER.csv");
        // File SDCardRoot = new File("/sdcard/Android/data/com.example.user.oicsch/Files/SEMISTER.csv");
        if(SDCardRoot.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(SDCardRoot);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String newline;
                while ((newline = bufferedReader.readLine()) != null) {
                    String[] row = newline.split(",");
                    // Log.d("line",""+row[0]+" and "+row[1]);
                    switch (row[0])
                    {
                        case "Bsc Csit":
                            semisterBSCCSIT.add(row[1]);
                            Log.d("line","csit"+row[1]);
                            break;
                        case "Bim":
                            semisterBIM.add(row[1]);
                            Log.d("line","bim"+row[1]);
                            break;
                        case "Bsw":
                            semisterBSW.add(row[1]);
                            Log.d("line","bsw"+row[1]);
                            break;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getActivity(), "The file doesnot exit", Toast.LENGTH_SHORT).show();
        }
        //Faculty
        String[] spin={"BSC CSIT","BIM","BSW"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.welcometext,spin);
        spinner=(Spinner) view.findViewById(R.id.welcomespinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setSelected(true);
        spinner1=(Spinner)view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        checkeditor.remove("faculty");
                        checkeditor.putString("faculty",""+spinner.getItemAtPosition(position).toString());
                        checkeditor.apply();
                        adapter1=new ArrayAdapter<String>(getContext(),R.layout.welcometext,semisterBSCCSIT);
                        spinner1.setAdapter(adapter1);
                        spinner1.setSelection(0);
                        break;
                    case 1:
                        checkeditor.remove("faculty");
                        checkeditor.putString("faculty",""+spinner.getItemAtPosition(position).toString());
                        checkeditor.apply();
                        adapter1=new ArrayAdapter<String>(getContext(),R.layout.welcometext,semisterBIM);
                        spinner1.setAdapter(adapter1);
                        spinner1.setSelection(0);
                        break;
                    case 2:
                        checkeditor.remove("faculty");
                        checkeditor.putString("faculty",""+spinner.getItemAtPosition(position).toString());
                        checkeditor.apply();
                        adapter1=new ArrayAdapter<String>(getContext(),R.layout.welcometext,semisterBSW);
                        spinner1.setAdapter(adapter1);
                        spinner1.setSelection(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //semister
        // ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getContext(),R.layout.welcometext,semisterBIM);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        checkeditor.remove("semister");
                        checkeditor.putString("semister","0");
                        checkeditor.apply();
                        break;
                    case 1:
                        checkeditor.remove("semister");
                        checkeditor.putString("semister","1");
                        checkeditor.apply();
                        break;
                    case 2:
                        checkeditor.remove("semister");
                        checkeditor.putString("semister","2");
                        checkeditor.apply();
                        break;
                    case 3:
                        checkeditor.remove("semister");
                        checkeditor.putString("semister","3");
                        checkeditor.apply();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //section
        String[] Section={"A","B"};
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getContext(),R.layout.welcometext,Section);
        ;spinner2=(Spinner)view.findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(0);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        checkeditor.remove("section");
                        checkeditor.putString("section","0");
                        checkeditor.apply();
                        break;
                    case 1:
                        checkeditor.remove("section");
                        checkeditor.putString("section","1");
                        checkeditor.apply();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }


}
