package it.hotel.controller;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>Home Page</h1>
 * Servlet che gestisce le pagine 'About Us' e 'Contattaci'
 * @author Pierpaolo Cammardella
 * @version 1.6
 * @since 2022-01-25
 */
@WebServlet(name = "Home", value = "/home/*")
public class HomeServlet extends HttpServlet
{
    /**
     * Richiesta che riceve il nome della pagina e la visualizza
     * @param request Richiesta del cliente
     * @param response Risposta visualizzare la pagina richiesta
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String path = (request.getPathInfo() != null) ? request.getPathInfo() : "/";
        switch (path)
        {

            case "/aboutus":
            {
                String active_link = "aboutus";
                request.setAttribute("active",active_link);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/AboutUs.jsp");
                dispatcher.forward(request,response);
                break;
            }

            case "/contattaci":
            {
                String active_link = "contattaci";
                request.setAttribute("active",active_link);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/Contattaci.jsp");
                dispatcher.forward(request,response);
                break;
            }
            default:
            {
                response.sendRedirect(request.getContextPath());
                break;
            }
        }
    }
}