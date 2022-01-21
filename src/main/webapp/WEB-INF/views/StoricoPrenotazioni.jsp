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
        <jsp:param name="title" value="Gestione prenotazioni"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="http://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script>
        $(document).ready(function() {
            $('#StoricoPrenotazioniTable').DataTable( {
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
                },
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
        <div class="profile-head">
            <h4>
                Gestione prenotazioni
            </h4>
        </div>
        <table id="StoricoPrenotazioniTable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Numero stanza</th>
                <th>Data inizio</th>
                <th>Data fine</th>
                <th>Prezzo finale</th>
                <th>Stato</th>
            </tr>
            </thead>
            <tbody>
                <%
                    List<PrenotazioneStanza> ps=(List<PrenotazioneStanza>) request.getAttribute("ListaPreno");
                    for(PrenotazioneStanza p : ps){
                %>
                    <tr>
                        <td><%=p.getKsStanza()%></td>
                        <td><%=p.convertDateToView(p.getDataInizio())%></td>
                        <td><%=p.convertDateToView(p.getDataFine())%></td>
                        <td><%=p.getPrezzoFinale()%></td>
                        <td>
                            <%
                                switch(p.getKsStato()){
                                case 1:
                                  %><a class="bi bi-credit-card" href="${pageContext.request.contextPath}/Pagamenti"></a>
                                  <a class="bi bi-x-square-fill" href="${pageContext.request.contextPath}/AnnulareOrdine"></a>
                             <%   break;
                            case 2:
                             %><a class="bi bi-x-square-fill" href="${pageContext.request.contextPath}/Rimborso"></a>
                            <%   break;
                                case 3:
                            %><a class="bi bi-bag-plus" href="${pageContext.request.contextPath}/PrenotaServizi"></a>
                            <%   break;
                                case 4: case 5: case 6:
                            %><a class="bi bi-archive"  ></a>
                            <%   break;
                            }%>
                        </td>
                    </tr>
                <% }%>
            </tbody>
            <tfoot>
            <tr>
                <th>Numero stanza</th>
                <th>Data inizio</th>
                <th>Data fine</th>
                <th>Prezzo finale</th>
                <th>Stato</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>

</body>
</html>
