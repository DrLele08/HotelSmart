<%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 17/01/2022
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="it.hotel.model.prenotazioneStanza.PrenotazioneStanzaDAO" %>
<%@ page import="it.hotel.model.prenotazioneStanza.PrenotazioneStanza" %>
<%@ page import="it.hotel.model.utente.Utente" %>
<%@ page import="it.hotel.Utility.Utility" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <%
        Utente ul = (Utente) session.getAttribute(Utility.SESSION_USER);
    %>

    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Gestione prenotazioni"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="http://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script src="${pageContext.request.contextPath}/script/GestionePrenotazione.js"></script>

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
                Gestione prenotazioni
            </h4>
        </div>
        <table id="StoricoPrenotazioniTable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Numero stanza</th>
                <th>Data inizio</th>
                <th>Data fine</th>
                <th>Prezzo finale</th>
                <th>Stato</th>
                <th>Azione</th>
            </tr>
            </thead>
            <tbody>
                <%
                    List<PrenotazioneStanza> ps=(List<PrenotazioneStanza>) request.getAttribute("ListaPreno");
                    for(PrenotazioneStanza p : ps){
                %>
                    <tr>
                        <td><%=p.getKsStanza()%></td>
                        <td><%=Utility.convertDateToView(p.getDataInizio())%></td>
                        <td><%=Utility.convertDateToView(p.getDataFine())%></td>
                        <td><%=p.getPrezzoFinale()%></td>
                        <td><%=p.getStatoName()%></td>
                        <td>
                            <%
                                switch(p.getKsStato()){
                                case 1:
                            %><span data-toggle="tooltip" title="Effettuare pagamento"><a class="bi bi-credit-card" data-toggle="modal"  data-target="#modalPagamento"></a></span>
                            <span data-toggle="tooltip" title="Annullare ordine"> <a class="bi bi-x-square-fill" id="iconAnnullaOrdine" data-prenotazione-id="<%=p.getIdPrenotazioneStanza()%>" data-toggle="modal"  data-target="#modalAnnullaOrdine"></a>
                             <%   break;
                            case 2:
                             %><span data-toggle="tooltip" title="Effettuare rimborso"> <a class="bi bi-cash" id="iconRichiediRimborso" data-toggle="modal" data-prenotazione-id="<%=p.getIdPrenotazioneStanza()%>" data-target="#modalRimborso"></a>
                            <%   break;
                                case 3:
                            %><a class="bi bi-bag-plus" data-toggle="tooltip" title="Prenota servizi" href="${pageContext.request.contextPath}/servizi/goservizi"></a>
                                    <span data-toggle="tooltip" title="Visualizza codice qr"><a class="fa fa-qrcode" data-toggle="modal" data-target="#modalCodiceQr"></a></span>
                            <%   break;
                                case 4: case 5: case 6:
                            %><a data-toggle="tooltip" title="Completata" class="bi bi-archive"></a>
                            <%   break;
                            }%>
                        </td>
                    </tr>
                <% }%>
            </tbody>
            <tfoot>
            <tr>
                <th>Numero stanza</th>
                <th>Data inizio</th>
                <th>Data fine</th>
                <th>Prezzo finale</th>
                <th>Stato</th>
                <th>Azione</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
<div class="modal fade" id="modalPagamento" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalPagamento">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modalAnnullaOrdine" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalAnnullaOrdine">Sei sicuro di voler annullare l'ordine?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
                <input type="hidden" id="idPrenotazioneAO" value="">
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="annullaOrdine()">Conferma</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modalRimborso" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalRimborso">Sei sicuro di voler effettuare il rimborso?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
                <input type="hidden" id="idPrenotazioneRR" value="">
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="effettuaRimborso()">Conferma</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modalCodiceQr" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalCodiceQr">Sei sicuro di voler effettuare il rimborso?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary">Conferma</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
