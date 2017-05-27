package com.example.user.oicsch;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.oicsch.Adapter.ViewPagerAdapter;
import com.example.user.oicsch.sectionA.FridayA;
import com.example.user.oicsch.sectionA.MondayA;
import com.example.user.oicsch.sectionA.SundayA;
import com.example.user.oicsch.sectionA.ThrusdayA;
import com.example.user.oicsch.sectionA.TuesdayA;
import com.example.user.oicsch.sectionA.WednesdayA;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MasterFragment_sectionA extends Fragment {
    private View view;
private String dayName;
private String currentDate;
    private int i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_master, container, false);
        ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewpager);

       // Toolbar tb = (Toolbar) view.findViewById(R.id.view);
       // tb.setTitle("CManger");
        Calendar calendar = Calendar.getInstance();
        //date format is:  "Date-Month-Year Hour:Minutes am/pm"
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm a"); //Date and time
        currentDate = sdf.format(calendar.getTime());
        //Day of Name in full form like,"Saturday", or if you need the first three characters you have to put "EEE" in the date format and your result will be "Sat".
        SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        Date date = new Date();
        dayName = sdf_.format(date);
         if (dayName.equals("Sunday")) {
            i = 0;

        }
        if (dayName.equals("Monday")) {
            i = 1;

        }
        if (dayName.equals("Tuesday")) {
            i = 2;

        }
        if (dayName.equals("Wednesday")) {
            i = 3;

        }
        if (dayName.equals("Thursday")) {
            i = 4;

        }
        if (dayName.equals("Friday")) {
            i = 5;

        }
        if (dayName.equals("Saturday")) {
            i = 6;
        }
        try {
            tabLayout.getTabAt(i).select();
        }
        catch(Exception ignored) {}
        return view;
    }

    private void setupViewPager(ViewPager viewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), this.getContext());
        adapter.addFragmeent(new SundayA(), "Sun");
        adapter.addFragmeent(new MondayA(), "Mon");
        adapter.addFragmeent(new TuesdayA(), "Tue");
        adapter.addFragmeent(new WednesdayA(), "Wed");
        adapter.addFragmeent(new ThrusdayA(), "Thr");
        adapter.addFragmeent(new FridayA(), "Fri");
        viewpager.setAdapter(adapter);
    }
}
