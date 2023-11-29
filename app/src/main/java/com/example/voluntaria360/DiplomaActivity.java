package com.example.voluntaria360;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.START_VIEW_PERMISSION_USAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiplomaActivity extends AppCompatActivity {
    Button back,exportar;
    TextView date;
    ImageView profileImage;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    int horas;
    String nombre,fecha;

    final static int REQUEST_CODE = 1232;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diploma);
        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setSelectedItemId(R.id.thirdFragment);
        Intent intent = getIntent();
        horas=intent.getIntExtra("horas",horas);
        back = findViewById(R.id.returnbtn);
        exportar = findViewById(R.id.exportar);
        profileImage = findViewById(R.id.usrPic);
        date = findViewById(R.id.date);
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
                nombre=documentSnapshot.getString("nombre");
            }
        });
        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDiploma();
            }
        });
        fecha = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault()).format(new Date());
        date.setText(fecha);
        getUsrInfo();
    }
    private void askPermissions(){
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }
    private void generarDiploma() {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200,800,1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.fondocertificado_2);
        float scale = Math.min((float) 1200/bitmap.getWidth(),(float)800/bitmap.getHeight());
        canvas.drawBitmap(bitmap, null, new Rect(0, 0, (int) (bitmap.getWidth() * scale), (int) (bitmap.getHeight() * scale)), null);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(42);
        String text= "La Cruz Roja Otorga el presente certificado a:";
        float anchol1=paint.measureText(text);
        float x=(canvas.getWidth()-anchol1)/2 , y=360;
        canvas.drawText(text,x,y,paint);
        paint.setTextSize(52);
        float anchotxt=paint.measureText(nombre);
        float xC=(canvas.getWidth()-anchotxt)/2;
        canvas.drawText(nombre,xC,450,paint);
        paint.setTextSize(42);
        String text2="Por haber completado "+ horas+" horas de servicio voluntario";
        float anchol2=paint.measureText(text2);
        float x2=(canvas.getWidth()-anchol2)/2;
        canvas.drawText(text2,x2,550,paint);
        float anchodate=paint.measureText(fecha);
        float x3=(canvas.getWidth()-anchodate)/2;
        canvas.drawText(fecha,x3,630,paint);
        document.finishPage(page);
        File dowloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS
        );
        String filename = "Certificado"+horas+"hrs.pdf";
        File file = new File(dowloadDir,filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            fos.close();
            Toast.makeText(this, "Pdf generado exitosamente en Documents", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.d("mylog","Error al escribir el documento"+e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void getUsrInfo(){
        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    if(documentSnapshot.contains("image")){
                        String image = documentSnapshot.getString("image");
                        profileImage.setBackgroundResource(R.drawable.transparentbtn);
                        Picasso.get().load(image).into(profileImage);
                    }
                }
            }
        });
    }
}