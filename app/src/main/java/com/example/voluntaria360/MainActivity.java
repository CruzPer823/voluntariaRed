package com.example.voluntaria360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btn_register;
    EditText name, password, correo, matricula;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        correo = findViewById(R.id.correo);
        matricula = findViewById(R.id.matricula);

        btn_register = findViewById(R.id.registBut);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUsr = name.getText().toString().trim();
                String passwordUsr = password.getText().toString().trim();
                String usernameUsr = correo.getText().toString().trim();
                String matriculaUsr = matricula.getText().toString().trim();

                if(nameUsr.isEmpty() && passwordUsr.isEmpty() && usernameUsr.isEmpty() && matriculaUsr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else if(passwordUsr.length()<6){
                    Toast.makeText(MainActivity.this, "La contraseña debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
                }
                else{RegisterUser(nameUsr, passwordUsr, usernameUsr, matriculaUsr);}
            }
        });
    }

    private void RegisterUser(String nameUsr, String passwordUsr, String usernameUsr, String matriculaUsr) {
        mAuth.createUserWithEmailAndPassword(usernameUsr, passwordUsr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();

                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("email", usernameUsr);
                map.put("nombre", nameUsr);
                map.put("matricula", matriculaUsr);
                map.put("password", passwordUsr);

                db.collection("users").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        Toast.makeText(MainActivity.this, "Registrado Correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error al hacer el registro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}