package it.hotel.model.prenotazioneStanza;

import it.hotel.Utility.Connect;
import it.hotel.model.stanza.Stanza;

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

}
