<%@ page import="it.hotel.model.servizio.Servizio" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 21/01/2022
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>


    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Gestione utenti"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="http://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/rowreorder/1.2.8/js/dataTables.rowReorder.min.js"></script>
    <script src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script src="${pageContext.request.contextPath}/script/GestioneServizi.js"></script>
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
                Gestione servizi
            </h4>
        </div>

        <button type="button" id="buttonCreazione" class="btn btn-success" data-toggle="modal" onclick='createServizi("<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>)' data-target="#modalCreazioneServizio">Nuovo servizio</button>

        <!-- Table gestione servizi -->
        <table id="GestioneServiziTable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Nome</th>
                <th>Costo</th>
                <th>Limite posti</th>
                <th>Azione</th>
            </tr>
            </thead>

            <tbody>
            <%
                List<Servizio> servizi=(List<Servizio>) request.getAttribute("Servizi");
                for(Servizio s : servizi){
            %>
            <tr>
                <td><%=s.getNome()%></td>
                <td><%=s.getCosto()%></td>
                <td><%=s.getLimitePosti()%></td>
                <td>
                    <span data-toggle="tooltip" title="Modifica"><a class="fas fa-edit" data-toggle="modal" data-target="#modalEditServizio" onclick='editServizio("<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>,<%=s.getIdServizio()%>,"<%=s.getNome()%>", <%=s.getCosto()%>, <%=s.getLimitePosti()%>,"<%=s.getDescrizione()%>")'></a></span>
                </td>
            </tr>
            <%}%>
            </tbody>

            <tfoot>
            <tr>
                <th>Nome</th>
                <th>Costo</th>
                <th>Limite posti</th>
                <th>Azione</th>
            </tr>
            </tfoot>
        </table>
        <!-- Fine content -->
    </div>
    <!-- Fine wrapper -->
</div>

<!-- Modal Creazione Servizio -->

<div class="modal fade" id="modalCreazioneServizio" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalCreazioneServizio">Creazione servizio</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="nomeServizio">Nome Servizio</label>
                                <input type="text" id="nomeServizio" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="costoServizio">Costo Servizio</label>
                                <input type="number" min="0" value="0" onchange="if(!(this.value>0)){this.value= 0}else{this.value = parseInt(this.value);}" id="costoServizio" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="limitePosti">Limite posti</label>
                                <input type="number" min="0" value="0" onchange="if(!(this.value>0)){this.value= 0}" id="limitePosti" class="form-control form-control-lg" required/>
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-md-12 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="descrizione">Descrizione</label>
                                <textarea class="form-control" id="descrizione" rows="3"></textarea>
                            </div>
                        </div>

                    </div>
                    <input type="hidden" id="tokenUtente" value="">
                    <input type="hidden" id="idUtente" value="">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="creazioneServizio()">Conferma</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal Modifica servizio -->

<div class="modal fade" id="modalEditServizio" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalEditServizio">Creazione servizio</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="nomeServizioEdit">Nome Servizio</label>
                                <input type="text" id="nomeServizioEdit" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="costoServizioEdit">Costo Servizio</label>
                                <input type="number" min="0" value="0" onchange="if(!(this.value>0)){this.value= 0}else{this.value = parseInt(this.value);}" id="costoServizioEdit" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="limitePostiEdit">Costo notte</label>
                                <input type="number" min="0" value="0" onchange="if(!(this.value>0)){this.value= 0}" id="limitePostiEdit" class="form-control form-control-lg" required/>
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-md-12 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="descrizioneEdit">Descrizione</label>
                                <textarea class="form-control" id="descrizioneEdit" rows="3"></textarea>
                            </div>
                        </div>

                    </div>
                    <input type="hidden" id="tokenUtenteEdit" value="">
                    <input type="hidden" id="idUtenteEdit" value="">
                    <input type="hidden" id="idServizio" value="">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="updateServizio()">Conferma</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
