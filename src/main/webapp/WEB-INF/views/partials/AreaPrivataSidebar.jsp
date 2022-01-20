<%@ page import="it.hotel.model.utente.Utente" %>
<%@ page import="it.hotel.Utility.Utility" %>
<link rel="stylesheet" href="css/AreaPrivataSidebar.css">
<%
    Utente ut = (Utente) session.getAttribute(Utility.SESSION_USER);
%>

    <!-- Sidebar  -->
<nav id="sidebar">
    <div class="sidebar-header">
        <h3><%=ut.getNome()%> <%=ut.getCognome()%></h3>
    </div>
    <ul class="list-unstyled">
        <li class="active"><a href="Anagrafica">Anagrafica</a></li>
        <li><a href="StoricoPrenotazioni">Storico prenotazioni</a></li>
        <li><a href="StoricoServizi">Storico servizi</a></li>
        <% if(ut.getRuolo()==1){ %>
        <li><a href="GestionePermessi">Gestione permessi</a></li>
        <li><a href="GestioneStanze">Gestione stanze</a></li>
        <li><a href="GestioneUtenti">Gestione utenti</a></li>
        <li><a href="GestioneUtenti">Gestione prenotazioni</a></li>
        <li><a href="GestioneUtenti">Gestione servizi</a></li>
        <%}%>
    </ul>
</nav>

<!-- Overlay  -->
<div id="overlay">
    <div id="dismiss">
        <i class="fas fa-arrow-left"></i>
    </div>

</div>


