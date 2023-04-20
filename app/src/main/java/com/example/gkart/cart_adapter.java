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

public class cart_adapter extends RecyclerView.Adapter<cart_adapter.Viewholder>{
    Context c;
    ArrayList<model> cp;
    final String rupee = "₹ ";

    public cart_adapter(Context c, ArrayList<model> cp) {
        this.c = c;
        this.cp = cp;
    }



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.cart_card,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.name.setText( cp.get(position).getName());

        int i=Integer.parseInt(cp.get(position).getPrice());

        int price  = i*cp.get(position).getQuantity();

        String fin_price = "";
        fin_price += price;

        holder.price.setText(rupee + fin_price);
        Glide.with(holder.img.getContext()).load(cp.get(position).getImage()).into(holder.img);
        holder.delete.setTag(cp.get(position).getC_id());
        holder.minus.setTag(cp.get(position).getC_id());
        holder.plus.setTag(cp.get(position).getC_id());
        Log.d("pranav", "onBindViewHolder: " +cp.get(position).getQuantity() );
        int  quan = cp.get(position).getQuantity();
        String quant = "";
        quant += quan;
        holder.quantity.setText(quant);
    }

    @Override
    public int getItemCount() {
        return cp.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView price,name,quantity;

        Button delete,minus,plus;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.cart_image);
            price = (TextView) itemView.findViewById(R.id.cart_price);
            name = (TextView) itemView.findViewById(R.id.cart_name);
            delete = itemView.findViewById(R.id.delete);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            quantity = itemView.findViewById(R.id.quantity);
        }

        @Override
        public void onClick(View view) {


        }
    }
}
