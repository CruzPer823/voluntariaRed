package com.example.voluntaria360;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AnuncioFragmentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Anuncio> anuncioArrayList;
    AnunciosAdapter anunciosAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anunciofragment);

        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setSelectedItemId(R.id.firstFragment);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.firstFragment) {
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


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LocoOo");
        progressDialog.setMessage("LocOoO");
        progressDialog.show();


        recyclerView = findViewById(R.id.testrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        anuncioArrayList = new ArrayList<Anuncio>();
        anunciosAdapter = new AnunciosAdapter(AnuncioFragmentActivity.this, anuncioArrayList);

        recyclerView.setAdapter(anunciosAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("anuncios").orderBy("fecha", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED ) {
                                if (!(dc.getDocument().toObject(Anuncio.class).tipo)) {
                                    anuncioArrayList.add(dc.getDocument().toObject(Anuncio.class));
                                }
                            }
                        }

                        anunciosAdapter.notifyDataSetChanged();
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });
    }


}