package com.example.gkart;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cart extends AppCompatActivity {

    cart_database db;
    private RecyclerView cart_display;
    private cart_adapter c_a;
    private ArrayList<model> cart_products;
    TextView total_amount;
    final String rupee = "₹ ";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        db = new cart_database(this);
        cart_display = findViewById(R.id.cr);
        total_amount = findViewById(R.id.total_amount);
     //   Cursor c = db.getdata();
        cart_products = new ArrayList<>();

//        while (c.moveToNext()){
//            String s = c.getString(4);
//            int i=Integer.parseInt(s);
//            Log.d("pranav", "onCreate: ");
//            model d = new model(c.getString(0),c.getString(1),c.getString(2),c.getString(3),i);
//            cart_products.add(d);
//        }

         display();
//
//        cart_display.setLayoutManager(new LinearLayoutManager(this));
//
//        c_a = new cart_adapter(cart.this,cart_products);
//        cart_display.setAdapter(c_a);
    }


    public void calculate_total_amount(){
        int total = 0;
        Cursor c = db.getdata();
        while (c.moveToNext()){
            String s = c.getString(4);
            int i=Integer.parseInt(s);

            String price = c.getString(3);
            int pri = Integer.parseInt(price);

            total += (i*pri);

        }

        total_amount.setText("Total: "+rupee + total);

    }

    public void display(){
        Cursor c = db.getdata();

        ArrayList<model> products = new ArrayList<>();
        while (c.moveToNext()){
            String s = c.getString(4);
            int i=Integer.parseInt(s);
            model d = new model(c.getString(0),c.getString(1),c.getString(2),c.getString(3),i);
            products.add(d);
        }
        cart_products = products;
        cart_display.setLayoutManager(new LinearLayoutManager(this));
        calculate_total_amount();
        c_a = new cart_adapter(cart.this,cart_products);
        cart_display.setAdapter(c_a);
    }

    public void delete_product(View view){
        String c_id = view.getTag().toString();

        Integer d = db.delete(c_id);
        if(d>0){
            Toast.makeText(cart.this,"Product deleted sucessfully",Toast.LENGTH_SHORT).show();
        }
        display();
    }

    public void decrement(View view){
        String c_id = view.getTag().toString();

        Cursor cs = db.search(c_id);
        int i = 0;
        while (cs.moveToNext()){
            String s = cs.getString(4);
            i=Integer.parseInt(s);
            i--;

        }
        if(i!=0) {
            boolean dec = db.update(c_id, i);
        }
        else{
            Integer d = db.delete(c_id);
            if(d>0){
                Toast.makeText(cart.this,"Data deleted sucessfully",Toast.LENGTH_SHORT).show();
            }
        }
        display();

    }

    public void increment(View view){
        String c_id = view.getTag().toString();

        Cursor cs = db.search(c_id);
           int i = 0;
        while (cs.moveToNext()){
            String s = cs.getString(4);
             i=Integer.parseInt(s);
            i++;
        }
        boolean dec = db.update(c_id,i);
        display();

    }


}