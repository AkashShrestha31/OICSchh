package com.example.user.oicsch.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.oicsch.R;

/**
 * Created by Aakash on 7/30/2016.
 */
public class SyllabusRVAdapter extends RecyclerView.Adapter<SyllabusRVAdapter.MyViewHolder> {
    private final String[] array;
    private final Context context;
    private final OnCardClickListner onCardClickListner;
    public SyllabusRVAdapter(Context context, String[] array,OnCardClickListner onCardClickListner) {
        this.context=context;
        this.array=array;
        this.onCardClickListner=onCardClickListner;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        final TextView Stext;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            //cardView=(CardView)view.findViewById(R.id.syllabuslayout);
            Stext=(TextView)view.findViewById(R.id.syllabustext);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/robotofordisplay.ttf");
            Stext.setTypeface(custom_font);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.syllabuslayoutcontent, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
     holder.Stext.setText(array[position]);
        Log.d("pp",""+holder.Stext.getText());
        holder.Stext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pp",""+holder.Stext.getText());
                onCardClickListner.OnCardClicked(v,position,holder.Stext.getText().toString());
            }
        });


    }


    @Override
    public int getItemCount() {
        return array.length;
    }
    public interface OnCardClickListner {
       void OnCardClicked(View view, int position, String tag);
    }


}
