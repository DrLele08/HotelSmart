package it.hotel.model.stanza;

public class Stanza {

    private int idStanza;
    private boolean animaleDomestico;
    private boolean fumatore;
    private int lettiSingoli;
    private int lettiMatrimoniali;
    private double costoNotte;
    private double sconto;

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

    public int getIdStanza() {
        return idStanza;
    }

    public boolean getAnimaleDomestico() {
        return animaleDomestico;
    }

    public boolean getFumatore() {
        return fumatore;
    }

    public int getLettiSingoli() {
        return lettiSingoli;
    }

    public int getLettiMatrimoniali() {
        return lettiMatrimoniali;
    }

    public double getCostoNotte() {
        return costoNotte;
    }

    public double getSconto() {
        return sconto;
    }

    @Override
    public String toString() {
        return "Stanza{" +
                "idStanza=" + idStanza +
                ", animaleDomestico=" + animaleDomestico +
                ", fumatore=" + fumatore +
                ", lettiSingoli=" + lettiSingoli +
                ", lettiMatrimoniali=" + lettiMatrimoniali +
                ", costoNotte=" + costoNotte +
                ", sconto=" + sconto +
                '}';
    }
}
