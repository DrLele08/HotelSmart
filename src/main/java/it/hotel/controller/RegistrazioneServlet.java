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
public class RegistrazioneServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(Utility.isActive(Utility.CHECK_SIGNUP))
        {
            RequestDispatcher rd;
            HttpSession session = request.getSession(true);
            Utente u = (Utente) session.getAttribute("utente");
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
        JSONObject object=new JSONObject();
        UtenteService service = new UtenteService();
        try
        {
            service.doRegistrazione(codiceFiscale,nome,cognome,indirizzoEmail,Utility.dataConverter(dataNascita),password);
            object.put("status",true);
            response.getOutputStream().print(object.toString());
        }
        catch (ParseException e)
        {
            object.put("status",false);
            object.put("data","BOPROBLEMA");
            response.getOutputStream().print(object.toString());
        }
        catch (PasswordNotValidException e)
        {
            object.put("status",false);
            object.put("data","PWDPROBLEMA");
            response.getOutputStream().print(object.toString());
        }
        catch (EmailAlreadyExistingException | SQLException e)
        {
            object.put("status",false);
            object.put("data","EMAIL PROBLEMA");
            response.getOutputStream().print(object.toString());
        }

    }
}
