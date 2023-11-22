package com.example.voluntaria360;

import com.google.firebase.Timestamp;

import java.sql.Time;

public class Evento {
    String titulo,descripcion,imagen,fechaEvento,id;
    Timestamp fecha;
    Integer hrsMax;
    Boolean tipo;
    public Evento(){}

    public Evento(String titulo, Timestamp fecha, String descripcion, String imagen, Integer hrsMax, Boolean tipo, String fechaEvento, String id) {
        this.titulo = titulo;
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.hrsMax = hrsMax;
        this.tipo = tipo;
        this.fechaEvento = fechaEvento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
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

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
