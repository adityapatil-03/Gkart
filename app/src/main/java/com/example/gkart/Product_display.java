package com.example.gkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("products").child("oils");

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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Log.d("pranav", "onCreate: "+products.size());
        adapter = new myadapter(Product_display.this,products);
        recyclerView.setAdapter(adapter);
//        FirebaseRecyclerOptions<model> options =
//                new FirebaseRecyclerOptions.Builder<model>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").child("oils"), model.class)
//                        .build();
//        adapter = new myadapter(options);
//        recyclerView.setAdapter(adapter);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}