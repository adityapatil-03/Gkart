package com.example.gkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Product_display extends AppCompatActivity {
    RecyclerView recyclerView;
    myadapter adapter;
    DatabaseReference databaseReference;
    ArrayList<model> products;
    private ProgressDialog progressDialog;
    Button cart;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog  = new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<>();
         intent = getIntent();
        progressDialog.setMessage("Connecting to our database...");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("products").child(intent.getExtras().getString("category"));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    HashMap h = new HashMap();
                    h = (HashMap)snapshot1.getValue();
                    model x = new model(h);
                    products.add(x);
                    Log.d("pranav", "onDataChange: " +x.getName());
                }
                Log.d("pranav", "onDataChange: "+products.size());
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Log.d("pranav", "onCreate: "+products.size());
        adapter = new myadapter(Product_display.this,products);
        recyclerView.setAdapter(adapter);



    }

    public void Cartclick(View view){
        Log.d("pranav", "Cartclick:  " + view.getTag().toString() + " " +intent.getExtras().getString("category") );
    }



}