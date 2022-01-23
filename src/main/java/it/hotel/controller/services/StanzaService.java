package it.hotel.controller.services;


import it.hotel.model.stanza.Stanza;
import it.hotel.model.stanza.StanzaDAO;
import it.hotel.model.stanza.stanzaExceptions.StanzaNotFoundException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StanzaService
{
    private final StanzaDAO dao;

    public StanzaService()
    {
        dao=new StanzaDAO();
    }

    public List<Stanza> getStanze() {
        return dao.getStanze();
    }

    public double getMaxPrice(List<Stanza> stanze) {
        double max_prezzo = stanze.get(0).getCostoNotte();
        for(Stanza s: stanze){
            if(s.getCostoNotte() > max_prezzo) max_prezzo = s.getCostoNotte();
        }
        return max_prezzo;
    }

    public double getMinPrice(List<Stanza> stanze) {
        double min_prezzo = stanze.get(0).getCostoNotte();
        for(Stanza s: stanze){
            if(s.getCostoNotte() < min_prezzo) min_prezzo = s.getCostoNotte();
        }
        return min_prezzo;
    }

    public int getMaxLetti_S(List<Stanza> stanze) {
        int maxLetti_s = stanze.get(0).getLettiSingoli();
        for(Stanza s: stanze){
            if(s.getLettiSingoli() > maxLetti_s) maxLetti_s = s.getLettiSingoli();
        }
        return maxLetti_s;
    }

    public int getMaxLetti_M(List<Stanza> stanze) {
        int maxLetti_m = stanze.get(0).getLettiMatrimoniali();
        for(Stanza s: stanze){
            if(s.getLettiMatrimoniali() > maxLetti_m) maxLetti_m = s.getLettiMatrimoniali();
        }
        return maxLetti_m;
    }

    public List<Stanza> search(Boolean animaleDomestico, Boolean fumatore, Integer lettiSingoli,
                                      Integer lettiMatrimoniali, Double costoNotteMinimo, Double costoNotteMassimo,
                                      Double scontoMinimo, Double scontoMassimo, java.sql.Date dataIn, Date dataOut) {
        return dao.doSearch(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali,
                costoNotteMinimo, costoNotteMassimo, scontoMinimo, scontoMassimo, dataIn, dataOut);
    }

    public Stanza selectById(Integer stanzaId) throws StanzaNotFoundException {
        return dao.doSelectById(stanzaId);
    }

}
