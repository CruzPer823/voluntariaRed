package com.example.voluntaria360;

import com.google.firebase.Timestamp;

public class HorasVoluntarios {
    String evento,idVoluntario;
    Boolean registered;
    public HorasVoluntarios(){}

    public HorasVoluntarios(String evento, String idVoluntario,Boolean registered) {
        this.evento = evento;
        this.idVoluntario = idVoluntario;
        this.registered = registered;
    }
    public Boolean getSaved() {
        return registered;
    }

    public void setSaved(Boolean registered) {
        this.registered = registered;
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
