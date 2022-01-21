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
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="http://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script>
        $(document).ready(function() {
            $('#GestioneUtentiTable').DataTable( {
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
                <th></th>

            </tr>
            </thead>
            <tbody>

            <tr>
                <td>ProvaNome</td>
                <td>ProvaCognome</td>
                <td>ProvaCodiceFiscale</td>
                <td>2015-08-20</td>
                <td>ProvaEmail</td>
                <td><a class="fas fa-edit"></a></td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Codice fiscale</th>
                <th>Data di nascita</th>
                <th>Email</th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>