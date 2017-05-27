package com.example.user.oicsch.Adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private final ArrayList<Fragment> mfragment=new ArrayList<>();
    private final ArrayList<String> Title=new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return mfragment.get(position);
    }

public void addFragmeent(Fragment fragment, String title)
{
    mfragment.add(fragment);
    Title.add(title);
}
    @Override
        public int getCount() {
            return mfragment.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {

        return Title.get(position);
    }
}

