package it.hotel.model.stanza;

import it.hotel.model.stanza.stanzaExceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornisce l'accesso al database per {@link Stanza}.
 */
public class StanzaDAO {

    /**
     * Inserisce nel database un oggetto Stanza secondo i valori specificati.
     * @param con Connessione al database
     * @param animaleDomestico Idoneità per animali domestici
     * @param fumatore Idoneità per fumatori
     * @param lettiSingoli Quantità letti singoli
     * @param lettiMatrimoniali Quantità letti matrimoniali
     * @param costoNotte Costo per notte
     * @param sconto Sconto applicabile
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doInsert(Connection con, boolean animaleDomestico, boolean fumatore, int lettiSingoli, int lettiMatrimoniali,
                         double costoNotte, double sconto) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("INSERT INTO Stanza (animaleDomestico, fumatore, lettiSingoli," +
                                " lettiMatrimoniali, costoNotte, sconto) VALUES(?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setBoolean(1, animaleDomestico);
        ps.setBoolean(2, fumatore);
        ps.setInt(3, lettiSingoli);
        ps.setInt(4, lettiMatrimoniali);
        ps.setDouble(5, costoNotte);
        ps.setDouble(6, sconto);
        ps.executeUpdate();
    }

    /**
     * Aggiorna un oggetto Stanza nel database secondo i valori specificati.
     * @param con Connessione al database
     * @param animaleDomestico Idoneità per animali domestici
     * @param fumatore Idoneità per fumatori
     * @param lettiSingoli Quantità letti singoli
     * @param lettiMatrimoniali Quantità letti matrimoniali
     * @param costoNotte Costo per notte
     * @param sconto Sconto applicabile
     * @throws SQLException Errore nella comunicazione con il database
     */
    public void doUpdate(Connection con, int idStanza, boolean animaleDomestico, boolean fumatore, int lettiSingoli,
                         int lettiMatrimoniali, double costoNotte, double sconto) throws SQLException {
        PreparedStatement ps = con.prepareStatement
                ("UPDATE Stanza SET animaleDomestico=?, fumatore=?, lettiSingoli=?, lettiMatrimoniali=?, " +
                                "costoNotte=?, sconto=? WHERE idStanza=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setBoolean(1, animaleDomestico);
        ps.setBoolean(2, fumatore);
        ps.setInt(3, lettiSingoli);
        ps.setInt(4, lettiMatrimoniali);
        ps.setDouble(5, costoNotte);
        ps.setDouble(6, sconto);
        ps.setInt(7, idStanza);
        ps.executeUpdate();
    }

    /**
     * Recupera l'oggetto Stanza trovato nel database secondo il valore specificato.
     * @param con Connessione al database
     * @param idStanza Id che identifica la stanza cercata
     * @return La stanza trovata nel database
     * @throws StanzaNotFoundException La stanza cercata non è presente nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public Stanza doSelectById(Connection con, int idStanza) throws StanzaNotFoundException, SQLException {
        Stanza stanza;
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Stanza WHERE idStanza=?",
                        Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, idStanza);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            stanza = createStanza(rs);
        } else {
            throw new StanzaNotFoundException();
        }
        return stanza;
    }

    /**
     * Recupera tutti gli oggetti Stanza presenti nel database.
     * @param con Connessione al database
     * @return Le stanze presenti nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<Stanza> getStanze(Connection con) throws SQLException {
        ArrayList<Stanza> stanze = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Stanza",
                        Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            stanze.add(createStanza(rs));
        }
        return stanze;
    }

    /**
     * Recupera gli oggetti Stanza presenti nel database che hanno uno sconto maggiore di ZERO.
     * @param con Connessione al database
     * @return Lista contenente le stanze con uno sconto maggiore di ZERO.
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<Stanza> getOfferte(Connection con) throws SQLException {
        ArrayList<Stanza> stanze = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Stanza WHERE Stanza.sconto > 0",
                        Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            stanze.add(createStanza(rs));
        }
        return stanze;
    }

    /**
     * Recupera il prezzo più basso e il prezzo più alto tra tutti gli oggetti Stanza presenti nel database.
     * @param con Connessione al database
     * @return Lista contenente il prezzo più basso e il prezzo più alto
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<Double> doSelect_Min_And_Max_Prices(Connection con) throws SQLException {
        ArrayList<Double> prezzi = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement
                ("SELECT MIN(costoNotte), MAX(costoNotte) FROM Stanza",
                        Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            prezzi.add(rs.getDouble(1));
            prezzi.add(rs.getDouble(2));
        }
        return prezzi;
    }

    /**
     * Informa se l'oggetto Stanza specificato è disponibile per una prenotazione nell'intervallo di tempo richiesto.
     * @param con Connessione al database
     * @param ksStanza Identificativo della stanza
     * @param dataInizio Data d'inizio della prenotazione desiderata
     * @param dataFine Data di fine della prenotazione desiderata
     * @return Idoneità per prenotazione
     * @throws SQLException Errore nella comunicazione con il database
     */
    public boolean isDisponibile(Connection con, int ksStanza, Date dataInizio, Date dataFine) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT idPrenotazioneStanza FROM PrenotazioneStanza WHERE " +
                        "ksStanza=? AND dataFine >= ? AND dataInizio <= ? AND ksStato NOT IN (SELECT idStato FROM Stato st WHERE " +
                        "(st.stato = 'ANNULLATA') OR (st.stato = 'ARCHIVIATA') OR (st.stato = 'RIMBORSATA'))",
                Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, ksStanza);
        ps.setDate(2, dataInizio);
        ps.setDate(3, dataFine);
        ResultSet rs = ps.executeQuery();
        return !rs.next();
    }

    /**
     * Recupera gli oggetti Stanza trovati nel database secondo i valori specificati.
     * @param con Connessione al database
     * @param animaleDomestico Permesso animali domestici
     * @param fumatore Permesso fumatori
     * @param numeroOspiti Numero di ospiti
     * @param costoNotteMinimo Costo minimo per notte
     * @param costoNotteMassimo Costo massimo per notte
     * @param scontoMinimo Sconto minimo
     * @param scontoMassimo Sconto massimo
     * @param dataIn Data di entrata
     * @param dataOut Data di uscita
     * @return Le stanze trovate nel database
     * @throws SQLException Errore nella comunicazione con il database
     */
    public List<Stanza> doSearch(Connection con, Boolean animaleDomestico, Boolean fumatore, Integer numeroOspiti,
                                 Double costoNotteMinimo, Double costoNotteMassimo,
                                 Double scontoMinimo, Double scontoMassimo, Date dataIn, Date dataOut) throws SQLException {

        String query = getQuery(animaleDomestico, fumatore, numeroOspiti, costoNotteMinimo,
                costoNotteMassimo, scontoMinimo, scontoMassimo, dataIn, dataOut);

        ArrayList<Stanza> stanze = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement
                ("SELECT * FROM Stanza s" + query,
                        Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            stanze.add(createStanza(rs));
        }
        return stanze;

    }

    public String getQuery(Boolean animaleDomestico, Boolean fumatore, Integer numeroOspiti,
                            Double costoNotteMinimo, Double costoNotteMassimo,
                            Double scontoMinimo, Double scontoMassimo, Date dataIn, Date dataOut) {

        String query;
        boolean p1_AND_p2_AND_p3_Boolean, NOT_EXISTS_prenotazioneStanza_Boolean;

        ArrayList<String> parametri = new ArrayList<>();
        animaleDomesticoStr(parametri, animaleDomestico);
        fumatoreStr(parametri, fumatore);
        numeroOspitiStr(parametri,numeroOspiti);
        costoNotteStr(parametri, costoNotteMinimo, costoNotteMassimo);
        scontoStr(parametri, scontoMinimo, scontoMassimo);

        String p1_AND_p2_AND_p3 = "";
        if (parametri.size() > 0) {
            for (int i = 0; i < parametri.size() - 1; i++) {
                p1_AND_p2_AND_p3 += parametri.get(i) + " AND ";
            }
            p1_AND_p2_AND_p3 += parametri.get(parametri.size() - 1);
            p1_AND_p2_AND_p3_Boolean = true;
        } else {
            p1_AND_p2_AND_p3_Boolean = false;
        }

        String NOT_EXISTS_prenotazioneStanza = "";
        if ((dataIn != null) || (dataOut != null)) {
            NOT_EXISTS_prenotazioneStanza = data("NOT EXISTS ", dataIn, dataOut);
            NOT_EXISTS_prenotazioneStanza_Boolean = true;
        } else {
            NOT_EXISTS_prenotazioneStanza_Boolean = false;
        }

        if (p1_AND_p2_AND_p3_Boolean && NOT_EXISTS_prenotazioneStanza_Boolean) {
            query = " WHERE " + p1_AND_p2_AND_p3 + " AND " + NOT_EXISTS_prenotazioneStanza;
        } else if (p1_AND_p2_AND_p3_Boolean) {
            query = " WHERE " + p1_AND_p2_AND_p3;
        } else {
            query = " WHERE "+ NOT_EXISTS_prenotazioneStanza;
        }

        return query;
    }

    public void animaleDomesticoStr(List<String> parametri, Boolean animaleDomestico) {
        if (animaleDomestico != null) {
            if (animaleDomestico) {
                parametri.add("animaleDomestico = TRUE");
            } else {
                parametri.add("animaleDomestico = FALSE");
            }
        }
    }

    public void fumatoreStr(List<String> parametri, Boolean fumatore) {
        if (fumatore != null) {
            if (fumatore) {
                parametri.add("fumatore = TRUE");
            } else {
                parametri.add("fumatore = FALSE");
            }
        }
    }

    public void numeroOspitiStr(List<String> parametri, Integer numeroOspiti) {
        if (numeroOspiti != null) {
            parametri.add("lettiMatrimoniali*2 + lettiSingoli = " + numeroOspiti);
        }
    }

    public void costoNotteStr(List<String> parametri, Double costoNotteMinimo, Double costoNotteMassimo) {
        if ((costoNotteMinimo != null) && (costoNotteMassimo != null)) {
            parametri.add("costoNotte >= " + costoNotteMinimo + " AND " +
                    "costoNotte <= " + costoNotteMassimo);
        } else if (costoNotteMinimo != null) {
            parametri.add("costoNotte >= " + costoNotteMinimo);
        } else if (costoNotteMassimo != null) {
            parametri.add("costoNotte <= " + costoNotteMassimo);
        }
    }

    public void scontoStr(List<String> parametri, Double scontoMinimo, Double scontoMassimo) {
        if ((scontoMinimo != null) && (scontoMassimo != null)) {
            parametri.add("sconto >= " + scontoMinimo + " AND " +
                    "sconto <= " + scontoMassimo);
        } else if (scontoMinimo != null) {
            parametri.add("sconto >= " + scontoMinimo);
        } else if (scontoMassimo != null) {
            parametri.add("sconto <= " + scontoMassimo);
        }
    }

    public String data(String NOT_EXISTS_prenotazioneStanza, Date dataIn, Date dataOut) {
        NOT_EXISTS_prenotazioneStanza += "(SELECT idPrenotazioneStanza FROM PrenotazioneStanza rs WHERE (";
        NOT_EXISTS_prenotazioneStanza += "rs.ksStanza = s.idStanza AND ";
        String inizio = "";
        String fine = "";
        if (dataIn != null) {
            inizio += "rs.dataFine >= " + "\'" + dataIn + "\'";
        }
        if (dataOut != null) {
            fine += "rs.dataInizio <= " + "\'" + dataOut + "\'";
        }

        if ((!inizio.equals("")) && (!fine.equals(""))) {
            NOT_EXISTS_prenotazioneStanza += inizio + " AND " + fine + ")";
        } else if (inizio.equals("")) {
            NOT_EXISTS_prenotazioneStanza += fine + ")";
        } else {
            NOT_EXISTS_prenotazioneStanza += inizio + ")";
        }
        String statoCheck = " AND rs.ksStato NOT IN (SELECT idStato FROM Stato st WHERE (st.stato = \"ANNULLATA\") OR (st.stato = \"ARCHIVIATA\") OR (st.stato = \"RIMBORSATA\")))";
        return NOT_EXISTS_prenotazioneStanza + statoCheck;
    }

    private Stanza createStanza(ResultSet rs) throws SQLException {
        return new Stanza(rs.getInt(1), rs.getBoolean(2), rs.getBoolean(3),
                rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7));
    }

}
