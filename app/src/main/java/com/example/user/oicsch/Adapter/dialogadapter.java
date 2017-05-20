package com.example.user.oicsch.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.oicsch.R;

import java.util.ArrayList;

/**
 * Created by user on 5/2/2017.
 */

public class dialogadapter extends BaseAdapter {
    ArrayList<String> firebase;
    Context context;
   public dialogadapter(ArrayList<String> firebase,Context context)
    {
        this.firebase=firebase;
        this.context=context;
    }
    @Override
    public int getCount() {
        return firebase.size();
    }

    @Override
    public Object getItem(int position) {
        return firebase.get(position);
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
            convertView=inflter.inflate(R.layout.dialoglayout,null);
        }
        TextView textView=(TextView)convertView.findViewById(R.id.dialoguetext);
        textView.setText(firebase.get(position));
        return convertView;
    }
}
