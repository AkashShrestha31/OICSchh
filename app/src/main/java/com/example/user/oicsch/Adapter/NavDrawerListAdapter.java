package com.example.user.oicsch.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.oicsch.Nav_drawer_item;
import com.example.user.oicsch.R;
import com.example.user.oicsch.navimage;

import java.util.ArrayList;

/**
 * Created by Aakash on 8/3/2016.
 */
public class NavDrawerListAdapter extends BaseAdapter {
    Context context;
    Drawable res;
            ArrayList<Nav_drawer_item> draweritem=new ArrayList<>();
    ArrayList<navimage> navigationimage=new ArrayList<>();
public NavDrawerListAdapter(Context context, ArrayList<Nav_drawer_item> draweritem, ArrayList<navimage> navigationimage)
{
    this.context=context;
    this.draweritem=draweritem;
    this.navigationimage=navigationimage;
}

    @Override
    public int getCount() {
        return draweritem.size();
    }

    @Override
    public Object getItem(int position) {
        return draweritem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflter=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=inflter.inflate(R.layout.navitemlayout,null);
        }
        ImageView imageView=(ImageView)convertView.findViewById(R.id.navimage);
        TextView tv=(TextView) convertView.findViewById(R.id.title);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/robotofordisplay.ttf");
        tv.setTypeface(custom_font);
        tv.setText(draweritem.get(position).gettitle());
        imageView.setImageDrawable(navigationimage.get(position).getDrawerimage());
        return  convertView;
    }
}
