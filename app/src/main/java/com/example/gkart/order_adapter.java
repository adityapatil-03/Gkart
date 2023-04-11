package com.example.gkart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class order_adapter extends RecyclerView.Adapter<order_adapter.viewholder>{

    Context c;
    ArrayList<String> dates;

    public order_adapter(Context c, ArrayList<String> dates) {
        this.c = c;
        this.dates = dates;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.order_card,parent,false);
        return new order_adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        int x = position+1;
        String s = "Order no: " + x;
          holder.order_no.setText(s);
          String d = "Date: " + dates.get(position);
          holder.date.setText(d);

          holder.see.setTag(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView date,order_no;
        Button see;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            order_no = itemView.findViewById(R.id.order_no);
            see = itemView.findViewById(R.id.see_details);
        }

        @Override
        public void onClick(View view) {


        }
    }
}
