package it.hotel.model.utente;

import it.hotel.Utility.Utility;
import it.hotel.model.ruolo.Ruolo;

import java.sql.Date;

/**
 * Rappresenta un Utente.
 */
public class Utente {

    private int idUtente;
    private int ruolo;
    private String cf;
    private String nome;
    private String cognome;
    private String email;
    private Date dataNascita;
    private String tokenAuth;

    /**
     * Costruisce un Utente con i parametri specificati.
     * @param idUtente Identificativo
     * @param ruolo Ruolo
     * @param cf Codice fiscale
     * @param nome Nome
     * @param cognome Cognome
     * @param email Indirizzo email
     * @param dataNascita Data di nascita
     * @param tokenAuth Token di autenticazione
     */
    public Utente(int idUtente, int ruolo, String cf, String nome, String cognome,
                  String email, Date dataNascita, String tokenAuth) {
        this.idUtente = idUtente;
        this.ruolo = ruolo;
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataNascita = dataNascita;
        this.tokenAuth = tokenAuth;
    }

    /**
     * Restituisce l'identificativo dell'utente
     * @return Identificativo
     */
    public int getIdUtente() {
        return idUtente;
    }

    /**
     * Restituisce il ruolo dell'utente
     * @return Ruolo
     */
    public int getRuolo() {
        return ruolo;
    }

    /**
     * Restituisce il codice fiscale dell'utente
     * @return Codice fiscale
     */
    public String getCf() {
        return cf;
    }

    /**
     * Restituisce il nome dell'utente
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il cognome dell'utente
     * @return Cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Restituisce l'indirizzo email dell'utente
     * @return Indirizzo email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Restituisce la data di nascita dell'utente
     * @return Data di nascita
     */
    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * Restituisce il token di autenticazione dell'utente
     * @return Token di autenticazione
     */
    public String getTokenAuth() {
        return tokenAuth;
    }

    /**
     * @return Nome del ruolo
     */

    public String getRuoloName()
    {

        for(Ruolo r: Utility.listRuoli)
        {
            if(r.getIdRuolo()== ruolo)
                return r.getRuolo();
        }
        return "N/D";
    }
}
