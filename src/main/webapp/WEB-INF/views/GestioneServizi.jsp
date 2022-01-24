<%@ page import="it.hotel.model.servizio.Servizio" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 21/01/2022
  Time: 16:06
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
    <script>
        $(document).ready(function() {
            $('#GestioneServiziTable').DataTable( {
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
                Gestione servizi
            </h4>
        </div>
        <button type="button" id="buttonCreazione" class="btn btn-success">Nuovo servizio</button>
        <table id="GestioneServiziTable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Nome</th>
                <th>Costo</th>
                <th>Limite posti</th>
                <th>Azione</th>
            </tr>
            </thead>
            <tbody>
                <tr>
                        <%
                        List<Servizio> servizi=(List<Servizio>) request.getAttribute("Servizi");
                        for(Servizio s : servizi){
                    %>
                <tr>
                    <td><%=s.getNome()%></td>
                    <td><%=s.getCosto()%></td>
                    <td><%=s.getLimitePosti()%></td>
                    <td><span data-toggle="tooltip" title="Elimina"><a class="bi bi-trash"></a></span>
                        <span data-toggle="tooltip" title="Modifica"><a class="fas fa-edit"></a></span>
                    </td>
                </tr>
            <%}%>
            </tbody>
            <tfoot>
            <tr>
                <th>Nome</th>
                <th>Costo</th>
                <th>Limite posti</th>
                <th>Azione</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>
