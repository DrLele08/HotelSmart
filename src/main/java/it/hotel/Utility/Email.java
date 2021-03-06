package it.hotel.Utility;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
/**
 * <h1>Invio Email</h1>
 * Invio di una mail
 * @author Sais Raffaele
 * @version 1.6
 * @since 2022-02-03
 */
public class Email
{
    /**
     * Email del mittente
     */
    private static final String senderEmail = "tsw@saisraffaele.it";
    /**
     * Password del mittente
     */
    private static final String senderPassword = ";7@rk$u0lOX8";


    /**
     * Preparara l'email da essere inviata
     * @param to Email del destinatario
     * @param title Titolo della mail
     * @param html Corpo del messaggio
     */
    public static void sendAsHtml(String to, String title, String html)
    {
        try
        {
            System.out.println("Sending email to " + to);
            Session session = createSession();
            MimeMessage message = new MimeMessage(session);
            prepareEmailMessage(message, to, title, html);
            Transport.send(message);
            System.out.println("Done");
        }
        catch (MessagingException ignored) {}
    }


    /**
     * Invia email con codice HTML
     * @param message Flusso per inviare il messaggio
     * @param to Email del destinatario
     * @param title Titolo della mail
     * @param html Corpo del messaggio
     * @see MimeMessage
     * @exception MessagingException Eccezzione nel caso in cui il messaggio non viene inviato
     */
    private static void prepareEmailMessage(MimeMessage message, String to, String title, String html) throws MessagingException
    {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

    /**
     * Crea la sessione per inviare un messaggio
     */
    private static Session createSession()
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.saisraffaele.it");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }
}