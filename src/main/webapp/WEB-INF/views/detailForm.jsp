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
        <jsp:param name="title" value="Dettagli"/>
        <jsp:param name="styles" value="header.css"/>
        <jsp:param name="styles" value="Registrazione.css"/>
    </jsp:include>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/detailForm.js"></script>
    <script src="${pageContext.request.contextPath}/script/HeaderCheck.js"></script>

    <style>

        .custom-container {
            text-align: center;
        }

        .custom-elem {
            display: inline-block;
        }

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

<body onload="headerCheck()" style="background-color: #cdd7e2">

<%@include file="/WEB-INF/views/partials/header.jsp" %>

<%
    Integer num_persone = (Integer) request.getAttribute("num_persone");
    String dataArrivo = (String) request.getAttribute("dataArrivoString");
    String dataPartenza = (String) request.getAttribute("dataPartenzaString");

    Utente user = (Utente) session.getAttribute(Utility.SESSION_USER);
    Stanza selected_stanza = (Stanza) request.getAttribute("selected_stanza");
    Integer id_stanza = selected_stanza.getIdStanza();

    String fumatore = "Non permessso";
    String animale_domestico = "Non permesso";
    if(selected_stanza.getFumatore()) fumatore = "Permesso";
    if(selected_stanza.getAnimaleDomestico()) animale_domestico = "Permesso";
%>

<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container custom-container">
        <h1 class="display-4">Conferma la tua prenotazione</h1><br>
        <div class="card mb-3 custom-elem">
            <img class="card-img-top" src="${pageContext.request.contextPath}/images/hotelroom0.jpg" alt="Room image">
            <div class="card-body" style="background-color: #cdd7e2; text-align: left">
                <ul>
                    <h5 class="card-title">Riepilogo dettagli stanza selezionata</h5>
                    <li class="card-text">Letti matrimoniali: <%=selected_stanza.getLettiMatrimoniali()%></li>
                    <li class="card-text">Letti singoli: <%=selected_stanza.getLettiSingoli()%></li>
                    <li class="card-text">Fumatore: <%=fumatore%></li>
                    <li class="card-text">Animale domestico: <%=animale_domestico%></li>
                    <li class="card-text">Costo per notte: <%=selected_stanza.getCostoNotte()%></li>
                    <li class="card-text">Sconto applicato (per notte): <%=selected_stanza.getSconto()%>€</li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container">

        <h1 class="display-4">Compila i dettagli della tua prenotazione</h1><br>

        <%if (user != null) {%>

        <form id="form" action="${pageContext.request.contextPath}/Prenotazione" method="post" class="needs-validation">

            <p>Dati personali del cliente a cui sarà assegnata la prenotazione</p><br>

            <div class="form-group">
                <label for="nome1">Nome Cliente 1
                </label><br>
                <input type="text" class="form-control form-control-lg" name="nome1" id="nome1"
                       pattern="[A-z  À-ù ‘-]{2,30}$" value="<%=user.getNome()%>" required readonly>
                <div class="invalid-feedback">
                    Inserisci un nome valido!
                </div>
            </div>

            <div class="form-group">
                <label for="cognome1">Cognome Cliente 1
                </label><br>
                <input type="text" class="form-control form-control-lg" name="cognome1" id="cognome1"
                       pattern="[A-z  À-ù ‘-]{2,30}$" value="<%=user.getCognome()%>" required readonly>
                <div class="invalid-feedback">
                    Inserisci un cognome valido!
                </div>
            </div>

            <div class="form-group">
                <label for="codicef1">Codice fiscale Cliente 1
                </label><br>
                <input type="text" class="form-control form-control-lg" name="codicef1" id="codicef1"
                       pattern="[A-z 0-9]{16}$" value="<%=user.getCf()%>" required readonly>
                <div class="invalid-feedback">
                    Inserisci un codice fiscale valido!
                </div>
            </div>

            <div class="form-group">
                <label for="dataNascita1">Data di nascita Cliente 1
                </label><br>
                <input type="date" class="form-control form-control-lg" id="dataNascita1" name="dataNascita1"
                       value="<%=user.getDataNascita()%>" required readonly>
            </div>
            <br><br>

            <p>Dati degli altri ospiti</p><br>

            <% for (int i = 1; i < num_persone; i++) { %>
            <div class="form-group">
                <label for="nome<%=i+1%>">Nome Cliente <%=i + 1%>
                </label><br>
                <input type="text" class="form-control form-control-lg" name="nome<%=i+1%>"
                       pattern="[A-z  À-ù ‘-]{2,30}$" id="nome<%=i+1%>" required>
                <div class="invalid-feedback">
                    Inserisci un nome valido!
                </div>
            </div>

            <div class="form-group">
                <label for="cognome<%=i+1%>">Cognome Cliente <%=i + 1%>
                </label><br>
                <input type="text" class="form-control form-control-lg" name="cognome<%=i+1%>"
                       pattern="[A-z  À-ù ‘-]{2,30}$" id="cognome<%=i+1%>" required>
                <div class="invalid-feedback">
                    Inserisci un cognome valido!
                </div>
            </div>

            <div class="form-group">
                <label for="codicef<%=i+1%>">Codice fiscale Cliente <%=i + 1%>
                </label><br>
                <input type="text" class="form-control form-control-lg" name="codicef<%=i+1%>" pattern="[A-z 0-9]{16}$"
                       id="codicef<%=i+1%>" required>
                <div class="invalid-feedback">
                    Inserisci un codice fiscale valido!
                </div>
            </div>

            <div class="form-group">
                <label for="dataNascita<%=i+1%>">Data di nascita Cliente <%=i + 1%>
                </label><br>
                <input type="date" class="form-control form-control-lg" id="dataNascita<%=i+1%>"
                       name="dataNascita<%=i+1%>" required>
            </div>
            <br><br>
            <% } %>
            <input type="hidden" class="num_persone" name="num_persone" value="<%=num_persone%>">
            <input type="hidden" name="id_stanza" value="<%=id_stanza%>">
            <input type="hidden" name="dataArrivo" value="<%=dataArrivo%>">
            <input type="hidden" name="dataPartenza" value="<%=dataPartenza%>">
            <Button onclick="return ValidateFormLogged()" id="ButtonSub" class="btn btn-dark" type="submit">Conferma</button>
        </form>

        <%} else { %>

        <p>I dati extra inseriti serviranno per registrarti alla piattaforma automaticamente</p><br>

        <form id="form" action="${pageContext.request.contextPath}/Prenotazione" method="post">

            <div class="form-group">
                <label for="nome1_notLogged">Nome Cliente 1
                </label><br>
                <input type="text" class="form-control form-control-lg" pattern="[A-z  À-ù ‘-]{2,30}$" name="nome1"
                       id="nome1_notLogged" required>
                <div class="invalid-feedback">
                    Inserisci un nome valido!
                </div>
            </div>

            <div class="form-group">
                <label for="cognome1_notLogged">Cognome Cliente 1
                </label><br>
                <input type="text" class="form-control form-control-lg" pattern="[A-z  À-ù ‘-]{2,30}$" name="cognome1"
                       id="cognome1_notLogged"
                       required>
                <div class="invalid-feedback">
                    Inserisci un cognome valido!
                </div>
            </div>

            <div class="form-group">
                <label for="codicef1_notLogged">Codice fiscale Cliente 1
                </label><br>
                <input type="text" class="form-control form-control-lg" pattern="[A-z 0-9]{16}$" name="codicef1"
                       id="codicef1_notLogged"
                       required>
                <div class="invalid-feedback">
                    Inserisci un codice fiscale valido!
                </div>
            </div>

            <div class="form-group">
                <label for="dataNascita1_notLogged">Data di nascita Cliente 1
                </label><br>
                <input type="date" class="form-control form-control-lg" id="dataNascita1_notLogged" name="dataNascita1"
                       required>
            </div><br>

            <p>Dati extra per cliente che sarà registrato alla piattaforma e a cui sarà assegnata la prenotazione</p>

            <div class="form-group">
                <label class="form-label" for="IndirizzoEmail">Indirizzo Email</label>
                <input type="text" name="emailcliente" id="IndirizzoEmail" class="form-control form-control-lg"
                       pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required/>
                <div class="invalid-feedback">
                    Inserisci una Email valida!
                </div>
            </div>

            <div class="form-group">
                <label class="form-label" for="Password">Password</label>
                <input type="password" name="pwdcliente" id="Password" class="form-control form-control-lg"
                       pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}:;',?+\*~$^=<>]).{8,20}$" required/>
                <div class="invalid-feedback">
                    La password deve contenere almeno una lettera maiuscola,una minuscola, un numero e un carattere
                    speciale e deve essere da 8 a 20 caratteri
                </div>
            </div>
            <br><br>

            <p>Dati degli altri ospiti</p><br>

            <% for (int i = 1; i < num_persone; i++) { %>
            <div class="form-group">
                <label for="nome<%=i+1%>">Nome Cliente <%=i + 1%>
                </label><br>
                <input type="text" class="form-control form-control-lg" name="nome<%=i+1%>"
                       pattern="[A-z  À-ù ‘-]{2,30}$" id="nome<%=i+1%>" required>
                <div class="invalid-feedback">
                    Inserisci un nome valido!
                </div>
            </div>

            <div class="form-group">
                <label for="cognome<%=i+1%>">Cognome Cliente <%=i + 1%>
                </label><br>
                <input type="text" class="form-control form-control-lg" name="cognome<%=i+1%>"
                       pattern="[A-z  À-ù ‘-]{2,30}$" id="cognome<%=i+1%>" required>
                <div class="invalid-feedback">
                    Inserisci un cognome valido!
                </div>
            </div>

            <div class="form-group">
                <label for="codicef<%=i+1%>">Codice fiscale Cliente <%=i + 1%>
                </label><br>
                <input type="text" class="form-control form-control-lg" name="codicef<%=i+1%>" pattern="[A-z 0-9]{16}$"
                       id="codicef<%=i+1%>" required>
                <div class="invalid-feedback">
                    Inserisci un codice fiscale valido!
                </div>
            </div>

            <div class="form-group">
                <label for="dataNascita<%=i+1%>">Data di nascita Cliente <%=i + 1%>
                </label><br>
                <input type="date" class="form-control form-control-lg" id="dataNascita<%=i+1%>"
                       name="dataNascita<%=i+1%>" required>
            </div>
            <br><br>
            <% } %>
            <input type="hidden" class="num_persone" name="num_persone" value="<%=num_persone%>">
            <input type="hidden" name="id_stanza" value="<%=id_stanza%>">
            <input type="hidden" name="dataArrivo" value="<%=dataArrivo%>">
            <input type="hidden" name="dataPartenza" value="<%=dataPartenza%>">
            <Button onclick="return ValidateForm()" id="ButtonSub" class="btn btn-dark" type="submit">Conferma</button>
        </form>
        <%}%>
    </div>
</div>

<%@include file="partials/footer.jsp" %>
</body>
</html>
