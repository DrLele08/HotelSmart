<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 21/01/2022
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>


    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Gestione utenti"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="http://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script src="${pageContext.request.contextPath}/script/GestioneUtenti.js"></script>
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
                Gestione utenti
            </h4>
        </div>
        <table id="GestioneUtentiTable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Codice fiscale</th>
                <th>Data di nascita</th>
                <th>Email</th>
                <th>Ruolo</th>
                <th>Azione</th>

            </tr>
            </thead>
            <tbody>

            <tr>
                <td>ProvaNome</td>
                <td>ProvaCognome</td>
                <td>ProvaCodiceFiscale</td>
                <td>2015-08-20</td>
                <td>ProvaEmail</td>
                <td>Ruolo</td>
                <td><span data-toggle="tooltip" title="Modifica permessi"><a class="bi bi-x-square-fill" id="iconModificaPermessi" data-toggle="modal" data-utente-id="1" data-target="#modalModificaPermessi"></a></span></td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Codice fiscale</th>
                <th>Data di nascita</th>
                <th>Email</th>
                <th>Ruolo</th>
                <th>Azione</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
<div class="modal fade" id="modalModificaPermessi" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalModificaPermessi">Modifica permessi utente</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="select">Permessi</label>
                        <select class="form-control" id="select">
                            <option value="1">Admin</option>
                            <option value="2">Assistente</option>
                            <option selected value="3">Utente</option>
                        </select>
                    </div>
                    <input type="hidden" id="idUtenteInput" value="">
                </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="ModificaPermessi()">Conferma</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>