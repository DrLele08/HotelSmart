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
    </jsp:include>

    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
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
                <h5>
                    Dettagli anagrafica utente
                </h5>
            </div>
    </div>
</div>
</body>
</html>
