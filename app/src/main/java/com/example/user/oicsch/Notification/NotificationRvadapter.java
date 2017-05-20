package com.example.user.oicsch.Notification;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.oicsch.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user on 5/15/2017.
 */

public class NotificationRvadapter extends RecyclerView.Adapter<NotificationRvadapter.myviewholer> {
    Context context;
    ArrayList<Notify> notification;
    View Itemview;
    public NotificationRvadapter(ArrayList<Notify> notification,Context context) {
        this.context = context;
        this.notification = notification;
    }

    public class myviewholer extends RecyclerView.ViewHolder {
        TextView title,message,time,date;
        ImageView imageview;
        public myviewholer(View itemView) {
            super(itemView);
           title = (TextView) itemView.findViewById(R.id.notifytitle);
            time=(TextView)itemView.findViewById(R.id.texttime);
            date=(TextView)itemView.findViewById(R.id.textdate);
            message = (TextView) itemView.findViewById(R.id.notifymessage);
            imageview = (ImageView) itemView.findViewById(R.id.notify_image_url);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/robotofordisplay.ttf");
            title.setTypeface(custom_font);
            message.setTypeface(custom_font);
            time.setTypeface(custom_font);
            date.setTypeface(custom_font);
        }
    }

    @Override
    public myviewholer onCreateViewHolder(ViewGroup parent, int viewType) {
        Itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationcard, parent, false);
        return new myviewholer(Itemview);
    }

    @Override
    public void onBindViewHolder(myviewholer holder, int position) {
        holder.title.setText(notification.get(position).getTitle());
        holder.message.setText(notification.get(position).getMessage());
        holder.time.setText(notification.get(position).getTime());
        holder.date.setText(notification.get(position).getDate());
        if(!notification.get(position).getImage_url().isEmpty()) {
            Glide.clear(holder.imageview);
            Glide.with(holder.imageview.getContext())
                    .load(notification.get(position).getImage_url())
                    .placeholder(android.R.color.darker_gray)
                    .into(holder.imageview);
        }
        else
        {
            /*LinearLayout Layout=(LinearLayout) Itemview.findViewById(R.id.notificationlayout);
           ViewGroup.LayoutParams layoutParams=Layout.getLayoutParams();
            layoutParams.height=LinearLayout.LayoutParams.WRAP_CONTENT;
            Layout.setLayoutParams(layoutParams);*/
           /* holder.imageview.setVisibility(View.GONE);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
          holder.imageview.setLayoutParams(parms);*/
        }
    }

    @Override
    public int getItemCount() {
        return notification.size();
    }


}
