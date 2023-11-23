package com.example.voluntaria360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class BadgesActivity extends AppCompatActivity {
    Button back,h10,h20,h30,h40,h50,h60;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);
        back = findViewById(R.id.returnbtn3);
        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setSelectedItemId(R.id.thirdFragment);
        h10 = findViewById(R.id.hours10);
        h20 = findViewById(R.id.horas20);
        h30 = findViewById(R.id.horas30);
        h40 = findViewById(R.id.horas40);
        h50 = findViewById(R.id.horas50);
        h60 = findViewById(R.id.horas60);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.firstFragment) {
                    startActivity(new Intent(getApplicationContext(), MainFeedActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.secondFragment) {
                    startActivity(new Intent(getApplicationContext(), activity_sos.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.thirdFragment) {
                    startActivity(new Intent(getApplicationContext(), UserPageActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserPageActivity.class));
            }
        });
    }

}
