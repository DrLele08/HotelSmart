<%@ page import="java.util.ArrayList" %>
<%@ page import="it.hotel.model.stanza.Stanza" %>
<%@ page import="java.util.Date" %>
<%@ page import="it.hotel.model.servizio.Servizio" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Risultati"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <script src="${pageContext.request.contextPath}/script/HeaderCheck.js"></script>

    <style>

        .card-text {
            font-size: medium;
        }

        .card {
            flex-direction: row;
            background-color: #cdd7e2;
        }

        .card img {
            width: 30%;
        }

        @media only screen and (max-width: 768px) {
            .card {
                flex-direction: column;
            }

            .card-body {
                padding: 0.5em 1.2em;
            }

            .card-body .card-text {
                margin: 0;
            }

            .card img {
                width: 100%;
            }
        }
    </style>
</head>

<body onload="headerCheck()" style="background-color: #cdd7e2">

<%@include file="/WEB-INF/views/partials/header.jsp" %>

<%
    ArrayList<Servizio> servizi = (ArrayList<Servizio>) request.getAttribute("servizi");
    boolean has_active_reservation = (boolean) request.getAttribute("has_active_reservation");
    Utente user = (Utente) session.getAttribute(it.hotel.Utility.Utilita.SESSION_USER);
%>

<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container">

       <%if(servizi != null && servizi.size() > 0){%>
        <h1 class="display-4">I nostri servizi extra</h1><br>
           <%if(user == null){%>
               <%for(Servizio s: servizi){%>
                    <div class="card">
                        <img src="${pageContext.request.contextPath}/images/Servizi/default.jpg" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title"><%=s.getNome()%></h5>
                            <p class="card-text"><%=s.getDescrizione()%></p>
                            <ul>
                                <li class="card-text">Prezzo: <%=s.getCosto()%></li>
                                <li class="card-text">Posti disponibili: <%=s.getLimitePosti()%></li>
                            </ul>
                            <p class="card-text">Per poter prenotare un servizio bisogna essere registrati alla piattaforma<br>
                            e avere almeno una prenotazione in corso</p>
                            <form action="${pageContext.request.contextPath}/Registrazione">
                                <input type="hidden" name="servizioId" value="<%=s.getIdServizio()%>">
                                <input class="btn btn-dark" type="submit" value="Registrati">
                            </form>
                        </div>
                    </div><br>
               <%}%>
           <%} else if(!has_active_reservation){%>
                <%for(Servizio s: servizi){%>
                    <div class="card">
                        <img src="${pageContext.request.contextPath}/images/Servizi/default.jpg" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title"><%=s.getNome()%></h5>
                            <p class="card-text"><%=s.getDescrizione()%></p>
                            <ul>
                                <li class="card-text">Prezzo: <%=s.getCosto()%></li>
                                <li class="card-text">Posti disponibili: <%=s.getLimitePosti()%></li>
                            </ul>
                            <p class="card-text">Per poter prenotare un servizio bisogna essere registrati alla piattaforma<br>
                            e avere almeno una prenotazione in corso</p>
                            <form action="${pageContext.request.contextPath}/ricerca/gosearch">
                                <input type="hidden" name="servizioId" value="<%=s.getIdServizio()%>">
                                <input class="btn btn-dark" type="submit" value="Prenota una camera">
                            </form>
                        </div>
                    </div><br>
                <%}%>
           <%}else{%>
                <%for(Servizio s: servizi){%>
                    <div class="card">
                        <img src="${pageContext.request.contextPath}/images/Servizi/default.jpg" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title"><%=s.getNome()%></h5>
                            <p class="card-text"><%=s.getDescrizione()%></p>
                            <ul>
                                <li class="card-text">Prezzo: <%=s.getCosto()%></li>
                                <li class="card-text">Posti disponibili: <%=s.getLimitePosti()%></li>
                            </ul>
                            <form action="${pageContext.request.contextPath}/servizi/servizioDetailForm" method="post">
                                <input type="hidden" name="servizioId" value="<%=s.getIdServizio()%>">
                                <input class="btn btn-dark" type="submit" value="Prenota">
                            </form>
                        </div>
                    </div><br>
                <%}%>
           <%}%>
       <%}else{%>
            <h2 class="display-4">Siamo spiacenti! non sono stati trovati servizi al momento.</h2><br>
            <form action="${pageContext.request.contextPath}">
                <input type="submit" class="btn btn-dark" value="Torna alla home">
            </form>
       <%}%>
    </div>
</div>

<%@include file="partials/footer.jsp" %>
</body>
</html>