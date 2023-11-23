package com.example.voluntaria360;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.voluntaria360.databinding.ActivityPictureBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class pictureActivity extends AppCompatActivity {
    Button aceptar,cancelar;
    TextView changePic;
    ImageView profilePic;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    DatabaseReference databaseReference;
    Uri imageUri;
    String myUri="";
    StorageTask uploadTask;
    StorageReference storageProfilePic;
    ActivityPictureBinding binding;
    ActivityResultLauncher<String> cropImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        binding = ActivityPictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        aceptar = findViewById(R.id.aceptar);
        cancelar = findViewById(R.id.cancelar);
        profilePic = findViewById(R.id.profilePic);
        changePic = findViewById(R.id.changepic);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageProfilePic = FirebaseStorage.getInstance().getReference().child("Profile Pic");


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.95),(int)(height*.55));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadProfileImage();

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void uploadProfileImage(){

    }
}