package com.example.voluntaria360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class eventolist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    CollectionReference database2;
    RecyclerAdapter recyclerAdapter;
    ArrayList<Evento> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventolist);

        recyclerView = findViewById(R.id.eventolist);
        database = FirebaseDatabase.getInstance().getReference("anuncios");
        database2 = FirebaseFirestore.getInstance().collection("anuncios");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(this, list);
        recyclerView.setAdapter(recyclerAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Evento evento = dataSnapshot.getValue(Evento.class);
                    list.add(evento);
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}