package it.hotel.model.ruolo;

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

}