package it.hotel.controller.api;

import it.hotel.Utility.Utility;
import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.EmailAlreadyExistingException;
import org.json.JSONObject;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

/**
 * <h1>Modifica Anagrafica Utente</h1>
 * API per modificare l'anagrafica di un utente
 * @author Sais Raffaele
 * @version 1.1
 * @since 2022-01-22
 */
@WebServlet(name = "ModificaAnagrafica", value = "/api/ModificaAnagrafica")
public class ModificaAnagrafica extends CheckServlet
{
    /**
     * Ritorna utente service
     * @see UtenteService
     */
    public UtenteService getUtenteService()
    {
        return new UtenteService();
    }
    /**
     * Richiesta che riceve tutti i dati dell'utente incluso il suo token per
     * verificare che sia davvero lui
     * @param request Richiesta del cliente
     * @param response Risposta per inviare il JSON
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JSONObject obj=new JSONObject();
        if(contieneParametro(request,"textNome") && contieneParametro(request,"textCognome") && contieneParametro(request,"textCodiceFiscale")
                && contieneParametro(request,"textDataNascita") && contieneParametro(request,"textEmail")
                        && contieneParametro(request,"textIdUtente") && contieneParametro(request,"textToken"))
        {
            try
            {
                int idUtente=Integer.parseInt(request.getParameter("textIdUtente"));
                String tokenAuth=request.getParameter("textToken");
                String textNome=request.getParameter("textNome");
                String textCognome=request.getParameter("textCognome");
                String textCodiceFiscale=request.getParameter("textCodiceFiscale");
                String textDataNascita=request.getParameter("textDataNascita");
                String textEmail=request.getParameter("textEmail");
                UtenteService service = getUtenteService();
                service.editAnagrafica(idUtente, tokenAuth, textNome, textCognome, textCodiceFiscale, textDataNascita, textEmail);
                HttpSession session=request.getSession(true);
                Utente ul = (Utente)session.getAttribute(Utility.SESSION_USER);
                Utente ut = new Utente(idUtente, ul.getRuolo(), textCodiceFiscale, textNome, textCognome,textEmail, Utility.dataConverter(textDataNascita),tokenAuth);
                session.setAttribute(Utility.SESSION_USER,ut);
                obj.put("Ris",1);
                obj.put("Mess","Fatto");
            }
            catch (EmailAlreadyExistingException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","L'email e' gia inserita");
            }
            catch (NumberFormatException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Id utente non valido");
            }
            catch (ParseException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Data non valida");
            }
            catch (SQLException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Errore db");
            }
        }
        else
        {
            obj.put("Ris",0);
            obj.put("Mess","Inserisci tutti i parametri");
        }
        response.getOutputStream().print(obj.toString());
    }
}
