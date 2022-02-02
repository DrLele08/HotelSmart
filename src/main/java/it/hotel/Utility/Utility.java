package it.hotel.Utility;

import it.hotel.model.ruolo.Ruolo;
import it.hotel.model.stato.Stato;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * <h1>Utility Generiche</h1>
 * Contiene tutte le informazioni generiche per offrire i servizi
 * @author Sais Raffaele
 * @version 1.9
 * @since 2022-12-15
 */
public class Utility
{
    /**
     * Pattern per la password degli utenti
    */
    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    /**
     * Lunghezza token autenticazione
    */
    public static final int lenghtAuth=64;
    /**
     * API Key di Stripe
    */
    public static final String stripeKey="sk_test_51KLDXkBGMwZsdNHVNexZB0QYRKoufGyY1XkvZqIvRUncWZIrTwuxFmWA2v9mfWkRHkrdzHmeQfFHsQGKHWu7SYvO00PAVrndqP";
    /**
     * Nome dell'attributo utente all'interno della sessione
    */
    public static final String SESSION_USER="idUsSe";
    /**
     * Nome del ID dell'utente per il cookie
    */
    public static final String COOKIE_ID="idUsCo";
    /**
     * Nome del tokenAuth dell'utente per il cookie
    */
    public static final String COOKIE_TOKEN="toUsCo";
    /**
     * Nome per controllare se il login è attivo
     */
    public static final String CHECK_LOGIN="ACTIVE_LOGIN";
    /**
     * Lista con i vari ruoli
     */
    public static List<Ruolo> listRuoli=new ArrayList<>();
    /**
     * Lista con i vari stati
     */
    public static List<Stato> listStato=new ArrayList<>();

    /**
     * Controlla se è possibile accedere a quella funzione RunTime
     * @param keyType Chiave DB per la relativa funzione
     * @return Booleano per controllare se è attiva o meno
     */
    public static boolean isActive(String keyType)
    {
        return UtilityDAO.isActive(keyType);
    }

    /**
     * Ritorna una data dando una stringa come parametro
     * @param dataStr Stringa in formato YYYY-mm-dd
     * @return Data convertita
     */
    public static Date dataConverter(String dataStr) throws ParseException
    {
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dataUtil = in.parse(dataStr);
        return new Date(dataUtil.getTime());
    }

    /**
     * restituisce una data formattata in standard italiano
     * @param date data non formattata YYYY-mm-dd
     * @return date formattato
     */
    public static String convertDateToView(Date date)
    {
        final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

}
