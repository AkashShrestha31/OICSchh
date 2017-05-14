package com.example.user.oicsch;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.oicsch.Adapter.ViewPagerAdapter;
import com.example.user.oicsch.sectionB.FridayB;
import com.example.user.oicsch.sectionB.MondayB;
import com.example.user.oicsch.sectionB.SundayB;
import com.example.user.oicsch.sectionB.ThrusdayB;
import com.example.user.oicsch.sectionB.TuesdayB;
import com.example.user.oicsch.sectionB.WednesdayB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MasterFragment_sectionB extends Fragment {
String currentDate,dayName;

int i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master, container, false);
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
        Log.d("string", "This is what i get " + tabLayout.getSelectedTabPosition());
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
        } catch (Exception ignored)
        {

        }
        return view;
    }

    private void setupViewPager(ViewPager viewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), this.getContext());
        adapter.addFragmeent(new SundayB(), "Sun");
        adapter.addFragmeent(new MondayB(), "Mon");
        adapter.addFragmeent(new TuesdayB(), "Tue");
        adapter.addFragmeent(new WednesdayB(), "Wed");
        adapter.addFragmeent(new ThrusdayB(), "Thr");
        adapter.addFragmeent(new FridayB(), "Fri");
        viewpager.setAdapter(adapter);
    }
}
