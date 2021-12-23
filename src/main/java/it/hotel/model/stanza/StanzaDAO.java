package it.hotel.model.stanza;

import it.hotel.Utility.Connect;
import it.hotel.model.stanza.stanzaExceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StanzaDAO {

    public void doInsert(Stanza stanza) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Stanza (animaleDomestico, fumatore, lettiSingoli," +
                                    " lettiMatrimoniali, costoNotte, sconto) VALUES(?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, stanza.getAnimaleDomestico());
            ps.setBoolean(2, stanza.getFumatore());
            ps.setInt(3, stanza.getLettiSingoli());
            ps.setInt(4, stanza.getLettiMatrimoniali());
            ps.setDouble(5, stanza.getCostoNotte());
            ps.setDouble(6, stanza.getSconto());

            ResultSet rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Stanza doSelectById(int idStanza) throws StanzaNotFoundException {
        Stanza stanza;
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Stanza WHERE idStanza=?",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idStanza);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stanza = new Stanza(rs.getInt(1), rs.getBoolean(2), rs.getBoolean(3),
                        rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7));
            } else {
                throw new StanzaNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stanza;
    }

    public List<Stanza> doSearch(Boolean animaleDomestico, Boolean fumatore, Integer lettiSingoli,
                                 Integer lettiMatrimoniali, Double costoNotteMinimo, Double costoNotteMassimo,
                                 Double scontoMinimo, Double scontoMassimo, Date dataIn, Date dataOut) {

        String query = getQuery(animaleDomestico, fumatore, lettiSingoli, lettiMatrimoniali, costoNotteMinimo,
                costoNotteMassimo, scontoMinimo, scontoMassimo, dataIn, dataOut);

        ArrayList<Stanza> stanze = new ArrayList<>();
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Stanza s" + query,
                            Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                stanze.add(new Stanza(rs.getInt(1), rs.getBoolean(2), rs.getBoolean(3),
                        rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7)));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stanze;

    }

    private String getQuery(Boolean animaleDomestico, Boolean fumatore, Integer lettiSingoli,
                            Integer lettiMatrimoniali, Double costoNotteMinimo, Double costoNotteMassimo,
                            Double scontoMinimo, Double scontoMassimo, Date dataIn, Date dataOut) {

        String query;
        boolean p1_AND_p2_AND_p3_Boolean, NOT_EXISTS_prenotazioneStanza_Boolean;

        ArrayList<String> parametri = new ArrayList<>();
        animaleDomesticoStr(parametri, animaleDomestico);
        fumatoreStr(parametri, fumatore);
        lettiSingoliStr(parametri, lettiSingoli);
        lettiMatrimonialiStr(parametri, lettiMatrimoniali);
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

    private void animaleDomesticoStr(List<String> parametri, Boolean animaleDomestico) {
        if (animaleDomestico == null) {
            return;
        } else if (animaleDomestico) {
            parametri.add("animaleDomestico = TRUE");
        } else {
            parametri.add("animaleDomestico = FALSE");
        }
    }

    private void fumatoreStr(List<String> parametri, Boolean fumatore) {
        if (fumatore == null) {
            return;
        } else if (fumatore) {
            parametri.add("fumatore = TRUE");
        } else {
            parametri.add("fumatore = FALSE");
        }
    }

    private void lettiSingoliStr(List<String> parametri, Integer lettiSingoli) {
        if (lettiSingoli == null) {
            return;
        } else {
            parametri.add("lettiSingoli = " + lettiSingoli);
        }
    }

    private void lettiMatrimonialiStr(List<String> parametri, Integer lettiMatrimoniali) {
        if (lettiMatrimoniali == null) {
            return;
        } else {
            parametri.add("lettiMatrimoniali = " + lettiMatrimoniali);
        }
    }

    private void costoNotteStr(List<String> parametri, Double costoNotteMinimo, Double costoNotteMassimo) {
        if ((costoNotteMinimo == null) && (costoNotteMassimo == null)) {
            return;
        } else if ((costoNotteMinimo != null) && (costoNotteMassimo != null)) {
            parametri.add("costoNotte >= " + costoNotteMinimo + " AND " +
                    "costoNotte <= " + costoNotteMassimo);
        } else if (costoNotteMinimo != null) {
            parametri.add("costoNotte >= " + costoNotteMinimo);
        } else if (costoNotteMassimo != null) {
            parametri.add("costoNotte <= " + costoNotteMassimo);
        }
    }

    private void scontoStr(List<String> parametri, Double scontoMinimo, Double scontoMassimo) {
        if ((scontoMinimo == null) && (scontoMassimo == null)) {
            return;
        } else if ((scontoMinimo != null) && (scontoMassimo != null)) {
            parametri.add("sconto >= " + scontoMinimo + " AND " +
                    "sconto <= " + scontoMassimo);
        } else if (scontoMinimo != null) {
            parametri.add("sconto >= " + scontoMinimo);
        } else if (scontoMassimo != null) {
            parametri.add("sconto <= " + scontoMassimo);
        }
    }

    private String data(String NOT_EXISTS_prenotazioneStanza, Date dataIn, Date dataOut) {
        NOT_EXISTS_prenotazioneStanza += "(SELECT 1 FROM PrenotazioneStanza rs WHERE (";
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
            NOT_EXISTS_prenotazioneStanza += inizio + " AND " + fine + "))";
        } else if (inizio.equals("")) {
            NOT_EXISTS_prenotazioneStanza += fine + "))";
        } else {
            NOT_EXISTS_prenotazioneStanza += inizio + "))";
        }
        return NOT_EXISTS_prenotazioneStanza;
    }

}
