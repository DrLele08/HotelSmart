package it.hotel.controller.services;


import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornisce metodi di utilizzo del database per {@link Stanza}.
 */
public class StanzaService
{

    private final StanzaDAO dao;

    /**
     * Costruisce un oggetto StanzaService.
     */
    public StanzaService()
    {
        dao=new StanzaDAO();
    }

    /**
     * Recupera tutte le stanze presenti nel database.
     * @return Lista contenente le stanze trovate
     */
    public List<Stanza> getStanze() {
        return dao.getStanze();
    }

    /**
     * Restituisce il prezzo più alto tra i prezzi delle stanze specificate.
     * @param stanze Le stanze tra cui effettuare la ricerca
     * @return Il prezzo più alto
     */
    public double getMaxPrice(List<Stanza> stanze) {
        double max_prezzo = stanze.get(0).getCostoNotte();
        for(Stanza s: stanze){
            if(s.getCostoNotte() > max_prezzo) max_prezzo = s.getCostoNotte();
        }
        return max_prezzo;
    }

    /**
     * Restituisce il prezzo più basso tra i prezzi delle stanze specificate.
     * @param stanze Le stanze tra cui effettuare la ricerca
     * @return Il prezzo più basso
     */
    public double getMinPrice(List<Stanza> stanze) {
        double min_prezzo = stanze.get(0).getCostoNotte();
        for(Stanza s: stanze){
            if(s.getCostoNotte() < min_prezzo) min_prezzo = s.getCostoNotte();
        }
        return min_prezzo;
    }

    /**
     * Restituisce la quantità maggiore di letti singoli trovata tra le stanze specificate.
     * @param stanze Le stanze tra cui effettuare la ricerca
     * @return La quantità maggiore di letti singoli
     */
    public int getMaxLetti_S(List<Stanza> stanze) {
        int maxLetti_s = stanze.get(0).getLettiSingoli();
        for(Stanza s: stanze){
            if(s.getLettiSingoli() > maxLetti_s) maxLetti_s = s.getLettiSingoli();
        }
        return maxLetti_s;
    }

    /**
     * Restituisce la quantità maggiore di letti matrimoniali trovata tra le stanze specificate.
     * @param stanze Le stanze tra cui effettuare la ricerca
     * @return La quantità maggiore di letti matrimoniali
     */
    public int getMaxLetti_M(List<Stanza> stanze) {
        int maxLetti_m = stanze.get(0).getLettiMatrimoniali();
        for(Stanza s: stanze){
            if(s.getLettiMatrimoniali() > maxLetti_m) maxLetti_m = s.getLettiMatrimoniali();
        }
        return maxLetti_m;
    }

    /**
     * Restituisce le stanze trovate secondo i valori specificati.
     * @param animaleDomestico Idoneità per animali domestici
     * @param fumatore Idoneità per fumatori
     * @param lettiSingoli Quantità letti singoli
     * @param lettiMatrimoniali Quantità letti matrimoniali
     * @param costoNotteMinimo Costo per notte minimo
     * @param costoNotteMassimo Costo per notte massimo
     * @param scontoMinimo Sconto applicabile minimo
     * @param scontoMassimo Sconto applicabile massimo
     * @param dataIn Data di ingresso
     * @param dataOut Data di uscita
     * @return Le stanze trovate
     */
    public List<Stanza> search(Boolean animaleDomestico, Boolean fumatore, Integer lettiSingoli,
                                      Integer lettiMatrimoniali, Double costoNotteMinimo, Double costoNotteMassimo,
                                      Double scontoMinimo, Double scontoMassimo, java.sql.Date dataIn, Date dataOut) {
        return dao.doSearch(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali,
                costoNotteMinimo, costoNotteMassimo, scontoMinimo, scontoMassimo, dataIn, dataOut);
    }

    /**
     * Restituisce la stanza trovata secondo l'identificativo specificato.
     * @param stanzaId Identificativo della stanza
     * @return Stanza trovata
     * @throws StanzaNotFoundException Non è stata trovata la stanza cercata
     */
    public Stanza selectById(Integer stanzaId) throws StanzaNotFoundException {
        return dao.doSelectById(stanzaId);
    }

}
