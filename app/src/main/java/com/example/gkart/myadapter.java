package com.example.gkart;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.viewholder>{

    Context context;
    ArrayList<model> products;

    public myadapter(Context context, ArrayList<model> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        Log.d("pranav", "onCreateViewHolder: ");
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.name.setText( products.get(position).getName());
        holder.price.setText(products.get(position).getPrice());
        Glide.with(holder.img.getContext()).load(products.get(position).getImage()).into(holder.img);
//        Log.d("pranav", "getItemCount: "+position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView  img;
        TextView price,name;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.product_image);
            price = (TextView) itemView.findViewById(R.id.product_price);
            name = (TextView) itemView.findViewById(R.id.product_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }
}
