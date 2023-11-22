package com.example.voluntaria360;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.MyViewHolder> {

    Context context;
    ArrayList<Evento> eventoArrayList;

    public EventosAdapter(Context context, ArrayList<Evento> eventoArrayList) {
        this.context = context;
        this.eventoArrayList = eventoArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.eventositem, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Evento evento = eventoArrayList.get(position);
            holder.titulo.setText(evento.titulo);
            holder.fecha.setText(evento.fechaEvento);
            holder.descripcion.setText(evento.descripcion);
            holder.hrsMax.setText(evento.hrsMax + " horas");
            Glide.with(context).load(evento.imagen).apply(new RequestOptions().override(holder.imagen.getMaxWidth(), holder.imagen.getMaxHeight())).into(holder.imagen);
    }



    @Override
    public int getItemCount() {
        return eventoArrayList.size();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titulo,fecha,descripcion, hrsMax;
        ImageView imagen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Button button = itemView.findViewById(R.id.guardarbtn);
            titulo = itemView.findViewById(R.id.eventotitulo);
            fecha = itemView.findViewById(R.id.fecha);
            descripcion = itemView.findViewById(R.id.descripcion);
            imagen = itemView.findViewById(R.id.imagen);
            hrsMax = itemView.findViewById(R.id.horas);
        }
    }
}
