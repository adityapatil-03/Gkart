package com.example.gkart;

import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    ArrayList<model> products,cart_products;
    private ProgressDialog progressDialog;

    cart_database db;


    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog  = new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<>();
        cart_products = new ArrayList<>();
         intent = getIntent();
        progressDialog.setMessage("Connecting to our database...");
        progressDialog.show();

        db = new cart_database(this);

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
       // Log.d("pranav", "Cartclick:  " + view.getTag().toString() + " " +intent.getExtras().getString("category") );
         String name = view.getTag().toString();
         boolean cs = db.search_name(name,"0");
        if(!cs) {
            String cate = intent.getExtras().getString("category");
            databaseReference = FirebaseDatabase.getInstance().getReference().child("products").child(intent.getExtras().getString("category")).child(view.getTag().toString());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    products.clear();
                    ArrayList<String> product_data = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        product_data.add(snapshot1.getValue().toString());
                        Log.d("pranav", "onDataChange: " + snapshot1.getValue().toString());
                    }
                    boolean c_product = db.insert(product_data.get(1), product_data.get(0), product_data.get(2), 1,cate);
                    if (c_product) {
                        Toast.makeText(Product_display.this, "Product added sucessfully", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(Product_display.this, "Product is already added", Toast.LENGTH_SHORT).show();
        }
    }



}