package it.hotel.model.servizio;

/**
 * Rappresenta un Servizio.
 */
public class Servizio {

    private int idServizio;
    private String nome;
    private String descrizione;
    private String foto;
    private double costo;
    private int limitePosti;

    /**
     * Costruisce un Servizio con i parametri specificati.
     * @param idServizio Identificativo
     * @param nome Nome
     * @param descrizione Descrizione
     * @param foto Percorso foto
     * @param costo Costo
     * @param limitePosti Posti disponibili
     */
    public Servizio(int idServizio, String nome, String descrizione, String foto, double costo, int limitePosti) {
        this.idServizio = idServizio;
        this.nome = nome;
        this.descrizione = descrizione;
        this.foto = foto;
        this.costo = costo;
        this.limitePosti = limitePosti;
    }

    /**
     * Restituisce l'identificativo del servizio
     * @return Identificativo
     */
    public int getIdServizio() {
        return idServizio;
    }

    /**
     * Restituisce il nome del servizio
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce la descrizione del servizio
     * @return Descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce il percorso della foto
     * @return Percorso foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Restituisce il costo
     * @return Costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Restituisce il limite dei posti
     * @return Posti disponibili
     */
    public int getLimitePosti() {
        return limitePosti;
    }
}
