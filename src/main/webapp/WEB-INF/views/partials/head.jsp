<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,viewport-fit=cover">

<!-- Tag per la corretta visualizzazione su iOS -->
<meta name="format-detection" content="telephone-no"> <!-- Evita che le stringhe numeriche vengano interpretate
come numeri di telefono -->
<meta name="apple-mobile-web-app-capable" content="yes"> <!-- Abilita il salvataggio del sito come webapp -->
<meta name="apple-mobile-web-app-title" content="levelUp"> <!-- Nome del sito se salvato come webapp -->
<meta name="apple-mobile-web-app-status-bar-style" content="default"> <!-- Colore della barra di stato -->
<link rel="apple-touch-icon" href="${context}/images/logo.png"> <!-- Icona nella barra dei preferiti di iOS -->
<link rel="apple-touch-startup-image" href="${context}/images/logo.png"> <!-- Icona schermata di caricamento -->

<!-- Tag per la corretta visualizzazione du Android -->
<meta name="theme-color" content="#000000"> <!-- Colore del sito -->

<!-- Contenuti di default -->
<meta charset="UTF-8">
<title> ${param.title} </title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<c:if test="${not empty param.styles}">
    <c:forEach items="${param.styles}" var="style">
        <link rel="stylesheet" href="${context}/css/${style}">
    </c:forEach>
</c:if>

<c:if test="${not empty param.scripts}">
    <c:forEach items="${param.scripts}" var="script">
        <script src="${context}/js/${script}" defer></script>
    </c:forEach>
</c:if>
