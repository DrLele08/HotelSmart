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
                Double scontoMinimo, Double scontoMassimo) {

        ArrayList<String> parametri = new ArrayList<>();

        animaleDomesticoStr(parametri, animaleDomestico);
        fumatoreStr(parametri, fumatore);

        if (lettiSingoli != null) {
            parametri.add(lettiSingoliStr(lettiSingoli));
        }
        if (lettiMatrimoniali != null) {
            parametri.add(lettiMatrimonialiStr(lettiMatrimoniali));
        }
        if ((costoNotteMinimo != null) || (costoNotteMassimo != null)) {
            parametri.add(costoNotteStr(costoNotteMinimo ,costoNotteMassimo));
        }
        if ((scontoMinimo != null) || (scontoMassimo != null)) {
            parametri.add(scontoStr(scontoMinimo, scontoMassimo));
        }

        String where = "";
        if (parametri.size() > 0) {
            where += " WHERE ";
            for (int i = 0; i < parametri.size() - 1; i++) {
                where += parametri.get(i) + " AND ";
            }
            where += parametri.get(parametri.size() - 1);
        }

        ArrayList<Stanza> stanze = new ArrayList<>();

        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM Stanza" + where,
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

    private String lettiSingoliStr(Integer lettiSingoli) {
        return "lettiSingoli = " + lettiSingoli;
    }

    private String lettiMatrimonialiStr(Integer lettiMatrimoniali) {
        return "lettiMatrimoniali = " + lettiMatrimoniali;
    }

    private String costoNotteStr(Double costoNotteMinimo, Double costoNotteMassimo) {
        if ((costoNotteMinimo != null) && (costoNotteMassimo != null)) {
            return "costoNotte >= " + costoNotteMinimo + " AND " +
                    "costoNotte <= " + costoNotteMassimo;
        } else if (costoNotteMinimo != null) {
            return "costoNotte >= " + costoNotteMinimo;
        } else if (costoNotteMassimo != null) {
            return "costoNotte <= " + costoNotteMassimo;
        }
        return "";
    }

    private String scontoStr(Double scontoMinimo, Double scontoMassimo) {
        if ((scontoMinimo != null) && (scontoMassimo != null)) {
            return "sconto >= " + scontoMinimo + " AND " +
                    "sconto <= " + scontoMassimo;
        } else if (scontoMinimo != null) {
            return "sconto >= " + scontoMinimo;
        } else if (scontoMassimo != null) {
            return "sconto <= " + scontoMassimo;
        }
        return "";
    }

}
