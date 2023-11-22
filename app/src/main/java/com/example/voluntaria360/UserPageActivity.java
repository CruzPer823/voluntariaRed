package com.example.voluntaria360;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;

public class UserPageActivity extends AppCompatActivity {

    Button logOut,badges,changePic,historyEvents,totalHours,savedEvents,back;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    TextView nameTv, usrnameTv, mailTv;
    ImageView profileImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setSelectedItemId(R.id.thirdFragment);
        nameTv=findViewById(R.id.nameusr);
        usrnameTv=findViewById(R.id.usrname2);
        mailTv=findViewById(R.id.usrmail);
        logOut = findViewById(R.id.logout);
        badges = findViewById(R.id.badges);
        back = findViewById(R.id.returnbtn);
        totalHours = findViewById(R.id.totalhours);
        changePic = findViewById(R.id.uploadPic);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.firstFragment) {
                    startActivity(new Intent(getApplicationContext(), AnuncioFragmentActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.secondFragment) {
                    startActivity(new Intent(getApplicationContext(), activity_sos.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.thirdFragment) {
                    return true;
                }
                return false;
            }
        });

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPageActivity.this, pictureActivity.class));
            }
        });
        totalHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPageActivity.this, PopUpActivity.class));
            }
        });
        badges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPageActivity.this, BadgesActivity.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPageActivity.this, MainFeedActivity.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(UserPageActivity.this, LoginActivity.class));
            }
        });
        db.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String nombre = documentSnapshot.getString("nombre");
                    String correo = documentSnapshot.getString("email");
                    String usrname = documentSnapshot.getString("matricula");

                    nameTv.setText("Hola, "+nombre+"!");
                    usrnameTv.setText(usrname);
                    mailTv.setText(correo);
                }

            }
        });

    }

}