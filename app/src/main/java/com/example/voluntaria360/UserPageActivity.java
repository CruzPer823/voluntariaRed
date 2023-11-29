package com.example.voluntaria360;

import androidx.annotation.DrawableRes;
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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

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
        savedEvents = findViewById(R.id.savedEvents);
        profileImage = findViewById(R.id.profilePic);
        historyEvents = findViewById(R.id.hisrtoryEvents);
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
                    return true;
                }
                return false;
            }
        });

        savedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Starting Activity", "Saved");
                startActivity(new Intent(UserPageActivity.this, SavedEventoFragmentActivity.class));
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

        historyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Starting Activity", "History");
                startActivity(new Intent(UserPageActivity.this, HistorialEventosActivity.class));
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
                Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logout);
                finish();
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

        getUsrInfo();

    }
    private void getUsrInfo(){
        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    if(documentSnapshot.contains("image")){
                        String image = documentSnapshot.getString("image");
                        profileImage.setBackgroundResource(R.drawable.transparentbtn);
                        changePic.setBackgroundResource(R.drawable.transparentbtn);
                        Picasso.get().load(image).into(profileImage);
                    }
                }
            }
        });
    }
}