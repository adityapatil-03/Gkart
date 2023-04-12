package com.example.gkart;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class cart extends AppCompatActivity {

    cart_database db;
    private RecyclerView cart_display;
    private cart_adapter c_a;
    private ArrayList<model> cart_products;
    TextView total_amount;
    final String rupee = "₹ ";
    DatabaseReference database;

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

    public Integer cleardb(){
        return db.deleteall();
    }


    public void placeorder(View view) {
        database = FirebaseDatabase.getInstance().getReference();
//        database.push().setValue(1);
        Cursor c = db.getdata();
        SharedPreferences switchState = getSharedPreferences("userdetails",MODE_PRIVATE);
        String emailid = switchState.getString("emailid","default").split("@",2)[0];
        DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
//        StringBuilder emailid = email
        


        while (c.moveToNext()){
            String s = c.getString(4);
            int i=Integer.parseInt(s);
            model m = new model(c.getString(0),c.getString(1),c.getString(2),c.getString(3),i);
            Log.d("adi", "placeorder: "+date);
            database.child("orders").child(emailid).child(date).child("products").push().setValue(m);
            database.child("orders").child(emailid).child(date).child("date").setValue(date);
        }
        Integer res = cleardb();
        if(res>0) Toast.makeText(this, "Order sent successfully...", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Empty cart...", Toast.LENGTH_SHORT).show();
        finish();
    }
}