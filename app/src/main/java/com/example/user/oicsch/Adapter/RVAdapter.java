package com.example.user.oicsch.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.oicsch.R;
import com.example.user.oicsch.schedule;

import java.util.List;

/**
 * Created by Aakash on 7/30/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {
    private final List<schedule> array;
    private final Context context;
    public RVAdapter(Context context, List<schedule> array) {
        this.context=context;
        this.array=array;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final CardView cv;
        final TextView Stime;
        final TextView Etime;
        final TextView subject;

        public MyViewHolder(View view) {
            super(view);
            cv=(CardView) view.findViewById(R.id.card_view);
            Typeface custom_font1 = Typeface.createFromAsset(context.getAssets(),  "fonts/robotofordisplay.ttf");
            Stime=(TextView)view.findViewById(R.id.text1);
            Stime.setTypeface(custom_font1);
            Etime=(TextView)view.findViewById(R.id.text2);
            Etime.setTypeface(custom_font1);
           subject=(TextView)view.findViewById(R.id.text3);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/robotofordisplay.ttf");
            subject.setTypeface(custom_font);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_content, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
     holder.Stime.setText(array.get(position).getITime());
        holder.Etime.setText(array.get(position).getFTime());
        holder.subject.setText(array.get(position).getsubject());
        String s=array.get(position).getITime();
        String[] get=s.split(":");
        int st=Integer.parseInt(get[0]);
        holder.Stime.setBackgroundResource(R.drawable.circleshape);
        holder.Etime.setBackgroundResource(R.drawable.circleshape);
       /* switch(st)
        {
            case 6:
                holder.Stime.setBackgroundResource(R.drawable.circleshape);
                //holder.Etime.setBackgroundResource(R.drawable.circleshape);
                break;
            case 7:
                holder.Stime.setBackgroundResource(R.drawable.circleshape1);
               // holder.Etime.setBackgroundResource(R.drawable.circleshape1);
                break;
            case 8:
                holder.Stime.setBackgroundResource(R.drawable.circleshape2);
               // holder.Etime.setBackgroundResource(R.drawable.circleshape2);
                break;
            case 9:
                holder.Stime.setBackgroundResource(R.drawable.circleshape3);
               // holder.Etime.setBackgroundResource(R.drawable.circleshape3);
                break;
            case 10:
                holder.Stime.setBackgroundResource(R.drawable.circleshape4);
               // holder.Etime.setBackgroundResource(R.drawable.circleshape4);
                break;
            case 11:
                holder.Stime.setBackgroundResource(R.drawable.circleshape5);
               // holder.Etime.setBackgroundResource(R.drawable.circleshape5);
                break;
            case 12:
                holder.Stime.setBackgroundResource(R.drawable.circleshape6);
               // holder.Etime.setBackgroundResource(R.drawable.circleshape6);
                break;
            case 1:
                holder.Stime.setBackgroundResource(R.drawable.circleshape7);
                //holder.Etime.setBackgroundResource(R.drawable.circleshape7);
                break;
            case 2:
                holder.Stime.setBackgroundResource(R.drawable.circleshape8);
               // holder.Etime.setBackgroundResource(R.drawable.circleshape8);
                break;
            case 3:
                holder.Stime.setBackgroundResource(R.drawable.circleshape9);
              //  holder.Etime.setBackgroundResource(R.drawable.circleshape9);
                break;
            case 4:
                holder.Stime.setBackgroundResource(R.drawable.circleshape10);
                //holder.Etime.setBackgroundResource(R.drawable.circleshape10);
                break;
        }*/
        String f=array.get(position).getFTime();
        String[] getf=f.split(":");
        int ft=Integer.parseInt(getf[0]);

       /* switch(ft)
        {
            case 6:
                holder.Etime.setBackgroundResource(R.drawable.circleshape);
                break;
            case 7:
                 holder.Etime.setBackgroundResource(R.drawable.circleshape1);
                break;
            case 8:
                holder.Etime.setBackgroundResource(R.drawable.circleshape2);
                break;
            case 9:
                holder.Etime.setBackgroundResource(R.drawable.circleshape3);
                break;
            case 10:
                 holder.Etime.setBackgroundResource(R.drawable.circleshape4);
                break;
            case 11:
                holder.Etime.setBackgroundResource(R.drawable.circleshape5);
                break;
            case 12:
                holder.Etime.setBackgroundResource(R.drawable.circleshape6);
                break;
            case 1:
               holder.Etime.setBackgroundResource(R.drawable.circleshape7);
                break;
            case 2:
                holder.Etime.setBackgroundResource(R.drawable.circleshape8);
                break;
            case 3:
                  holder.Etime.setBackgroundResource(R.drawable.circleshape9);
                break;
            case 4:
                holder.Etime.setBackgroundResource(R.drawable.circleshape10);
                break;
        }*/
        /*if(st==1)

        log.message(""+array.get(position).getsubject()+" "+st);*/


    }


    @Override
    public int getItemCount() {
        return array.size();
    }
}
