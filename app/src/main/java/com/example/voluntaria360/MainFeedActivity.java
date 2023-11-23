package com.example.voluntaria360;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainFeedActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem meventos, manuncios;
    RecyclerView recyclerView;
    ArrayList<Evento> eventoArrayList;
    EventosAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    ArrayList<Anuncio> anuncioArrayList;
    AnunciosAdapter anunciosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);
        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setSelectedItemId(R.id.firstFragment);

        meventos = findViewById(R.id.eventos);
        manuncios = findViewById(R.id.anuncios);
        tabLayout=findViewById(R.id.include);


        anunciosAdapter = new AnunciosAdapter(MainFeedActivity.this, anuncioArrayList);
        progressDialog = new ProgressDialog(this);
        recyclerView = findViewById(R.id.testrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando");
        progressDialog.setMessage("Cargando");
        progressDialog.show();
        eventoArrayList = new ArrayList<Evento>();
        myAdapter = new EventosAdapter(getApplicationContext(), eventoArrayList);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==1){
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Cargando");
                    progressDialog.setMessage("Cargando");
                    progressDialog.show();
                    anuncioArrayList=new ArrayList<Anuncio>();
                    anunciosAdapter = new AnunciosAdapter(MainFeedActivity.this, anuncioArrayList);
                    recyclerView.setAdapter(anunciosAdapter);
                    AdChangeListener();
                }
                if(tab.getPosition()==0){
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Cargando");
                    progressDialog.setMessage("Cargando");
                    progressDialog.show();
                    eventoArrayList = new ArrayList<Evento>();
                    myAdapter = new EventosAdapter(getApplicationContext(), eventoArrayList);
                    recyclerView.setAdapter(myAdapter);
                    EventChangeListener();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
    }
    private void AdChangeListener() {
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
                                if (dc.getDocument().toObject(Evento.class).tipo) {
                                    eventoArrayList.add(dc.getDocument().toObject(Evento.class));
                                }
                            }
                        }

                        myAdapter.notifyDataSetChanged();
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}