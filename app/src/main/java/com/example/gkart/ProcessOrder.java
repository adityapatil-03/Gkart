package com.example.gkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProcessOrder extends AppCompatActivity {
    Intent intent;
    DatabaseReference databaseReference;
    DatabaseReference database;
    ArrayList<model> o_products;
    ArrayList<String> unprocssed;
    orderd_adapter o_d;
    RecyclerView rv_o;
    ProgressDialog progressDialog;
    ValueEventListener listenr,listenr1;
    Button process_now;



    Button process_it;
    public Integer stock;
    final String TAG = "Pranav";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_order);
        process_now = findViewById(R.id.process_order_now);

        o_products = new ArrayList<>();
        intent = getIntent();
        rv_o = findViewById(R.id.products);
        progressDialog  = new ProgressDialog(this);
        process_it = findViewById(R.id.process_order_now);
        unprocssed = new ArrayList<>();
        SharedPreferences switchState = getSharedPreferences("userdetails",MODE_PRIVATE);
        String emailid = switchState.getString("emailid","default").split("@",2)[0];

        String date1 = intent.getExtras().getString("o_date");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("admin").child(date1).child("products");
        listenr=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum = 0;
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    HashMap h = new HashMap();
                    h = (HashMap)snapshot1.getValue();
                    model x = new model(h,"abc");
                    String s = h.get("quantity").toString();
                    int i = Integer.parseInt(s);
                    x.setQuantity(i);
                    o_products.add(x);
                    int p = Integer.parseInt(x.getPrice());
                    sum = sum +  p*i;
                }
                String s = "Total: " + sum;
                o_d.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        rv_o.setLayoutManager(new LinearLayoutManager(this));
        o_d = new orderd_adapter(this,o_products);
        rv_o.setAdapter(o_d);
    }
    public void processNow(View view){
        ArrayList<Integer> q = new ArrayList<>();
        for (model x:o_products) {
//            progressDialog.show();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("products").child(x.category).child(x.name).child("stock");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "onDataChange: " );
                    String y = snapshot.getValue().toString();

                    int st = Integer.parseInt(y) - x.quantity;
                    if(st<0){
                        unprocssed.add(x.getName());
                        Log.d(TAG, "onDataChange: "+unprocssed.toString());
                    }
                    else {
                        databaseReference.setValue(st);
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        Handler handler = new Handler();

        // Create a Runnable that calls the doSomething() method
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                process_now.setEnabled(false);

                progressDialog.dismiss();

            }
        };

        // Post the Runnable with a delay
        handler.postDelayed(runnable, 3000);






        progressDialog.setMessage("processing please wait");
        progressDialog.show();


    }




    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.removeEventListener(listenr);
    }

    public void delete_order(View view) {
        database = FirebaseDatabase.getInstance().getReference().child("admin");
        intent = getIntent();
        String date1 = intent.getExtras().getString("o_date");

        DatabaseReference newdb = FirebaseDatabase.getInstance().getReference().child("admin").child(date1).child("username");
        newdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("pranav", "onDataChange:ljgfkhgvb " + unprocssed.toString());
                DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("notifications").child(snapshot.getValue().toString());
                database1.setValue("Your order placed on\n"+date1+"\n"+" is processed except "+unprocssed.toString());
                        database.child(date1).removeValue();
                Toast.makeText(ProcessOrder.this,"Order processed successfully...",Toast.LENGTH_SHORT);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}