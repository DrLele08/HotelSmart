package it.hotel.controller.api;

import it.hotel.controller.services.UtenteService;
import it.hotel.controller.exception.PermissionDeniedException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import it.hotel.model.utente.utenteExceptions.UtenteNotFoundException;
import org.json.JSONObject;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ModifcaPwd", value = "/api/ModifcaPwd")
public class ModifcaPwd extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        int idUtente=Integer.parseInt(request.getParameter("textIdUtente"));
        String token=request.getParameter("textToken");
        String oldPwd=request.getParameter("textOldPwd");
        String newPwd=request.getParameter("textNewPwd");
        String repeatPwd=request.getParameter("textRepeatPwd");
        JSONObject obj=new JSONObject();
        if(newPwd.equals(repeatPwd))
        {
            UtenteService service=new UtenteService();
            try
            {
                service.editPassword(idUtente,token,oldPwd,newPwd);
                obj.put("Ris",1);
                obj.put("Mess","Fatto");
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
        }
        else
        {
            obj.put("Ris",0);
            obj.put("Mess","Le password non coincidono");
        }
        response.getOutputStream().print(obj.toString());
    }
}
