package com.example.gkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class add_product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] cate = { "bakery", "beverages",
            "dry goods" , "oils","others","personal care","pulses and grains","soaps and detergents","spices","stationary"};
    Spinner categories;
    String selected_category;
    DatabaseReference database;
    private ProgressDialog progressDialog;

    TextView result;

    EditText name,price,image,stock;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        progressDialog  = new ProgressDialog(this);

        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        categories = findViewById(R.id.catspinner);
        categories.setOnItemSelectedListener(this);
        result = findViewById(R.id.result);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cate);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        categories.setAdapter(ad);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),
                        cate[i],
                        Toast.LENGTH_LONG)
                .show();
        selected_category = cate[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void add_to_database(View view){
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        stock = findViewById(R.id.stock);
        database = FirebaseDatabase.getInstance().getReference();
        String s = name.getText().toString();
        String p = price.getText().toString();
        String img = image.getText().toString();
        String stk = stock.getText().toString();



        if(s.length()==0||p.length()==0||img.length()==0||stk.length()==0){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setMessage("Adding New Product...");
            progressDialog.show();
       DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("products").child(selected_category);
            final int[] f = {0};
            final boolean[] flag = {true};
            ref.orderByChild("name").equalTo(s).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()){
                        database = FirebaseDatabase.getInstance().getReference().child("products").child(selected_category).child(s);

                        database.child("name").setValue(s);
                        database.child("price").setValue(p);
                        database.child("image").setValue(img);
                        database.child("stock").setValue(stk);

                        result.setText("Product Added Successfully ");
                        result.setTextColor(Color.GREEN);
                        Log.d("pranav", "onDataChange: exists" + snapshot.getValue());
                        f[0] = 1;
                        name.setText("");
                        price.setText("");
                        image.setText("");
                        stock.setText("");

                    }
                    else if(snapshot.exists()&& f[0] !=1){
                        Log.d("pranav", "onDataChange: not exists" + snapshot.getValue());

                        result.setText("Product Already Exists");
                        result.setTextColor(Color.RED);
                    }
                    progressDialog.dismiss();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });



        }
    }

}