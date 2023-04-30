package com.example.gkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class updateproduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] cate = { "bakery", "beverages",
            "dry goods" , "oils","others","personal care","pulses and grains","soaps and detergents","spices","stationary"};
    Spinner u_categories,u_product;

    ArrayList<String> product_list;
    String selected_category,u_p;
    DatabaseReference database;
    private ProgressDialog progressDialog;
    ArrayAdapter<String> ad1;
    TextView name;
    EditText price,image,stock;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog  = new ProgressDialog(this);

        setContentView(R.layout.activity_updateproduct);
        selected_category = "bakery";
        progressDialog  = new ProgressDialog(this);
        product_list = new ArrayList<>();

        name = findViewById(R.id.u_name);
        price = findViewById(R.id.u_price);
        image = findViewById(R.id.u_image);
        stock = findViewById(R.id.u_stock);
        u_categories = findViewById(R.id.u_catspinner);
        u_categories.setOnItemSelectedListener(this);

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


        u_categories.setAdapter(ad);

        u_product = findViewById(R.id.u_product_spinner);
        u_product.setOnItemSelectedListener(this);

          show_produt_list();
    }

    void show_product_details(){
        database = FirebaseDatabase.getInstance().getReference().child("products").child(selected_category);
        if(u_p!=null) {
            database.child(u_p).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        HashMap h = new HashMap<>();
                        h = (HashMap) snapshot.getValue();
                        if(h.get("name")!=null&&h.get("price")!=null&&h.get("image")!=null&&h.get("stock")!=null) {
                            name.setText(h.get("name").toString());
                            price.setText(h.get("price").toString());
                            image.setText(h.get("image").toString());
                            stock.setText(h.get("stock").toString());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }


    void show_produt_list(){

        ArrayAdapter ad1
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                product_list);
        database = FirebaseDatabase.getInstance().getReference().child("products").child(selected_category);

        database.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    product_list.clear();
                    product_list.add("");
                    HashMap h = new HashMap<>();
                    h = (HashMap) snapshot.getValue();
                    assert h != null;
                    Set<String> hs = h.keySet();

                    product_list.addAll(hs);
                    show_product_details();
                    ad1.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ad1.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        u_product.setAdapter(ad1);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.u_catspinner:
                selected_category = cate[i];
                show_produt_list();
                show_product_details();
                name.setText("");
                price.setText("");
                image.setText("");
                stock.setText("");
                break;
            case R.id.u_product_spinner:

                u_p = product_list.get(i);
                break;
        }
        show_product_details();

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void update_product(View view){
        progressDialog.setMessage("Updating Product details...");
        progressDialog.show();
        String s = name.getText().toString(),p=price.getText().toString(),img =image.getText().toString(),stk =stock.getText().toString() ;

        if(s.length()==0||p.length()==0||img.length()==0||stk.length()==0){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
        else {
            database = FirebaseDatabase.getInstance().getReference().child("products");

            database.child(selected_category).child(u_p).child("price").setValue(p);
            database.child(selected_category).child(u_p).child("image").setValue(img);
            database.child(selected_category).child(u_p).child("stock").setValue(stk);
            Toast.makeText(this, "Poduct Updated sucessfully", Toast.LENGTH_SHORT).show();

            name.setText("");
            price.setText("");
            image.setText("");
            stock.setText("");
        }
        progressDialog.dismiss();


    }

    public void u_delete_product(View view){
         if(u_p!=null) {
             if(u_p.length()!=0) {
                 database = FirebaseDatabase.getInstance().getReference().child("products");
                 database.child(selected_category).child(u_p).removeValue();
                 Toast.makeText(this, "Poduct Deleted sucessfully", Toast.LENGTH_SHORT).show();
                 name.setText("");
                 price.setText("");
                 image.setText("");
                 stock.setText("");

             }
         }
         else{
             Toast.makeText(this, "Please select Product", Toast.LENGTH_SHORT).show();

         }

    }
}