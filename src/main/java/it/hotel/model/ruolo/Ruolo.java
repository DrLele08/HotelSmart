package it.hotel.model.ruolo;

import it.hotel.model.utente.Utente;

import java.util.ArrayList;

/**
 * Rappresenta il Ruolo di un {@link Utente}.
 */
public class Ruolo {

    private int idRuolo;
    private String tipo;

    /**
     * Costruisce un Ruolo con identificativo e tipo specificati.
     * @param idRuolo Identificativo
     * @param tipo Tipo
     */
    public Ruolo(int idRuolo, String tipo) {
        this.idRuolo = idRuolo;
        this.tipo = tipo;
    }

    /**
     * Restituisce l'identificativo del Ruolo.
     * @return Identificativo
     */
    public int getIdRuolo() {
        return idRuolo;
    }

    /**
     * Restituisce il tipo del Ruolo.
     * @return Tipo
     */
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