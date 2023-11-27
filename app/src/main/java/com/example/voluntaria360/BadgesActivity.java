package com.example.voluntaria360;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
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
        h10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DiplomaActivity.class));
            }
        });
        h20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DiplomaActivity.class));
            }
        });
        h30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DiplomaActivity.class));
            }
        });
        h40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DiplomaActivity.class));
            }
        });
        h50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DiplomaActivity.class));
            }
        });
        h60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DiplomaActivity.class));
            }
        });

        db.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Long horas = documentSnapshot.getLong("hrsAcumuladas");
                    Integer h =horas.intValue();
                    h10.setEnabled(false);
                    h20.setEnabled(false);
                    h30.setEnabled(false);
                    h40.setEnabled(false);
                    h50.setEnabled(false);
                    h60.setEnabled(false);
                    if(h >= 10){
                        h10.setEnabled(true);
                        h10.setBackgroundResource(R.drawable.badgebtn);
                    }if(h >= 20){
                        h20.setEnabled(true);
                        h20.setBackgroundResource(R.drawable.badgebtn);
                    }if(h>=30) {
                        h30.setEnabled(true);
                        h30.setBackgroundResource(R.drawable.badgebtn);
                    }if(h>=40) {
                        h40.setEnabled(true);
                        h40.setBackgroundResource(R.drawable.badgebtn);
                    }if(h>=50) {
                        h50.setEnabled(true);
                        h50.setBackgroundResource(R.drawable.badgebtn);
                    }if(h>=60) {
                        h60.setEnabled(true);
                        h60.setBackgroundResource(R.drawable.badgebtn);
                    }


                }
            }
        });
    }

}
