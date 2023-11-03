package com.example.voluntaria360;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Evento> list;

    public RecyclerAdapter(Context context, ArrayList<Evento> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.eventositem, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Evento evento = list.get(position);
        holder.titulo.setText(evento.getTitulo());
        holder.descripcion.setText(evento.getDescripcion());
        holder.fecha.setText(evento.getFecha());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, descripcion, fecha;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.eventotitulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            fecha = itemView.findViewById(R.id.fecha);
        }
    }

}
