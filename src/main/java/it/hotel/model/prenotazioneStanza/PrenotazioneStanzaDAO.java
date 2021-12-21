package it.hotel.model.prenotazioneStanza;

import it.hotel.Utility.Connect;

import java.sql.*;

public class PrenotazioneStanzaDAO {

    public void doInsert(PrenotazioneStanza prenotazioneStanza) {
        try (Connection con = Connect.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO PrenotazioneStanza (ksUtente, ksStanza," +
                            " ksStato, dataInizio, dataFine, prezzoFinale, tokenStripe, tokenQr," +
                                    " commenti, valutazione) VALUES(?,?,?,?,?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, prenotazioneStanza.getKsUtente());
            ps.setInt(2, prenotazioneStanza.getKsStanza());
            ps.setInt(3, prenotazioneStanza.getKsStato());
            ps.setDate(4, prenotazioneStanza.getDataInizio());
            ps.setDate(5, prenotazioneStanza.getDataFine());
            ps.setDouble(6, prenotazioneStanza.getPrezzoFinale());
            ps.setString(7, prenotazioneStanza.getTokenStripe());
            ps.setString(8, prenotazioneStanza.getTokenQr());
            ps.setString(9, prenotazioneStanza.getCommenti());
            ps.setInt(10, prenotazioneStanza.getValutazione());

            ResultSet rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PrenotazioneStanza doSelectById(int idPrenotazioneStanza) throws PrenotazioneStanzaNotFoundException {
        PrenotazioneStanza prenotazioneStanza;
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
                throw new PrenotazioneStanzaNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stanza;
    }

}
