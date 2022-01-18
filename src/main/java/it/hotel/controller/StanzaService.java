package it.hotel.controller;


import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;

import java.sql.Date;
import java.util.ArrayList;

public class StanzaService
{
    private final StanzaDAO dao;

    public StanzaService()
    {
        dao=new StanzaDAO();
    }
    /**
     * Effettua la ricerca delle camere in base ai filtri
     *
     * @param animale Se il cliente ha un animale (figli esclusi)
     * @param fumo Se il cliente fuma
     * @param lettiS Numero di letti singoli
     * @param lettiM Numero di letti matrimoniali
     * @param costoNotteMin Valore minimo di costo per notte
     * @param costoNotteMax Valore massimo di costo per notte
     * @param scontoMin Valore minimo di sconto
     * @param scontoMax Valore massimo di sconto
     * @return ListaStanze Ritorna un vettore di stanze disponibili @
     * @see Stanza
     */
    public ArrayList<Stanza> searchByFilter(boolean animale, boolean fumo, int lettiS, int lettiM, double costoNotteMin, double costoNotteMax, double scontoMin, double scontoMax, Date dataIn, Date dataOut)
    {
        if(lettiM>0 & lettiS>0 && costoNotteMin>0 && costoNotteMax>costoNotteMin && scontoMin>=0 && scontoMax>=scontoMin)
            return (ArrayList<Stanza>)dao.doSearch(animale,fumo,lettiS,lettiM,costoNotteMin,costoNotteMax,scontoMin,scontoMax,dataIn,dataOut);
        else
            throw new IllegalArgumentException();
    }
    /**
     * Effettua la modifica della password di un utente
     *
     * @param idStanza Identificatico della stanza
     * @return Ritorna una stanza
     * @see Stanza
     */
    public Stanza getDetailById(int idStanza) throws StanzaNotFoundException
    {
        return dao.doSelectById(idStanza);
    }

}
