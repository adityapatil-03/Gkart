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
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderReview extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<String> dates;
    RecyclerView rv;
    order_adapter od;
    ValueEventListener listenr;

    private ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review);

        progressDialog  = new ProgressDialog(this);

//        SharedPreferences switchState = getSharedPreferences("userdetails",MODE_PRIVATE);
//        String emailid = switchState.getString("emailid","default").split("@",2)[0];


        dates = new ArrayList<>();

        progressDialog.setMessage("Connecting to our database...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("admin");
        listenr=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    HashMap h = new HashMap();
                    h = (HashMap) snapshot1.getValue();
//                    Log.d("pranav", "onDataChange: " );
                    if(h.get("date")!=null) {
                        dates.add(h.get("date").toString());
                    }
//                    else{
//                        Toast.makeText(OrderReview.this, "No Data Available", Toast.LENGTH_SHORT).show();
//                    }
                }
                progressDialog.dismiss();
                od.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        rv = findViewById(R.id.orders);
        rv.setLayoutManager(new LinearLayoutManager(this));
        od = new order_adapter(OrderReview.this,dates);

        rv.setAdapter(od);




    }

    public void seedetails(View view){
        String childname = view.getTag().toString();
        Intent intent = new Intent(OrderReview.this,ProcessOrder.class);
        intent.putExtra("o_date",childname);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.removeEventListener(listenr);
    }
}