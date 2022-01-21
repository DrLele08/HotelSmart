package it.hotel.controller.api;

import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.utenteExceptions.EmailAlreadyExistingException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;

@WebServlet(name = "ModificaAnagrafica", value = "/api/ModificaAnagrafica")
public class ModificaAnagrafica extends CheckServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JSONObject obj=new JSONObject();
        if(contieneParametro(request,"textNome") && contieneParametro(request,"textCognome") && contieneParametro(request,"textCodiceFiscale")
                && contieneParametro(request,"textDataNascita") && contieneParametro(request,"textEmail")
                        && contieneParametro(request,"textIdUtente") && contieneParametro(request,"textToken"))
        {
            int idUtente=Integer.parseInt(request.getParameter("textIdUtente"));
            String tokenAuth=request.getParameter("textToken");
            String textNome=request.getParameter("textNome");
            String textCognome=request.getParameter("textCognome");
            String textCodiceFiscale=request.getParameter("textCodiceFiscale");
            String textDataNascita=request.getParameter("textDataNascita");
            String textEmail=request.getParameter("textEmail");
            UtenteService service = new UtenteService();
            try
            {
                //TODO DATA NASCITA GESTIRE DAL SERVICE
                //TODO SERVE ANCHE TOKEN AUTH
                service.editAnagrafica(idUtente,tokenAuth, textNome, textCognome, textCodiceFiscale, textDataNascita, textEmail);
                obj.put("Ris",1);
                obj.put("Mess","Fatto");
            }
            catch (EmailAlreadyExistingException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","L'email è gia inserita");
            }
            catch (NumberFormatException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Id utente non valido");
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
