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

    public String search(Boolean animaleDomestico, Boolean fumatore, Integer lettiSingoli,
            Integer lettiMatrimoniali, Double costoNotteMinimo, Double costoNotteMassimo, Double sconto) {

        ArrayList<String> parametri = new ArrayList<>();

        if (animaleDomestico != null) {
            parametri.add(animaleDomesticoStr(animaleDomestico));
        }
        if (fumatore != null) {
            parametri.add(fumatoreStr(fumatore));
        }
        if (lettiSingoli != null) {
            parametri.add(lettiSingoliStr(lettiSingoli));
        }
        if (lettiMatrimoniali != null) {
            parametri.add(lettiMatrimonialiStr(lettiMatrimoniali));
        }
        if ((costoNotteMinimo != null) || (costoNotteMassimo != null)) {
            parametri.add(costoNotteStr(costoNotteMinimo ,costoNotteMassimo));
        }
        if (sconto != null) {
            parametri.add(scontoStr(sconto));
        }

        String where = "";
        for (int i = 0; i < parametri.size() - 1; i++) {
            where += parametri.get(i) + " AND ";
        }
        where += parametri.get(parametri.size() - 1);

        return where;

    }

    private String animaleDomesticoStr(Boolean animaleDomestico) {
        if (animaleDomestico) {
            return "animaleDomestico = TRUE";
        } else {
            return "animaleDomestico = FALSE";
        }
    }

    private String fumatoreStr(Boolean fumatore) {
        if (fumatore) {
            return "fumatore = TRUE";
        } else {
            return "fumatore = FALSE";
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

    private String scontoStr(Double sconto) {
        return "sconto = " + sconto;
    }

}
