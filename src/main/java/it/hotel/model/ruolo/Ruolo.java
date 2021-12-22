package it.hotel.model.ruolo;

import java.util.ArrayList;

public class Ruolo {

    private int idRuolo;
    private String tipo;

    public Ruolo(int idRuolo, String tipo) {
        this.idRuolo = idRuolo;
        this.tipo = tipo;
    }

    public int getIdRuolo() {
        return idRuolo;
    }

    public String getTipo() {
        return tipo;
    }
    public static int getIdByNome(ArrayList<Ruolo> listRuoli,String tipo)
    {
        for(Ruolo r:listRuoli)
        {
            if(r.tipo.equalsIgnoreCase(tipo))
                return r.idRuolo;
        }
        return -1;
    }
}