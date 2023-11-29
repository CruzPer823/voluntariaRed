package com.example.voluntaria360;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class addhorasActivity extends AppCompatActivity {
    Button exportar,back;
    TextView nomEven;
    EditText horasVol;
    ImageView uploadPic;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    Uri imageUri;
    String idEvento,horas,myUri="";
    int horasMax,horasSol,position;
    StorageTask uploadTask;
    StorageReference storageProfilePic;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhoras);
        Intent intent = getIntent();
        if(intent != null){
            idEvento = intent.getStringExtra("idEvento");
        }
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        exportar = findViewById(R.id.exportar);
        nomEven = findViewById(R.id.AppLabel);
        uploadPic = findViewById(R.id.uploadPicHours);
        back = findViewById(R.id.returnbtn);
        horasVol = findViewById(R.id.editTextTime);
        storageProfilePic = FirebaseStorage.getInstance().getReference().child("usrEvents");
        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setSelectedItemId(R.id.thirdFragment);


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
                startActivity(new Intent(getApplicationContext(), SavedEventoFragmentActivity.class));
            }
        });
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(addhorasActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080,1080)
                        .start();
            }
        });
        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 horas = horasVol.getText().toString().trim();
                if(horas.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Introduzca las horas realizadas",Toast.LENGTH_SHORT).show();
                } else{
                    horasSol = Integer.parseInt(horas);
                }
                if (horasSol > horasMax) {
                    Toast.makeText(getApplicationContext(),"Cantidad de horas inválida",Toast.LENGTH_SHORT).show();
                }else if(horasSol <= horasMax && horasSol!=0){
                    uploadProfileImage();
                }
                
            }
        });
        db.collection("anuncios").document(idEvento).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombreEvento = documentSnapshot.getString("titulo");
                Long hrsMax = documentSnapshot.getLong("hrsMax");
                horasMax = hrsMax.intValue();
                nomEven.setText(nombreEvento);
            }
        });
    }

    private void uploadHours() {
        db.collection("horasVoluntarios").whereEqualTo("idVoluntario",user.getUid()).whereEqualTo("evento",idEvento).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        db.collection("horasVoluntarios").document(document.getId()).update("hrs",horasSol);
                        Toast.makeText(getApplicationContext(),"Horas correctamente registradas",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d(TAG,"No se obtuvieron los documentos deseados",task.getException());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            imageUri = data.getData();
            uploadPic.setImageURI(imageUri);
        }else if (resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.getError(data),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Se ha cancelado la selección de la imagen",Toast.LENGTH_SHORT).show();
        }

    }
    private void uploadProfileImage(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Imagen Establecida");
        dialog.setMessage("Por favor, espere mientras se sube su imagen");
        dialog.show();
        if(imageUri != null){
            final StorageReference fileRef = storageProfilePic.child("Evento"+idEvento+user.getUid()+".jpg");
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
                        db.collection("horasVoluntarios").whereEqualTo("idVoluntario",user.getUid()).whereEqualTo("evento",idEvento).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(QueryDocumentSnapshot document : task.getResult()){
                                            db.collection("horasVoluntarios").document(document.getId()).update("evidencia",myUri);
                                        }
                                        uploadHours();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), SavedEventoFragmentActivity.class));
                                        Toast.makeText(getApplicationContext(),"Evidencia subida exitosamente",Toast.LENGTH_SHORT).show();

                                    }else{
                                        Log.d(TAG,"No se obtuvieron los documentos deseados",task.getException());
                                    }

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
            Toast.makeText(this, "Evidencia no seleccionada",Toast.LENGTH_SHORT).show();
        }
    }

}