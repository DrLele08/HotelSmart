<%@ page import="java.util.ArrayList" %>
<%@ page import="it.hotel.model.stanza.Stanza" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Risultati"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>

    <style>
        .card-text {
            font-size: x-small;
        }
    </style>
</head>

<body style="background-color: #cdd7e2">

<%@include file="/WEB-INF/views/partials/header.jsp" %>

<%
    ArrayList<Stanza> stanze = (ArrayList<Stanza>) request.getAttribute("stanze_result");
    Integer numero_ospiti = (Integer) request.getAttribute("numero_ospiti");
%>

<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container">
        <%if (stanze != null && stanze.size() > 0) {%>
        <% for (Stanza s : stanze) { %>
        <jsp:include page="/WEB-INF/views/partials/stanzaCard.jsp">
            <jsp:param name="id" value="<%=s.getIdStanza()%>"/>
            <jsp:param name="numOspiti" value="<%=numero_ospiti%>"/>
            <jsp:param name="numLetti_M" value="<%=s.getLettiMatrimoniali()%>"/>
            <jsp:param name="numLetti_S" value="<%=s.getLettiSingoli()%>"/>
            <jsp:param name="costoNotte" value="<%=s.getCostoNotte()%>"/>
            <jsp:param name="sconto" value="<%=s.getSconto()%>"/>
        </jsp:include>
        <% } %>
        <%} else {%>
        <h2 class="display-4">Siamo spiacenti! non sono state trovate camere con i requisiti indicati.</h2><br>
        <form action="${pageContext.request.contextPath}/ricerca/gosearch">
            <input type="submit" class="btn btn-dark" value="Torna alla ricera">
        </form>
        <%}%>
    </div>
</div>

<%@include file="partials/footer.jsp" %>
</body>
</html>