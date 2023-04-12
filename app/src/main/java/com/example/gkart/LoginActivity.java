package com.example.gkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private  Button adminbutton;
    private EditText username;
    private EditText password;
    private Button loginbutton;
    private FirebaseAuth authenticator;
    ProgressDialog progressDialog;
    ConnectivityManager connectivityManager;

//    ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//    NetworkInfo nInfo = cm.getActiveNetworkInfo();
//    boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_acitvity);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbutton = findViewById(R.id.loginbutton);
        adminbutton = findViewById(R.id.adminloginbutton);
        authenticator = FirebaseAuth.getInstance();
        progressDialog  = new ProgressDialog(this);
        connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);



        adminbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Redirecting to Admin Page", Toast.LENGTH_SHORT).show();
                Intent adminIntent =  new Intent(LoginActivity.this,Admin_Login_page.class);
                startActivity(adminIntent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(authenticator.getCurrentUser()==null ){
            noInternet();
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.setMessage("Initial checks...");
                    progressDialog.show();
                    String email = username.getText().toString();
                    String pass = password.getText().toString();
                    if (email.length()>0&&pass.length()>0){
                        loginUser(email,pass);
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"OOOps!! Empty credentials..",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });


        }
        else{
            //            start home page intent
            Toast.makeText(LoginActivity.this,"you are already logged in",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,navigation_activity.class));
            finish();
            noInternet();
        }
    }

    public void loginUser(String name, String pass){
        progressDialog.setMessage("Connecting to our database...");
        progressDialog.show();
        authenticator.signInWithEmailAndPassword(name,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this,"Hurrrayy! Login was successful",Toast.LENGTH_SHORT).show();
                dataSaver(name);
                progressDialog.dismiss();
                startActivity(new Intent(LoginActivity.this,navigation_activity.class));
                finish();
//                start new intent here
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void dataSaver(String s){
        SharedPreferences saver = getSharedPreferences("userdetails",MODE_PRIVATE);
        SharedPreferences.Editor editor = saver.edit();
        editor.putString("emailid",s);
        editor.apply();
    }
    public void noInternet(){
        NetworkInfo nInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();

        if(! connected){

            startActivity(new Intent(LoginActivity.this,NoInternet.class));
        }
    }
}