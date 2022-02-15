package it.hotel.Utility.payment;

import it.hotel.model.prenotazioneStanza.PrenotazioneStanza;

/**
 * Interfaccia per effettuare il pagamento
 * @author Sais Raffaele
 */
public interface PaymentAdapter
{
    /**
     * Metodo per effettuare il pagamento di una prenotazione
     * @param preno PrenotazioneStanza
     * @param link Url del sito
     * @return Il link del gataway di pagamento per effettuare l'operazione
     */
    String makePayment(PrenotazioneStanza preno,String link);

    /**
     * Metodo che controlla se il pagamento è stato effettuato
     * @param idPagamento
     * @return Verifica se il pagamento è stato effettuato
     */
    boolean isPagato(String idPagamento);
}
