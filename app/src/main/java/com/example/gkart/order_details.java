package com.example.gkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class order_details extends AppCompatActivity {

    Intent intent;
    DatabaseReference databaseReference;
    ArrayList<model> o_products;
    orderd_adapter o_d;
    RecyclerView rv_o;
    TextView o_t;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        o_products = new ArrayList<>();
        intent = getIntent();
        o_t = findViewById(R.id.o_total_amount);
        rv_o = findViewById(R.id.orderd);

        SharedPreferences switchState = getSharedPreferences("userdetails",MODE_PRIVATE);
        String emailid = switchState.getString("emailid","default").split("@",2)[0];

        String date1 = intent.getExtras().getString("o_date");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child(emailid).child(date1).child("products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum = 0;
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    HashMap h = new HashMap();
                    h = (HashMap)snapshot1.getValue();
                    model x = new model(h);
                    String s = h.get("quantity").toString();
                    int i = Integer.parseInt(s);
                    x.setQuantity(i);
                    o_products.add(x);
                    int p = Integer.parseInt(x.getPrice());
                    sum = sum +  p*i;
                    Log.d("pranav", "onDataChange: " + snapshot1.getValue());
                }
                String s = "Total: " + sum;
                o_t.setText(s);
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
}