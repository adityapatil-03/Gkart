package com.example.gkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_Login_page extends AppCompatActivity {
    private Button adminButton;
    private EditText adminusername;
    private EditText adminpassword;
    private String username ="admin";
    private String password = "admin123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);
        adminButton = findViewById(R.id.adminlogin);
        adminusername = findViewById(R.id.adminusername);
        adminpassword = findViewById(R.id.adminpassword);

    }


    @Override
    protected void onStart() {
        super.onStart();
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.equals(adminusername.getText().toString()) && password.equals(adminpassword.getText().toString())){
//                    Toast.makeText(Admin_Login_page.this, "Correct Cored", Toast.LENGTH_SHORT).show();
                    Intent adminPanel = new Intent(Admin_Login_page.this,Admin_Page.class);
                    startActivity(adminPanel);
                }
                else {
                    Toast.makeText(Admin_Login_page.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    Intent redirectLogin = new Intent(Admin_Login_page.this,LoginActivity.class);
                    startActivity(redirectLogin);
                }
            }
        });
    }
}