package com.example.user.oicsch.WelcomeScreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.oicsch.R;


public class WelcomeFragment1 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Button button=(Button)getActivity().findViewById(R.id.startbutton);
        button.setVisibility(View.GONE);
        return inflater.inflate(R.layout.fragment_welcome_fragment1, container, false);
    }


}
