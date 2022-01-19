package it.hotel.controller;

import it.hotel.Utility.Utility;
import it.hotel.model.utente.Utente;
import it.hotel.model.utente.utenteExceptions.EmailAlreadyExistingException;
import it.hotel.model.utente.utenteExceptions.PasswordNotValidException;
import jdk.nashorn.internal.objects.Global;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "Registrazione", value = "/Registrazione")
public class RegistrazioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd;
        HttpSession session = request.getSession(true);
        Utente u = (Utente) session.getAttribute("utente");
        if(u == null)
            rd=request.getRequestDispatcher("/WEB-INF/views/Registrazione.jsp");
        else
            rd=request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
        String dataNascitaString = request.getParameter("textDataNascita");


        String nome = request.getParameter("textNome");
        String cognome = request.getParameter("textCognome");
        String codiceFiscale = request.getParameter("textCodiceFiscale");
        String indirizzoEmail = request.getParameter("textEmail");
        String password = request.getParameter("textPwd");



        UtenteService service = new UtenteService();
        try {
            Date dataNascitaUtil = in.parse(dataNascitaString);
            java.sql.Date dataNascita = new java.sql.Date(dataNascitaUtil.getTime());
            service.doRegistrazione(codiceFiscale,nome,cognome,indirizzoEmail,dataNascita,password);
            JSONObject object=new JSONObject();
            object.put("status",true);
            response.getOutputStream().print(object.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (PasswordNotValidException e) {
            response.getOutputStream().print("{\"status\":false,\"data\":\"PWDPROBLEMA\"}");
        } catch (EmailAlreadyExistingException e) {
            response.getOutputStream().print("{\"status\":false,\"data\":\"EMAIL PROBLEMA\"}");
        }

    }
}
