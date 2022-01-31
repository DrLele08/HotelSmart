<%@ page import="it.hotel.model.servizio.Servizio" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Dettagli"/>
        <jsp:param name="styles" value="header.css"/>
        <jsp:param name="styles" value="Registrazione.css"/>
    </jsp:include>

    <style>

        #totalPrice {
            display: none;
        }

        .custom-container {
            text-align: center;
        }

        .custom-elem {
            display: inline-block;
        }
    </style>

    <script>
        function checkForm(){

            let numero_ospiti = document.getElementById("numero_ospiti").value;

            if(isNaN(numero_ospiti) || numero_ospiti <= 0){
                document.getElementById("errore_ospiti").style.display = "block";
                return false;
            }

            return true;

        }

        function prezzoTotale(){

            let numero_ospiti = document.getElementById("numero_ospiti").value;

            if(!isNaN(numero_ospiti) && numero_ospiti > 0){

                let temp = document.getElementById("totalPrice").innerHTML;

                document.getElementById("totalPrice").innerHTML = temp + numero_ospiti;
                document.getElementById("totalPrice").style.display = "block";

            } else{
                document.getElementById("totalPrice").style.display = "none";
                document.getElementById("errore_ospiti").style.display = "block";
            }
        }
    </script>
</head>
<body onload="headerCheck()" style="background-color: #cdd7e2">

<%
    Servizio servizio = (Servizio) request.getAttribute("servizio");
%>

<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container custom-container">
        <h1 class="display-4">Conferma la tua prenotazione</h1><br>
        <div class="card mb-3 custom-elem">
            <img class="card-img-top" src="${pageContext.request.contextPath}/images/Servizi/default.jpg" alt="Room image">
            <div class="card-body" style="background-color: #cdd7e2; text-align: left">
                <ul>
                    <h5 class="card-title">Dettagli</h5>
                    <li class="card-text">Prezzo (per persona): <%=servizio.getCosto()%></li>
                    <li class="card-text">Posti disponibili rimanenti: <%=servizio.getLimitePosti()%></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container">
        <h1 class="display-4">Dettagli prenotazione</h1><br>
        <form action="${pageContext.request.contextPath}/servizi/prenotaServizio" method="post">

            <div class="form-group">
                <label for="numero_ospiti">Numero persone</label><br>
                <input onchange="prezzoTotale()" type="number" name="numero_ospiti" id="numero_ospiti" min="1">
                <div class="invalid-feedback" id="errore_ospiti">
                    Numero ospiti non valido
                </div>
            </div>

            <p id="totalPrice">Prezzo totale:  </p>

            <input type="submit" onclick="return checkForm()" class="btn btn-dark" value="Vai al pagamento">
        </form>
    </div>
</div>


</body>
</html>
