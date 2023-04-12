package com.example.gkart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class orderd_adapter extends RecyclerView.Adapter<orderd_adapter.viewholder>{
    Context context;
    ArrayList<model> o_products;
    final String rupee = "₹ ";

    public orderd_adapter(Context context, ArrayList<model> o_products) {
        this.context = context;
        this.o_products = o_products;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.order_details_card,parent,false);

        return new orderd_adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.name.setText( o_products.get(position).getName());
        holder.price.setText(rupee+o_products.get(position).getPrice());
        int i = o_products.get(position).getQuantity();
        String s = "Quantity: " + i;
        holder.quantity.setText(s);
        Glide.with(holder.img.getContext()).load(o_products.get(position).getImage()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return o_products.size();
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView price,name,quantity;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.order_image);
            price = (TextView) itemView.findViewById(R.id.order_product_price);
            name = (TextView) itemView.findViewById(R.id.order_product_name);
            quantity = (TextView) itemView.findViewById(R.id.o_product_quantity) ;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }
}
