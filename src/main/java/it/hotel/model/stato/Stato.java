package it.hotel.model.stato;

public class Stato {

    private int idStato;
    private String stato;

    public Stato(int idStato, String stato) {
        this.idStato = idStato;
        this.stato = stato;
    }

    public int getIdStato() {
        return idStato;
    }

    public String getStato() {
        return stato;
    }

}