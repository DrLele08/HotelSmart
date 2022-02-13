package it.hotel.controller.api;

import it.hotel.controller.CheckServlet;
import it.hotel.controller.services.UtenteService;
import it.hotel.controller.exception.PermissionDeniedException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ModificaPwd", value = "/api/ModificaPwd")
public class ModificaPwd extends CheckServlet
{
    public UtenteService getUtenteService()
    {
        return new UtenteService();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JSONObject obj=new JSONObject();
        if(contieneParametro(request,"textIdUtente") && contieneParametro(request,"textToken") && contieneParametro(request,"textOldPwd")
                                && contieneParametro(request,"textNewPwd") && contieneParametro(request,"textRepeatPwd"))
        {
            try
            {
                int idUtente=Integer.parseInt(request.getParameter("textIdUtente"));
                String token=request.getParameter("textToken");
                String oldPwd=request.getParameter("textOldPwd");
                String newPwd=request.getParameter("textNewPwd");
                String repeatPwd=request.getParameter("textRepeatPwd");
                if(newPwd.equals(repeatPwd))
                {
                    UtenteService service=getUtenteService();
                    service.editPassword(idUtente,token,oldPwd,newPwd);
                    obj.put("Ris",1);
                    obj.put("Mess","Fatto");
                }
                else
                {
                    obj.put("Ris",0);
                    obj.put("Mess","Le password non coincidono");
                }
            }
            catch(NumberFormatException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Inserisci tutti i parametri correttamente");
            }
            catch (PasswordNotValidException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Password non valida");
            }
            catch (UtenteNotFoundException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Utente non trovato");
            }
            catch (PermissionDeniedException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Permessi non validi");
            }
            catch (SQLException e)
            {
                obj.put("Ris",0);
                obj.put("Mess","Errore DB");
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
