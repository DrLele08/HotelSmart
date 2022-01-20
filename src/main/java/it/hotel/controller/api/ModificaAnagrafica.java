package it.hotel.controller.api;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ModificaAnagrafica", value = "/api/ModificaAnagrafica")
public class ModificaAnagrafica extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String textNome=request.getParameter("textNome");
        String textCognome=request.getParameter("textCognome");
        String textCodiceFiscale=request.getParameter("textCodiceFiscale");
        String textDataNascita=request.getParameter("textDataNascita");
        String textEmail=request.getParameter("textEmail");
        //ToDo Service

    }
}
