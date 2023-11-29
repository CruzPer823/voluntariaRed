package com.example.voluntaria360;

import com.google.firebase.Timestamp;

public class HorasVoluntarios {
    String evento,idVoluntario, estatus, evidencia;
    Integer hrs;
    Boolean aprobadas;

    public HorasVoluntarios(){}

    public HorasVoluntarios(String evento, String idVoluntario, Integer hrs, Boolean aprobadas, String estatus, String evidencia) {
        this.evento = evento;
        this.idVoluntario = idVoluntario;
        this.hrs = hrs;
        this.aprobadas = aprobadas;
        this.estatus = estatus;
        this.evidencia = evidencia;
    }

    public Integer getHrs() {
        return hrs;
    }

    public void setHrs(Integer hrs) {
        this.hrs = hrs;
    }

    public String getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(String idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

}
