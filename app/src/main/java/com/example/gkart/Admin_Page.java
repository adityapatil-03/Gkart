package com.example.gkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }

    public void addproduct(View view){
        Intent intent = new Intent(Admin_Page.this,add_product.class);
        startActivity(intent);
    }

    public void updateproduct(View view){
        Intent intent = new Intent(Admin_Page.this,updateproduct.class);
        startActivity(intent);
    }

    public void add_user(View view){
        Intent intent = new Intent(Admin_Page.this,adduser.class);
        startActivity(intent);
        }
    public void reviewOrder(View view){
        startActivity(new Intent(Admin_Page.this,OrderReview.class));

    }
}