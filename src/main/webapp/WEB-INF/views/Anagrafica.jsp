<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 17/01/2022
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>


    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Anagrafica"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script src="${pageContext.request.contextPath}/script/ModificaAnagrafica.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/partials/header.jsp" %>
<div class="wrapper">
    <%@ include file="/WEB-INF/views/partials/AreaPrivataSidebar.jsp" %>
    <div id="content">
        <button type="button" id="sidebarCollapse" class="btn btn-dark d-md-none">
            <i class="fas fa-align-justify"></i>
        </button>
            <div class="profile-head">
                <h4>
                    Dettagli anagrafica utente
                </h4>
            </div>
        <form id="formAnagr" class = "needs-validation">
            <div class="row">
                <div class="col-md-4 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="Nome">Nome</label>
                        <input type="text" id="Nome" value="<%=ut.getNome()%>" class="form-control form-control-lg" pattern="[A-z  À-ù ‘-]{2,30}$" required/>
                        <div class="invalid-feedback">
                            Inserisci un nome valido!
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="Cognome">Cognome</label>
                        <input type="text"id="Cognome" value="<%=ut.getCognome()%>" class="form-control form-control-lg" pattern="[A-z  À-ù ‘-]{2,30}$" required/>
                        <div class="invalid-feedback">
                            Inserisci un cognome valido!
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="CodiceFiscale">Codice fiscale</label>
                        <input type="text" id="CodiceFiscale" value="<%=ut.getCf()%>" class="form-control form-control-lg" pattern="[A-z 0-9]{16}$" required/>
                        <div class="invalid-feedback">
                            Inserisci un codice fiscale valido!
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4 d-flex align-items-center">
                    <div class="form-outline w-100">
                        <label for="DataNascita" class="form-label">Data di nascita</label>
                        <input id="DataNascita"  type="date" class="form-control form-control-lg" value="<%=ut.getDataNascita()%>" required/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 mb-4 pb-2">
                    <div class="form-outline">
                        <label class="form-label" for="IndirizzoEmail">Indirizzo Email</label>
                        <input type="text" id="IndirizzoEmail" value="<%=ut.getEmail()%>" class="form-control form-control-lg" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required />
                        <div class="invalid-feedback">
                            Inserisci una Email valida!
                        </div>
                    </div>
                </div>
                <input type="hidden" id="idUtenteAna" value="<%=ut.getIdUtente()%>">
                <input type="hidden" id="tokenUtenteAna" value="<%=ut.getTokenAuth()%>">
                <div class="col-md-4 mb-4 pb-2">
                    <div class="mt-4 pt-2">
                        <Button onclick="ModificaAnagrafica()" id="ButtonSub" class="btn btn-primary btn-lg" type="button">Modifica</button>
                    </div>
                </div>
            </div>
         </form>
        <form id="formPass" class = "needs-validation">
        <div class="profile-head">
            <h4>
                Modifica Password
            </h4>
        </div>

            <div class="row">
                <div class="col-md-4 mb-4 pb-2">
                    <div class="form-outline">
                        <label class="form-label" for="VecchiaPassword">Vecchia Password</label>
                        <input type="password" id="VecchiaPassword" class="form-control form-control-lg" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}:;',?+\*~$^=<>]).{8,20}$" required  />
                        <div class="invalid-feedback">
                            La password deve contenere almeno una lettera maiuscola,una minuscola, un numero e un carattere speciale e deve essere da 8 a 20 caratteri
                        </div>
                    </div>
                </div>
                <input type="hidden" id="idUtentePsw" value="<%=ut.getIdUtente()%>">
                <input type="hidden" id="tokenUtente" value="<%=ut.getTokenAuth()%>">
            </div>
            <div class="row">
                <div class="col-md-4 mb-4 pb-2">
                    <div class="form-outline">
                        <label class="form-label" for="NuovaPassword">Nuova Password</label>
                        <input type="password" id="NuovaPassword" class="form-control form-control-lg" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}:;',?+\*~$^=<>]).{8,20}$" required  />
                        <div class="invalid-feedback">
                            La password deve contenere almeno una lettera maiuscola,una minuscola, un numero e un carattere speciale e deve essere da 8 a 20 caratteri
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4 pb-2">
                    <div class="form-outline">
                        <label class="form-label" for="RipetiPassword">Ripeti Password</label>
                        <input type="password" id="RipetiPassword" class="form-control form-control-lg"/>
                    </div>
                </div>
            </div>
            <div class="row">
            <div class="col-md-4 mb-4 pb-2">
                <div class="mt-4 pt-2">
                    <Button onclick="ModificaPassword()" id="ButtonSubPass" class="btn btn-primary btn-lg" type="button">Modifica</button>
                </div>
            </div>
        </div>
        </form>
    </div>
</div>
</body>
</html>
