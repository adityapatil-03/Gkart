package com.example.gkart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class admin_order_adapter extends RecyclerView.Adapter<admin_order_adapter.viewHolder> {
    Context c;
    ArrayList<String> dates,username,mode,total;

    public admin_order_adapter(Context c, ArrayList<String> dates, ArrayList<String> username, ArrayList<String> mode, ArrayList<String> total) {
        this.c = c;
        this.dates = dates;
        this.username = username;
        this.mode = mode;
        this.total = total;
    }

    @NonNull
    @Override
    public admin_order_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.admin_order_details,parent,false);
        return new admin_order_adapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        int x = position+1;
        String s = "Order no: " + x;
        holder.order_no.setText(s);
        String d = "Date: " + dates.get(position);
        holder.date.setText(d);
        //holder.a_total.setText(total.get(position));
        String name = "Name :" + username.get(position);
        holder.username_x.setText(name);
        String md = "Status: "+mode.get(position);
        holder.a_mode.setText(md);
        if(mode.get(position).equals("Paid")){
            holder.a_mode.setTextColor(Color.GREEN);
        }
        else{
            holder.a_mode.setTextColor(Color.RED);

        }
        String t = "Total: " + total.get(position);
        holder.a_total.setText(t);
        holder.see.setTag(dates.get(position));
    }


    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView date,order_no,username_x,a_total,a_mode;
        Button see;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            order_no = itemView.findViewById(R.id.order_no);
            see = itemView.findViewById(R.id.see_details);
            username_x = itemView.findViewById(R.id.username);
            a_total = itemView.findViewById(R.id.a_total);
            a_mode = itemView.findViewById(R.id.a_mode);
        }

        @Override
        public void onClick(View view) {


        }
    }


}
