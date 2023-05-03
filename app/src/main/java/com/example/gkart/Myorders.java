package com.example.gkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Myorders extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<String> dates;
    RecyclerView rv;
    order_adapter od;
    private ProgressDialog progressDialog;
    ValueEventListener listenr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        progressDialog  = new ProgressDialog(this);

        SharedPreferences switchState = getSharedPreferences("userdetails",MODE_PRIVATE);
        String emailid = switchState.getString("emailid","default").split("@",2)[0];


        dates = new ArrayList<>();

        progressDialog.setMessage("Connecting to our database...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child(emailid);
            listenr=databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        HashMap h = new HashMap();
                        h = (HashMap) snapshot1.getValue();
                        Log.d("pranav", "onDataChange: " );
                        if(h.get("date")!=null) {
                            dates.add(h.get("date").toString());
                        }
                        else{
                            Toast.makeText(Myorders.this, "No Data Available", Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                    od.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        rv = findViewById(R.id.order);
        rv.setLayoutManager(new LinearLayoutManager(this));
        od = new order_adapter(Myorders.this,dates);

        rv.setAdapter(od);




    }

    public void seedetails(View view){
        String childname = view.getTag().toString();
        Intent intent = new Intent(Myorders.this,order_details.class);
        intent.putExtra("o_date",childname);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.removeEventListener(listenr);
    }
}