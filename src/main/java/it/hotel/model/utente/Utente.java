package it.hotel.model.utente;

import java.sql.Date;

public class Utente {

    private int idUtente;
    private int ruolo;
    private String cf;
    private String nome;
    private String cognome;
    private String email;
    private Date dataNascita;
    private String tokenAuth;

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

    public int getIdUtente() {
        return idUtente;
    }

    public int getRuolo() {
        return ruolo;
    }

    public String getCf() {
        return cf;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public String getTokenAuth() {
        return tokenAuth;
    }

}
