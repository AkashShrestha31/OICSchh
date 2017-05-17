package com.example.user.oicsch.News;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.oicsch.R;
import com.example.user.oicsch.webview;

import java.util.ArrayList;

class newadapter extends RecyclerView.Adapter<newadapter.myviewHolder> {
    private ArrayList<getResponseData> data;
    private ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    newadapter(ArrayList<getResponseData> data, Context context) {
        sharedPreferences=context.getSharedPreferences("url",Context.MODE_PRIVATE);
     editor=sharedPreferences.edit();
        this.context = context;
        this.data = data;


    }

    class myviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView i;
        TextView v2, v3, v4,pagename;

        myviewHolder(View itemView) {
            super(itemView);
            CardView cardView=(CardView)itemView.findViewById(R.id.card_view1);
            cardView.setOnClickListener(this);
            i = (ImageView) itemView.findViewById(R.id.display_image);
            v2 = (TextView) itemView.findViewById(R.id.textView4);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/robotofordisplay.ttf");
            v2.setTypeface(custom_font);
            v3 = (TextView) itemView.findViewById(R.id.textView5);
            v3.setTypeface(custom_font);
            v4 = (TextView) itemView.findViewById(R.id.textView6);
            v4.setTypeface(custom_font);
            pagename=(TextView)itemView.findViewById(R.id.Pagename);
            pagename.setTypeface(custom_font);
        }

        @Override
        public void onClick(View v) {
            Log.d("fuck",""+getAdapterPosition());
            editor.putString("url",""+data.get(getAdapterPosition()).getUrl());
            editor.apply();
            Intent newIntent = new Intent(context, webview.class);
            context.startActivity(newIntent);
           // Toast.makeText(context, ""+sharedPreferences.getString("url",""), Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public myviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        final CardView cardView = (CardView) view.findViewById(R.id.card_view1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "cardview is click"+v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(myviewHolder holder, int position) {

        holder.v2.setText(data.get(position).getDescription());
        holder.v3.setText(data.get(position).getAuthor());
        holder.v4.setText(data.get(position).getPublishedAt());
        holder.pagename.setText(data.get(position).getPagename());
        Glide.clear(holder.i);
        Glide.with(holder.i.getContext())
                .load(data.get(position).getUrlToImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.i);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


}
