package it.hotel.model.ruolo;

import it.hotel.model.utente.Utente;

import java.util.ArrayList;

/**
 * Rappresenta il Ruolo di un {@link Utente}.
 */
public class Ruolo {

    private int idRuolo;
    private String ruolo;

    /**
     * Costruisce un Ruolo con identificativo e ruolo specificati.
     * @param idRuolo Identificativo
     * @param ruolo Ruolo
     */
    public Ruolo(int idRuolo, String ruolo) {
        this.idRuolo = idRuolo;
        this.ruolo = ruolo;
    }

    /**
     * Restituisce l'identificativo dell'oggetto Ruolo.
     * @return Identificativo
     */
    public int getIdRuolo() {
        return idRuolo;
    }

    /**
     * Restituisce il ruolo che l'oggetto Ruolo rappresenta
     * @return Ruolo
     */
    public String getRuolo() {
        return ruolo;
    }

    public static int getIdByNome(ArrayList<Ruolo> listRuoli,String ruolo)
    {
        for(Ruolo r:listRuoli)
        {
            if(r.ruolo.equalsIgnoreCase(ruolo))
                return r.idRuolo;
        }
        return -1;
    }
}