package com.example.voluntaria360;

import com.google.firebase.Timestamp;

public class Anuncio {
    String titulo,descripcion,imagen;
    Timestamp fecha;
    Boolean tipo;
    Integer hrsMax;

    public Anuncio(){}

    public Anuncio(String titulo, Timestamp fecha, String descripcion, String imagen, Integer hrsMax, Boolean tipo) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.tipo = tipo;
        this.hrsMax = hrsMax;
    }

    public Integer getHrsMax() {
        return hrsMax;
    }

    public void setHrsMax(Integer hrsMax) {
        this.hrsMax = hrsMax;
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