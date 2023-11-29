package com.example.voluntaria360;

import android.content.Context;
import android.graphics.Color;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HistorialEventosAdapter extends RecyclerView.Adapter<HistorialEventosAdapter.MyViewHolder> {

    Context context;
    ArrayList<Evento> eventoList;
    FirebaseFirestore db;

    public HistorialEventosAdapter(Context context, ArrayList<Evento> eventoList) {
        this.context = context;
        this.eventoList = eventoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.eventositem, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Evento evento = eventoList.get(position);
        holder.button.setVisibility(View.GONE);
        holder.titulo.setText(evento.titulo);
        holder.fecha.setText(evento.fechaEvento);
        holder.descripcion.setText(evento.descripcion);
        holder.hrsMax.setText(evento.hrsMax + " horas");
        Glide.with(context).load(evento.imagen).apply(new RequestOptions().override(holder.imagen.getMaxWidth(), holder.imagen.getMaxHeight())).into(holder.imagen);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titulo,fecha,descripcion, hrsMax;
        ImageView imagen;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.guardarbtn);
            titulo = itemView.findViewById(R.id.eventotitulo);
            fecha = itemView.findViewById(R.id.fecha);
            descripcion = itemView.findViewById(R.id.descripcion);
            imagen = itemView.findViewById(R.id.imagen);
            hrsMax = itemView.findViewById(R.id.horas);
        }
    }
}
