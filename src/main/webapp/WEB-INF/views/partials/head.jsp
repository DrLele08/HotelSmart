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
<link rel="shortcut icon" href="${context}/images/favicon.ico" type="image/x-icon">
<link rel="icon" href="${context}/images/favicon.ico" type="image/x-icon">
<!-- Contenuti di default -->
<meta charset="UTF-8">
<title> ${param.title} </title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Scripts -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<!-- Css -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.2.9/css/responsive.dataTables.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/rowreorder/1.2.8/css/rowReorder.dataTables.min.css">

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
