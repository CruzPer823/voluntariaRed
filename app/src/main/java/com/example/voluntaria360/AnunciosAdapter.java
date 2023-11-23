package com.example.voluntaria360;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AnunciosAdapter extends RecyclerView.Adapter<AnunciosAdapter.MyViewHolder> {

    Context context;
    ArrayList<Anuncio> anuncioArrayList;

    public AnunciosAdapter(Context context, ArrayList<Anuncio> anuncioArrayList) {
        this.context = context;
        this.anuncioArrayList = anuncioArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.anunciositem, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Anuncio anuncio = anuncioArrayList.get(position);
        holder.titulo.setText(anuncio.titulo);
        holder.descripcion.setText(anuncio.descripcion);
        Glide.with(context).load(anuncio.imagen).apply(new RequestOptions().override(holder.imagen.getMaxWidth(), holder.imagen.getMaxHeight())).into(holder.imagen);
    }



    @Override
    public int getItemCount() {
        return anuncioArrayList.size();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titulo,descripcion;
        ImageView imagen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.eventotitulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            imagen = itemView.findViewById(R.id.imagen);
        }
    }
}
