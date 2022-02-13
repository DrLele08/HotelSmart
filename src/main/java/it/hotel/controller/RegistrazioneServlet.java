package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.controller.services.UtenteService;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.EmailAlreadyExistingException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


@WebServlet(name = "Registrazione", value = "/Registrazione")
public class RegistrazioneServlet extends CheckServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(canOpen(Utility.CHECK_SIGNUP))
        {
            RequestDispatcher rd;
            HttpSession session = request.getSession(true);
            Utente u = (Utente) session.getAttribute(Utility.SESSION_USER);
            if(u == null)
                rd=request.getRequestDispatcher("/WEB-INF/views/Registrazione.jsp");
            else
                rd=request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
        else
        {
            response.sendRedirect("./ServiceNA.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String dataNascita = request.getParameter("textDataNascita");
        String nome = request.getParameter("textNome");
        String cognome = request.getParameter("textCognome");
        String codiceFiscale = request.getParameter("textCodiceFiscale");
        String indirizzoEmail = request.getParameter("textEmail");
        String password = request.getParameter("textPwd");
        JSONObject obj=new JSONObject();
        UtenteService service = new UtenteService();
        try
        {
            service.doRegistrazione(codiceFiscale,nome,cognome,indirizzoEmail,Utility.dataConverter(dataNascita),password);
            obj.put("Ris",1);
            obj.put("Mess","Registrazione effettuata con successo");
            response.getOutputStream().print(obj.toString());
        }
        catch (ParseException | SQLException e)
        {
            obj.put("Ris",0);
            obj.put("Mess","Si è verificato un errore, riprovare piu' tardi o contattare l'assistenza");
            response.getOutputStream().print(obj.toString());
        }
        catch (PasswordNotValidException e)
        {
            obj.put("Ris",0);
            obj.put("Mess","La password non e' nel formato corretto");
            response.getOutputStream().print(obj.toString());
        }
        catch (EmailAlreadyExistingException e)
        {
            obj.put("Ris",0);
            obj.put("Mess","Email gia' esistente");
            response.getOutputStream().print(obj.toString());
        }

    }
}
