<%--
  Autore: Alessandro d'Esposito
--%>
<%@ page import="it.hotel.model.prenotazioneStanza.PrenotazioneStanza" %>
<%@ page import="it.hotel.model.utente.Utente" %>
<%@ page import="it.hotel.Utility.Utility" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Gestione prenotazioni"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/rowreorder/1.2.8/js/dataTables.rowReorder.min.js"></script>
    <script src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.min.js"></script>
    <script src="https://cdn.rawgit.com/davidshimjs/qrcodejs/gh-pages/qrcode.min.js"></script>
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
                Optional<Utente> us = (Optional<Utente>) request.getAttribute("Utente");
                for(PrenotazioneStanza p : ps){
            %>
            <tr>
                <td><%=p.getKsStanza()%></td>
                <td><%=Utility.convertDateToView(p.getDataInizio())%></td>
                <td><%=Utility.convertDateToView(p.getDataFine())%></td>
                <td><%=p.getPrezzoFinale()%></td>
                <td><%=p.getStatoName()%></td>
                <!-- blocco if sul ruolo  -->
                <!-- in questo caso get non ha bisogno di controlli perche se non fosse settato non
                    arriverebbe in questa pagina, vedi la servlet -->

                <% if(us.get().getRuolo() == 1 || us.get().getRuolo() == 2){ %>

                <!-- Blocco Administrator -->
                <td>
                    <%
                        switch(p.getKsStato()){
                            case 1:
                    %>
                    <span data-toggle="tooltip" title="Annullare ordine"> <a class="fa fa-times icon-hover" id=iconAnnullaOrdineAdmin"" onclick='iconFillData(6,<%=p.getIdPrenotazioneStanza()%>,"<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>)'  data-toggle="modal"  data-target="#modalAnnullaOrdine"></a></span>
                    <%   break;
                        case 2:
                    %>
                    <span data-toggle="tooltip" title="Check-In"> <a class="fa fa-check icon-hover" id="iconCheckIn" onclick='iconFillData(3,<%=p.getIdPrenotazioneStanza()%>,"<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>)' data-toggle="modal"  data-target="#modalCheckIn"></a></span>
                    <%   break;
                        case 3:
                    %>
                    <span data-toggle="tooltip" title="Check-Out"> <a class="fas fa-sign-out-alt icon-hover" id="iconCheckOut" onclick='iconFillData(4,<%=p.getIdPrenotazioneStanza()%>,"<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>)' data-toggle="modal"  data-target="#modalCheckOut" ></a></span>
                    <%   break;

                    }%>

                </td>
                <!-- Blocco Utente -->
                <% }else{ %>
                <td>

                    <%
                        switch(p.getKsStato()){
                            case 1:
                    %>
                    <span data-toggle="tooltip" title="Effettuare pagamento"><a class="bi bi-credit-card icon-hover" href="PagamentoServlet?idPreno=<%=p.getIdPrenotazioneStanza()%>"></a></span>
                    <span data-toggle="tooltip" title="Annullare ordine"> <a class="fa fa-times icon-hover" id="iconAnnullaOrdineUser" onclick='iconFillData(6,<%=p.getIdPrenotazioneStanza()%>,"<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>)'  data-toggle="modal"  data-target="#modalAnnullaOrdine"></a></span>
                    <%   break;
                        case 2:
                            long miliseconds = System.currentTimeMillis();
                            Date dataAttuale = new Date((miliseconds + (1000 * 60 * 60 * 24 * 13)));
                            if((dataAttuale.compareTo(p.getDataInizio())) <= 0){
                    %>
                        <span data-toggle="tooltip" title="Richiedi rimborso"> <a class="fa fa-shopping-bag icon-hover" id="iconRichiediRimborso" onload="rimborsoGiorni(<%=p.getDataInizio()%>)" onclick='iconFillData(5,<%=p.getIdPrenotazioneStanza()%>,"<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>)' data-toggle="modal"  data-target="#modalRimborso"></a></span>
                    <%  }
                        break;
                        case 3:
                    %>

                    <span><a class="bi bi-bag-plus" data-toggle="tooltip" title="Prenota servizi" href="${pageContext.request.contextPath}/servizi/goservizi"></a></span>
                    <span data-toggle="tooltip" title="Visualizza codice qr"><a class="fa fa-qrcode icon-hover" onclick='iconTokenQr("<%=p.getTokenQr()%>")' data-toggle="modal" data-target="#modalCodiceQr"></a></span>
                    <%   break;

                    }%>
                </td>
                <!-- Fine if -->
                <% } %>

            </tr>
            <!-- Fine for -->
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

<!-- Modal annulla ordine -->
<div class="modal fade" id="modalAnnullaOrdine" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalAnnullaOrdine">Sei sicuro di voler annullare l"ordine?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <input type="hidden" class="stato" value="">
            <input type="hidden" class="idPrenotazione" value="">
            <input type="hidden" class="tokeUtente" value="">
            <input type="hidden" class="idUtente" value="">
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="cambiaStato()">Conferma</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal Rimborso -->
<div class="modal fade" id="modalRimborso" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalRimborso">Sei sicuro di voler effettuare il rimborso?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <input type="hidden" class="stato" value="">
            <input type="hidden" class="idPrenotazione" value="">
            <input type="hidden" class="tokeUtente" value="">
            <input type="hidden" class="idUtente" value="">
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="cambiaStato()">Conferma</button>
            </div>
        </div>
    </div>
</div>

<!-- Modale QR -->
<div class="modal fade" id="modalCodiceQr" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalCodiceQr">Qr code</h5>
            </div>
            <div class="modal-body">
                <div id="tokenQr"></div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
            </div>
        </div>
    </div>
</div>

<!-- Modale CheckIn -->
<div class="modal fade" id="modalCheckIn" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalCheckIn">Sei sicuro di voler effettuare il Check-in?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <input type="hidden" class="stato" value="">
            <input type="hidden" class="idPrenotazione" value="">
            <input type="hidden" class="tokeUtente" value="">
            <input type="hidden" class="idUtente" value="">
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="cambiaStato()">Conferma</button>
            </div>
        </div>
    </div>
</div>

<!-- Modale CheckOut -->
<div class="modal fade" id="modalCheckOut" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalCheckOut">Sei sicuro di voler effettuare il Check-out?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <input type="hidden" class="stato" value="">
            <input type="hidden" class="idPrenotazione" value="">
            <input type="hidden" class="tokeUtente" value="">
            <input type="hidden" class="idUtente" value="">
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="cambiaStato()">Conferma</button>
            </div>
        </div>
    </div>
</div>

<!-- Fine blocco modali -->

</body>
</html>
