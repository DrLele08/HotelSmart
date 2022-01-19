<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 17/01/2022
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO" %>
<%@ page import="it.hotel.model.prenotazioneStanza.PrenotazioneStanza" %>
<%@ page import="it.hotel.model.utente.Utente" %>
<%@ page import="it.hotel.Utility.Utility" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <%
        Utente ul = (Utente) session.getAttribute(Utility.SESSION_USER);
    %>

    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Anagrafica"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="http://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script>
        var data = [
           <%
           List<PrenotazioneStanza> ps=(List<PrenotazioneStanza>) request.getAttribute("ListaPreno");
           for(PrenotazioneStanza p : ps){
           %>
            ["<%=p.getCommenti()%>",
              "<%=p.getDataInizio()%>",
              "<%=p.getDataFine()%>",
            <%=p.getPrezzoFinale()%>],
          <% }%>
        ]
        $(document).ready(function() {
            $('#StoricoPrenotazioniTable').DataTable( {
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
                },
                data: data
            } );
        } );
    </script>

</head>
<body>
<%@ include file="/WEB-INF/views/partials/header.jsp" %>
<div class="wrapper">
    <%@ include file="/WEB-INF/views/partials/AreaPrivataSidebar.jsp" %>
    <div id="content">
        <button type="button" id="sidebarCollapse" class="btn btn-dark d-md-none">
            <i class="fas fa-align-justify"></i>
        </button>
        <table id="StoricoPrenotazioniTable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Commenti</th>
                <th>Data inizio</th>
                <th>Data fine</th>
                <th>Prezzo finale</th>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <th>Commenti</th>
                <th>Data inizio</th>
                <th>Data fine</th>
                <th>Prezzo finale</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>
