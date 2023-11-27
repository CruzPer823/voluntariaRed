package com.example.voluntaria360;

import com.google.firebase.Timestamp;

public class HorasVoluntarios {
    String evento,idVoluntario;
    public HorasVoluntarios(){}

    public HorasVoluntarios(String evento, String idVoluntario) {
        this.evento = evento;
        this.idVoluntario = idVoluntario;
    }

    public String getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(String idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

}
