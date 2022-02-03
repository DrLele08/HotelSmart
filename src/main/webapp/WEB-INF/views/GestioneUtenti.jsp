<%--
  Autore: Alessandro d'Esposito
--%>

<%@ page import="it.hotel.model.utente.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="it.hotel.controller.services.UtenteService" %>
<%@ page import="it.hotel.controller.services.RuoloService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Gestione utenti"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="http://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/rowreorder/1.2.8/js/dataTables.rowReorder.min.js"></script>
    <script src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.min.js"></script>
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

            <%
                List<Utente> utenti=(List<Utente>) request.getAttribute("Utenti");
                RuoloService serviceRuolo = new RuoloService();
                for(Utente s : utenti){
            %>
            <tr>
                <td><%=s.getNome()%></td>
                <td><%=s.getCognome()%></td>
                <td><%=s.getCf()%></td>
                <td><%=Utility.convertDateToView(s.getDataNascita())%></td>
                <td><%=s.getEmail()%></td>
                <td><%=serviceRuolo.getById(s.getRuolo())%></td>
                <td>
                    <%if(s.getIdUtente()!=ut.getIdUtente()){%><span data-toggle="tooltip" title="Modifica permessi"><a class="fas fa-edit icon-hover" id="iconModificaPermessi" onclick='iconModificaPermessi(<%=s.getIdUtente()%>,"<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>,<%=s.getRuolo()%>)'  data-toggle="modal" data-target="#modalModificaPermessi"></a></span> <%}%>
                </td>

            </tr>
            <%}%>

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

<!-- Modal modifica permessi -->
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
                    <label for="selectPermessi">Permessi</label>
                    <select class="form-control" id="selectPermessi">
                        <option value="1">Admin</option>
                        <option value="2">Assistente</option>
                        <option value="3">Utente</option>
                    </select>
                </div>
                <input type="hidden" id="idUtenteLoggato" value="">
                <input type="hidden" id="tokenUtenteLoggato" value="">
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