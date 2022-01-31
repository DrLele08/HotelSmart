package it.hotel.model.personaAggiuntiva;

import it.hotel.Utility.Utility;

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
        this.dataNascita = Utility.dataConverter(dataNascita);
    }

    /**
     * @return
     */
    public int getIdPersona() {
        return idPersona;
    }

    /**
     * @return
     */
    public int getKsUtente() {
        return ksUtente;
    }

    /**
     * @return
     */
    public String getCf() {
        return cf;
    }

    /**
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @return
     */
    public Date getDataNascita() {
        return dataNascita;
    }
}
