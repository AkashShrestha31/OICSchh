package com.example.user.oicsch.WelcomeScreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.oicsch.R;


public class WelcomeFragment2 extends Fragment {


    @Override
    public void onResume() {
        super.onResume();
        Log.d("line", "fuckafafaf");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*SharedPreferences checkstart = getActivity().getSharedPreferences("start", Context.MODE_PRIVATE);
        SharedPreferences.Editor checkeditor = checkstart.edit();
        checkeditor.putString("faculty","Bsc Csit");
                checkeditor.putString("semister","0");
        checkeditor.putString("section","0");
        checkeditor.apply();*/

        return inflater.inflate(R.layout.fragment_welcome_fragment2, container, false);
    }


}

