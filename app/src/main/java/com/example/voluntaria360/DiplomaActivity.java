package com.example.voluntaria360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
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

public class DiplomaActivity extends AppCompatActivity {
    Button back,exportar;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    final static int REQUEST_CODE = 1232;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diploma);
        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setSelectedItemId(R.id.thirdFragment);
        back = findViewById(R.id.returnbtn);
        exportar = findViewById(R.id.exportar);
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
                startActivity(new Intent(getApplicationContext(), BadgesActivity.class));
            }
        });
        db.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            }
        });
        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissions();

            }
        });
    }
    private void askPermissions(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE );
    }
}