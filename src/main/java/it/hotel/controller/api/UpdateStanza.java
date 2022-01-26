package it.hotel.controller.api;

import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.StanzaService;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateStanza", value = "/api/UpdateStanza")
public class UpdateStanza extends CheckServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JSONObject obj=new JSONObject();
        if(contieneParametro(request,"idUtente") && contieneParametro(request,"Token") && contieneParametro(request,"idStanza")
                    && contieneParametro(request,"Animale")
                    && contieneParametro(request,"Fumatore") && contieneParametro(request,"LettiS") && contieneParametro(request,"LettiM")
                    && contieneParametro(request,"Costo") && contieneParametro(request,"Sconto"))
        {
            try
            {
                int idUtente=Integer.parseInt(request.getParameter("idUtente"));
                String token=request.getParameter("Token");
                UtenteService utenteService=new UtenteService();
                Utente user=utenteService.doLogin(idUtente,token);
                if(user.getRuolo()==1)
                {
                    int idStanza=Integer.parseInt(request.getParameter("idStanza"));
                    boolean animale=Boolean.parseBoolean(request.getParameter("Animale"));
                    boolean fumatore=Boolean.parseBoolean(request.getParameter("Fumatore"));
                    int lettiSingoli=Integer.parseInt(request.getParameter("LettiS"));
                    int lettiMatri=Integer.parseInt(request.getParameter("LettiM"));
                    double costoNotte=Double.parseDouble(request.getParameter("Costo"));
                    double sconto=Double.parseDouble(request.getParameter("Sconto"));
                    StanzaService service=new StanzaService();
                    service.updateStanza(idStanza,animale,fumatore,lettiSingoli,lettiMatri,costoNotte,sconto);
                    obj.put("Ris",1);
                    obj.put("Mess","Fatto");
                    response.getOutputStream().print(obj.toString());
                }
                else
                {
                    obj.put("Ris",0);
                    obj.put("Mess","Non hai i permessi");
                    response.getOutputStream().print(obj.toString());
                }
            }
            catch(NumberFormatException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Inserisci tutti i parametri correttamente");
                response.getOutputStream().print(obj.toString());
            }
            catch (UtenteNotFoundException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Utente non valido");
                response.getOutputStream().print(obj.toString());
            }
        }
        else
        {
            obj.put("Ris",0);
            obj.put("Mess","Inserisci tutti i parametri");
            response.getOutputStream().print(obj.toString());
        }
    }
}
