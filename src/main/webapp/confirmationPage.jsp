<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="ricerca"/>
        <jsp:param name="styles" value="header.css"/>
        <jsp:param name="styles" value="Registrazione.css"/>
    </jsp:include>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/Registrazione.js"></script>
    <script src="${pageContext.request.contextPath}/script/Ricerca.js"></script>

    <style>

        .custom-container {
            text-align: center;
        }

        .custom-elem {
            display: inline-block;
        }

    </style>
</head>

<body style="background-color: #cdd7e2">

<%@include file="/WEB-INF/views/partials/header.jsp" %>

<%
    //Passare un booleano success
    boolean success = Boolean.parseBoolean(request.getParameter("success"));
%>

<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container custom-container">

        <%if(success){%>
        <h1 class="display-4 custom-elem">Grazie! La tua prenotazione è stata confermata.</h1><br><br>

        <p>Controlla la tua email per il riepilogo informazioni o la tua area personale.</p><br>
        <%}else{%>
        <h1 class="display-4 custom-elem">Siamo spiacenti, la tua prenotazione non è andata a buon fine.</h1><br><br>

        <p>Controlla la tua email o area personale per sapere cosa è andato storto.</p><br>
        <%}%>
        <form action="${pageContext.request.contextPath}">
            <input type="submit" class="btn btn-dark" value="Torna alla home">
        </form>

    </div>
</div>

<%@include file="WEB-INF/views/partials/footer.jsp" %>

</body>
</html>