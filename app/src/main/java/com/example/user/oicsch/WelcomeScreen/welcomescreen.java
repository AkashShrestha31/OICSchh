package com.example.user.oicsch.WelcomeScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.oicsch.Adapter.ViewPagerAdapter;
import com.example.user.oicsch.MainActivity;
import com.example.user.oicsch.R;

public class welcomescreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);
        SharedPreferences sharedPreferences = getSharedPreferences("opt", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        TabLayout tabLayout=(TabLayout) findViewById(R.id.welcometablayout);
        ViewPager viewPager=(ViewPager)findViewById(R.id.welcomeviewpager);
        Button button=(Button)findViewById(R.id.startbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("check","start");
                editor.apply();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(welcomescreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },0);
            }
        });
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),this);
        viewPagerAdapter.addFragmeent(new WelcomeFragment1(),"");
        viewPagerAdapter.addFragmeent(new WelcomeFragment2(),"");
        viewPagerAdapter.addFragmeent(new WelcomeFragment3(),"");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);

    }



}