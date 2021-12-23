package it.hotel.model.stanza;

/**
 * Rappresenta una Stanza.
 */
public class Stanza {

    private int idStanza;
    private boolean animaleDomestico;
    private boolean fumatore;
    private int lettiSingoli;
    private int lettiMatrimoniali;
    private double costoNotte;
    private double sconto;

    /**
     * Costruisce una Stanza con i parametri specificati.
     * @param idStanza
     * @param animaleDomestico
     * @param fumatore
     * @param lettiSingoli
     * @param lettiMatrimoniali
     * @param costoNotte
     * @param sconto
     */
    public Stanza(int idStanza, boolean animaleDomestico, boolean fumatore, int lettiSingoli,
            int lettiMatrimoniali, double costoNotte, double sconto) {
        this.idStanza = idStanza;
        this.animaleDomestico = animaleDomestico;
        this.fumatore = fumatore;
        this.lettiSingoli = lettiSingoli;
        this.lettiMatrimoniali = lettiMatrimoniali;
        this.costoNotte = costoNotte;
        this.sconto = sconto;
    }

    /**
     * Restituisce l'identificativo della Stanza.
     * @return Identificativo
     */
    public int getIdStanza() {
        return idStanza;
    }

    /**
     * Informa se la Stanza è idonea per animali domestici.
     * @return Idoneità per animali domestici
     */
    public boolean getAnimaleDomestico() {
        return animaleDomestico;
    }

    /**
     * Informa se la Stanza è idonea per fumatori.
     * @return Idoneità per fumatori
     */
    public boolean getFumatore() {
        return fumatore;
    }

    /**
     * Restituisce la quantità di letti singoli disponibili nella Stanza.
     * @return Quantità letti singoli
     */
    public int getLettiSingoli() {
        return lettiSingoli;
    }

    /**
     * Restituisce la quantità di letti matrimoniali disponibili nella Stanza.
     * @return Quantità letti matrimoniali
     */
    public int getLettiMatrimoniali() {
        return lettiMatrimoniali;
    }

    /**
     * Restituisce il costo per notte della Stanza.
     * @return Costo per notte
     */
    public double getCostoNotte() {
        return costoNotte;
    }

    /**
     * Restituisce lo sconto applicabile sul costo per notte della Stanza.
     * @return Sconto applicabile
     */
    public double getSconto() {
        return sconto;
    }

}
