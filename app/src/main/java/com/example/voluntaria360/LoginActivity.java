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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegistro;
    EditText email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.correo);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.ingresar);
        btnRegistro = findViewById(R.id.registrarse);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUsr = email.getText().toString().trim();
                String passwordUsr = password.getText().toString().trim();

                if(emailUsr.isEmpty() && passwordUsr.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(emailUsr, passwordUsr);
                }

            }
        });
    }

    private void loginUser(String emailUsr, String passwordUsr) {
        mAuth.signInWithEmailAndPassword(emailUsr, passwordUsr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(LoginActivity.this, UserPageActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(LoginActivity.this, UserPageActivity.class));
            finish();
        }
    }
}