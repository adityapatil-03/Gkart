package com.example.gkart;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gkart.databinding.ActivityNavigationBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.security.PrivateKey;

public class navigation_activity extends AppCompatActivity {
    private ActivityNavigationBinding binding;
    private FloatingActionButton cart_button;
    private FirebaseAuth firebaseAuth;
    private TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        firebaseAuth = FirebaseAuth.getInstance();
        cart_button = findViewById(R.id.fab);
        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(navigation_activity.this,cart.class));
            }
        });
    }

    public void cardClick(View view) {
        String childname = view.getTag().toString();
        Intent intent = new Intent(navigation_activity.this,Product_display.class);
        intent.putExtra("category",childname);
        startActivity(intent);
    }

    public void Myorder(View view){
        Intent intent = new Intent(navigation_activity.this,Myorders.class);
        startActivity(intent);
    }

    public void logout(View view) {
        firebaseAuth.signOut();
        startActivity(new Intent(navigation_activity.this,LoginActivity.class));
        finish();
    }

    public void feedback(View view){
        startActivity(new Intent(navigation_activity.this,Feedback.class));
    }

    public void notification(View view){
        startActivity(new Intent(navigation_activity.this, Notifications.class));
    }
}