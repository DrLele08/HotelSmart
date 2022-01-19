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


    <jsp:include page="views/head.jsp">
        <jsp:param name="title" value="Anagrafica"/>
    </jsp:include>
    <link rel="stylesheet" href="../css/Anagrafica.css">

    <script src="../script/AreaPrivataSidebar.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<div class="wrapper">
    <%@ include file="/WEB-INF/views/AreaPrivataSidebar.jsp" %>
    <div id="content">
        <button type="button" id="sidebarCollapse" class="btn btn-dark d-md-none">
            <i class="fas fa-align-justify"></i>
        </button>
            <div class="profile-head">
                <h5>
                    Dettagli anagrafica utente
                </h5>
            </div>
            <div class="row align-items-start">
                <div class="col-4">
                    <div class="row">
                        <div class="col-md-6">
                            <label>Nome</label>
                        </div>
                        <div class="col-md-6">
                            <p><%=ut.getNome()%></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <label>Cognome</label>
                        </div>
                        <div class="col-md-6">
                            <p><%=ut.getCognome()%></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <label>Data di nascita</label>
                        </div>
                        <div class="col-md-6">
                            <p><%=ut.getDataNascita()%></p>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="row">
                        <div class="col-md-6">
                            <label>Codice fiscale</label>
                        </div>
                        <div class="col-md-6">
                            <p><%=ut.getCf()%></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <label>Email</label>
                        </div>
                        <div class="col-md-6">
                            <p><%=ut.getEmail()%></p>
                        </div>
                    </div>
                </div>
            </div>
         </div>
    </div>
</div>
</body>
</html>
