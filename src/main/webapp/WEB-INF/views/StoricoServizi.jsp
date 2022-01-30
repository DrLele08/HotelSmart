<%@ page import="it.hotel.model.servizio.Servizio" %>
<%@ page import="java.util.List" %><%--
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
        <jsp:param name="title" value="Storico servizi"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="http://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/rowreorder/1.2.8/js/dataTables.rowReorder.min.js"></script>
    <script src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script>
        $(document).ready(function() {
            $('#StoricoPrenotazioniServiziTable').DataTable( {
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
                },
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
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
                    Storico servizi
                </h4>
            </div>
        <table id="StoricoPrenotazioniServiziTable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Nome servizio</th>
                <th>Numero persone prenotate</th>
            </tr>
            </thead>
            <tbody>
            <%--<%
                List<PrenotazioneServizio> serviziPrenotati=(List<PrenotazioneServizio>) request.getAttribute("PrenotazioneServizi");
                for(PrenotazioneServizio s : serviziPrenotati){
            %>--%>

            <tr>
               <%-- <td><%=s.()%></td>
                <td><%=s.()%></td> --%>
                <td>Prova</td>
                <td>10</td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <th>Numero stanza</th>
                <th>Data inizio</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>
