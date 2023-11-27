package com.example.voluntaria360;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class pictureActivity extends AppCompatActivity {
    Button aceptar,cancelar;
    TextView changePic;
    ImageView profilePic;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    Uri imageUri;
    String myUri="";
    StorageTask uploadTask;
    StorageReference storageProfilePic;
    ActivityPictureBinding binding;
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
        storageProfilePic = FirebaseStorage.getInstance().getReference().child("usrPic");


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
                ImagePicker.with(pictureActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080,1080)
                        .start();
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
                        profilePic.setBackgroundResource(R.drawable.transparentbtn);
                        String image = documentSnapshot.getString("image");
                        Picasso.get().load(image).into(profilePic);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
        }else if (resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.getError(data),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Se ha cancelado la selección de la imagen",Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadProfileImage(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Imagen Establecida");
        dialog.setMessage("Por favor espere mientras se sube su imagen");
        dialog.show();
        if(imageUri != null){
            final StorageReference fileRef = storageProfilePic.child(mAuth.getCurrentUser().getUid()+".jpg");
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUrl = (Uri) task.getResult();
                        myUri = downloadUrl.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image", myUri);
                        db.collection("users").document(mAuth.getCurrentUser().getUid()).set(userMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), UserPageActivity.class));
                                Toast.makeText(getApplicationContext(),"Imagen subida exitosamente",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error al subir la imagen",Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    }
                }
            });
        }else {
            dialog.dismiss();
            Toast.makeText(this, "Imagen no seleccionada",Toast.LENGTH_SHORT).show();
        }
    }
}