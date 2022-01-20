<%@ page import="java.util.ArrayList" %>
<%@ page import="it.hotel.model.stanza.Stanza" %>
<%@ page import="it.hotel.model.utente.UtenteDAO" %>
<%@ page import="it.hotel.controller.services.UtenteService" %>
<%@ page import="it.hotel.model.utente.utenteExceptions.UtenteNotFoundException" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="ricerca"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>

    <style>

        input {
            width: 700px;
        }

        @media screen and (max-width: 1000px) {
            input {
                width: 500px;
            }
        }

        @media screen and (max-width: 650px) {
            input {
                width: 200px;
            }
        }

    </style>

</head>

<body style="background-color: #cdd7e2">

<%@include file="/WEB-INF/views/partials/header.jsp" %>

<%
    Integer num_persone = (Integer) request.getAttribute("num_persone");
    Utente user = (Utente) session.getAttribute(Utility.SESSION_USER);
%>

<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container">

        <h1 class="display-4">Compila i dettagli della tua prenotazione</h1><br>

        <%if (user != null) {%>

        <form action="#" method="post">

            <div class="form-group">
                <label for="nome1">Nome Cliente 1
                </label><br>
                <input type="text" name="nome1" id="nome1" value="<%=user.getNome()%>">
            </div>

            <div class="form-group">
                <label for="cognome1">Cognome Cliente 1
                </label><br>
                <input type="text" name="cognome1" id="cognome1" value="<%=user.getCognome()%>">
            </div>

            <div class="form-group">
                <label for="codicef1">Codice fiscale Cliente 1
                </label><br>
                <input type="text" name="codicef1" id="codicef1" value="<%=user.getCf()%>">
            </div>

            <div class="form-group">
                <label for="dataNascita1">Data di nascita Cliente 1
                </label><br>
                <input type="date" id="dataNascita1" name="dataNascita1" value="<%=user.getDataNascita()%>">
            </div>
            <br><br>

            <% for (int i = 1; i < num_persone; i++) { %>
            <div class="form-group">
                <label for="nome<%=i+1%>">Nome Cliente <%=i + 1%>
                </label><br>
                <input type="text" name="nome<%=i+1%>" id="nome<%=i+1%>">
            </div>

            <div class="form-group">
                <label for="cognome<%=i+1%>">Cognome Cliente <%=i + 1%>
                </label><br>
                <input type="text" name="cognome<%=i+1%>" id="cognome<%=i+1%>">
            </div>

            <div class="form-group">
                <label for="codicef<%=i+1%>">Codice fiscale Cliente <%=i + 1%>
                </label><br>
                <input type="text" name="codicef<%=i+1%>" id="codicef<%=i+1%>">
            </div>

            <div class="form-group">
                <label for="dataNascita<%=i+1%>">Data di nascita Cliente <%=i + 1%>
                </label><br>
                <input type="date" id="dataNascita<%=i+1%>" name="dataNascita<%=i+1%>">
            </div>
            <br><br>
            <% } %>
            <input type="hidden" name="num_persone" value="<%=num_persone%>">
            <input type="submit" class="btn btn-dark" value="Conferma prenotazione">
        </form>

        <%} else { %>

        <form action="#" method="post">

            <% for (int i = 0; i < num_persone; i++) { %>
            <div class="form-group">
                <label for="nome<%=i+1%>">Nome Cliente <%=i + 1%>
                </label><br>
                <input type="text" name="nome<%=i+1%>" id="nome<%=i+1%>">
            </div>

            <div class="form-group">
                <label for="cognome<%=i+1%>">Cognome Cliente <%=i + 1%>
                </label><br>
                <input type="text" name="cognome<%=i+1%>" id="cognome<%=i+1%>">
            </div>

            <div class="form-group">
                <label for="codicef<%=i+1%>">Codice fiscale Cliente <%=i + 1%>
                </label><br>
                <input type="text" name="codicef<%=i+1%>" id="codicef<%=i+1%>">
            </div>

            <div class="form-group">
                <label for="dataNascita<%=i+1%>">Data di nascita Cliente <%=i + 1%>
                </label><br>
                <input type="date" id="dataNascita<%=i+1%>" name="dataNascita<%=i+1%>">
            </div>
            <br><br>
            <% } %>
            <input type="hidden" name="num_persone" value="<%=num_persone%>">
            <input type="submit" class="btn btn-dark" value="Conferma prenotazione">
        </form>
        <%}%>
    </div>
</div>

<%@include file="partials/footer.jsp" %>
</body>
</html>
