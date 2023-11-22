package com.example.voluntaria360;

import com.google.firebase.Timestamp;

import java.sql.Time;

public class Evento {
    String titulo,descripcion,imagen,fechaEvento;
    Integer hrsMax;
    Boolean tipo;

    public Evento(){}

    public Evento(String titulo, String fechaEvento, String descripcion, String imagen, Integer hrsMax, Boolean tipo) {
        this.titulo = titulo;
        this.fechaEvento = fechaEvento;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.hrsMax = hrsMax;
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public Integer gethrsMax() {
        return hrsMax;
    }

    public void sethrsMax(Integer hrsMax) {
        this.hrsMax = hrsMax;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fecha) {
        this.fechaEvento = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
