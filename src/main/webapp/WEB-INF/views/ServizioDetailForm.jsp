<%@ page import="it.hotel.model.servizio.Servizio" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Dettagli"/>
        <jsp:param name="styles" value="header.css"/>
        <jsp:param name="styles" value="Registrazione.css"/>
    </jsp:include>
    <script src="${pageContext.request.contextPath}/script/HeaderCheck.js"></script>
    <script src="${pageContext.request.contextPath}/script/serviziDetails.js"></script>

    <style>

        #totalPrice {
            display: none;
        }

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

        .custom-container {
            text-align: center;
        }

        .custom-elem {
            display: inline-block;
        }
    </style>
</head>

<body onload="headerCheck()" style="background-color: #cdd7e2">

<%@include file="/WEB-INF/views/partials/header.jsp" %>

<%
    Servizio servizio = (Servizio) request.getAttribute("servizio");
%>

<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container">
        <h1 class="display-4">Conferma la tua prenotazione</h1><br>
        <div class="card">
            <img class="card-img-top" src="${pageContext.request.contextPath}/images/Servizi/default.jpg" alt="Room image">
            <div class="card-body" style="background-color: #cdd7e2; text-align: left">
                <ul>
                    <h5 class="card-title">Dettagli</h5>
                    <li class="card-text"><%=servizio.getDescrizione()%></li><br>
                    <li class="card-text">Prezzo (per persona): <%=servizio.getCosto()%></li>
                    <li class="card-text">Posti disponibili rimanenti: <%=servizio.getLimitePosti()%></li>
                </ul>
            </div>
        </div><br><br>

        <form action="${pageContext.request.contextPath}/servizi/prenotaServizio" method="post">

            <div class="form-group">
                <label for="numero_ospiti">Numero persone</label><br>
                <input class="form-control form-control-lg" onchange="prezzoTotale()" type="number" name="numero_ospiti" id="numero_ospiti" min="1">
                <div class="invalid-feedback" id="errore_ospiti">
                    Numero ospiti non valido
                </div>
            </div>

            <input type="hidden" id="prezzo" value="<%=servizio.getCosto()%>">

            <p id="totalPrice"></p>

            <div class="form-group">
                <label for="data">Data</label><br>
                <input class="form-control form-control-lg" type="date" name="data" id="data">
                <div class="invalid-feedback" id="errore_data">
                    Data non valida
                </div>
            </div><br>

            <input type="hidden" name="servizioId" value="<%=servizio.getIdServizio()%>">
            <input type="submit" onclick="return checkForm()" class="btn btn-dark" value="Conferma">
        </form>

    </div>
</div>

<%@include file="partials/footer.jsp" %>

</body>
</html>
