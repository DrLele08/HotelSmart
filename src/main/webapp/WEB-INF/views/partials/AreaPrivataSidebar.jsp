<%@ page import="it.hotel.model.utente.Utente" %>
<%@ page import="it.hotel.Utility.Utility" %>
<link rel="stylesheet" href="css/AreaPrivataSidebar.css">
<%
    Utente ut = (Utente) session.getAttribute(Utility.SESSION_USER);
    Integer tipoChiamata = (Integer) request.getAttribute("Tipo");
%>

    <!-- Sidebar  -->
<nav id="sidebar">
    <div class="sidebar-header">
        <h3><%=ut.getNome()%> <%=ut.getCognome()%></h3>
    </div>
    <ul class="list-unstyled">
        <li <% if(tipoChiamata == 1){%>
            class="active"
        <%}%>><a href="Anagrafica">Anagrafica</a></li>
        <% if(ut.getRuolo()==1){ %>
        <li <% if(tipoChiamata == 2){%>
                class="active"
                <%}%>><a  href="GestioneUtenti">Gestione utenti</a></li>
        <%}%>
        <li <% if(tipoChiamata == 3){%>
                class="active"
                <%}%>><a  href="GestionePrenotazioni">Gestione prenotazioni</a></li>
        <% if(ut.getRuolo()==3){ %>
        <li <% if(tipoChiamata == 4){%>
                class="active"
                <%}%>><a  href="StoricoServizi">Storico servizi</a></li>
        <%}%>
        <% if(ut.getRuolo()==1 || ut.getRuolo()==2){ %>
        <li <% if(tipoChiamata == 5){%>
                class="active"
                <%}%>><a  href="GestioneStanze">Gestione stanze</a></li>
        <li <% if(tipoChiamata == 6){%>
                class="active"
                <%}%>><a  href="GestioneServizi">Gestione servizi</a></li>
        <%}%>
    </ul>
</nav>

<!-- Overlay  -->
<div id="overlay">
    <div id="dismiss">
        <i class="fas fa-arrow-left"></i>
    </div>

</div>


