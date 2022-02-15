package it.hotel.model.personaAggiuntiva;

import java.sql.Date;
import java.text.ParseException;

/**
 * Rappresenta una PersonaAggiuntiva e fornisce metodi per il recupero dello stato.
 */
public class PersonaAggiuntiva {

    private int idPersona;
    private int ksUtente;
    private String cf;
    private String nome;
    private String cognome;
    private Date dataNascita;

    /**
     * Costruisce una PersonaAggiuntiva con i valori specificati.
     * @param idPersona Identificativo della persona
     * @param ksUtente Identificativo dell'utente
     * @param cf Codice fiscale
     * @param nome Nome
     * @param cognome Cognome
     * @param dataNascita Data di nascita
     */
    public PersonaAggiuntiva(int idPersona, int ksUtente, String cf,
            String nome, String cognome, Date dataNascita) {
        this.idPersona = idPersona;
        this.ksUtente = ksUtente;
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    /**
     * Costruisce una PersonaAggiuntiva con i valori specificati.
     * @param cf Codice fiscale
     * @param nome Nome
     * @param cognome Cognome
     * @param dataNascita Data di nascita
     */
    public PersonaAggiuntiva(String cf, String nome, String cognome, String dataNascita) throws ParseException {
        this.idPersona = -1;
        this.ksUtente = -1;
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = it.hotel.Utility.Utilita.dataConverter(dataNascita);
    }

    /**
     * @return Identificativo della persona
     */
    public int getIdPersona() {
        return idPersona;
    }

    /**
     * @return Identificativo dell'utente
     */
    public int getKsUtente() {
        return ksUtente;
    }

    /**
     * @return Codice fiscale
     */
    public String getCf() {
        return cf;
    }

    /**
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return Cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @return Data di nascita
     */
    public Date getDataNascita() {
        return dataNascita;
    }
}
