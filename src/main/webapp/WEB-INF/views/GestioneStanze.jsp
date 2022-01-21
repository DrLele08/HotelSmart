<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 21/01/2022
  Time: 16:00
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
            $('#GestioneStanzeTable').DataTable( {
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
                Gestione stanze
            </h4>
        </div>
        <button type="button" id="buttonCreazione" class="btn btn-success">Nuova stanza</button>
        <table id="GestioneStanzeTable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Numero stanza</th>
                <th>Letti singoli</th>
                <th>Letti matrimoniali</th>
                <th>Costo notte</th>
                <th>Animale domestico</th>
                <th>Fumatore</th>
                <th>Sconto</th>
                <th></th>

            </tr>
            </thead>
            <tbody>

            <tr>
                <td>4</td>
                <td>2</td>
                <td>1</td>
                <td>35</td>
                <td><i class="bi bi-check-square"></i></td>
                <td><i class="bi bi-x-square"></i></td>
                <td>2</td>
                <td><a class="bi bi-trash"></a>
                    <a class="fas fa-edit"></a>
                </td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <th>Numero stanza</th>
                <th>Letti singoli</th>
                <th>Letti matrimoniali</th>
                <th>Costo notte</th>
                <th>Animale domestico</th>
                <th>Fumatore</th>
                <th>Sconto</th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>