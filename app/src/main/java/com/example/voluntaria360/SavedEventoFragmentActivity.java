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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class SavedEventoFragmentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Evento> eventoList;
    ArrayList<Evento> savedEventoArrayList;
    ArrayList<HorasVoluntarios> registros;
    SavedEventosAdapter eventosAdapter;
    FirebaseFirestore db;
    FirebaseFirestore db2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedeventofragment);

        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setSelectedItemId(R.id.thirdFragment);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.firstFragment) {
                    startActivity(new Intent(getApplicationContext(), EventoFragmentActivity.class));
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

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LocoOo");
        progressDialog.setMessage("LocOoOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        progressDialog.show();


        recyclerView = findViewById(R.id.testrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();
        eventoList = new ArrayList<Evento>();
        savedEventoArrayList = new ArrayList<Evento>();
        registros = new ArrayList<HorasVoluntarios>();
        eventosAdapter = new SavedEventosAdapter(SavedEventoFragmentActivity.this, savedEventoArrayList);

        recyclerView.setAdapter(eventosAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {

        db2.collection("horasVoluntarios").whereEqualTo("idVoluntario", FirebaseAuth.getInstance().getCurrentUser().getUid())
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
                                HorasVoluntarios doc = dc.getDocument().toObject(HorasVoluntarios.class);
                                registros.add(doc);
                            }
                        }

                        eventosAdapter.notifyDataSetChanged();
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });

        for(HorasVoluntarios doc : registros){
            DocumentReference docRef = db.collection("anuncios").document("/" + doc.evento);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Evento evento = documentSnapshot.toObject(Evento.class);
                    evento.setTitulo("hola");
                    savedEventoArrayList.add(evento);
                }
            });
        }
    }
}