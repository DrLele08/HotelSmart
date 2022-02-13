<%@ page import="it.hotel.model.stanza.Stanza" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: aless
  Date: 21/01/2022
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>


    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Gestione stanze"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.11.3/css/jquery.dataTables.min.css">
    <script src="http://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/rowreorder/1.2.8/js/dataTables.rowReorder.min.js"></script>
    <script src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script src="${pageContext.request.contextPath}/script/GestioneStanze.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/partials/header.jsp" %>
<div class="wrapper">

    <!-- Side Bar -->
    <%@ include file="/WEB-INF/views/partials/AreaPrivataSidebar.jsp" %>


    <div id="content">
        <button type="button" id="sidebarCollapse" class="btn btn-dark d-md-none">
            <i class="fas fa-align-justify"></i>
        </button>
        <div class="profile-head">
            <h4>
                Gestione stanze
            </h4>
        </div>
        <%//Se è un amministratore può creare una stanza utilizzando il button
            if(ut.getRuolo()==1){%>
            <button type="button" id="buttonCreazione" class="btn btn-success" onclick='createStanza("<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>)' data-toggle="modal"  data-target="#modalCreazioneStanza">Nuova stanza</button>
        <%}%>
        <!-- Table gestione stanza -->
        <table id="GestioneStanzeTable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Numero stanza</th>
                <th>Letti singoli</th>
                <th>Letti matrimoniali</th>
                <th>Costo notte</th>
                <th>Animale domestico</th>
                <th>Fumatore</th>
                <th>Sconto</th>
               <%if(ut.getRuolo()==1){%>
                <th>Azione</th>
                <%}%>
            </tr>
            </thead>

            <tbody>
            <%
                List<Stanza> stanze=(List<Stanza>) request.getAttribute("Stanze");
                for(Stanza s : stanze){
            %>
            <tr>
                <td><%=s.getIdStanza()%></td>
                <td><%=s.getLettiSingoli()%></td>
                <td><%=s.getLettiMatrimoniali()%></td>
                <td><%=s.getCostoNotte()%></td>
                <td><%if(s.getAnimaleDomestico() == true){%>
                    <i class="bi bi-check-square"><%}else{%>
                        <i class="bi bi-x-square">  <%}%>

                </td>
                <td><%if(s.getFumatore() == true){%>
                    <i class="bi bi-check-square"><%}else{%>
                        <i class="bi bi-x-square">  <%}%>
                </td>
                <td><%=s.getSconto()%></td>
                <%//Se è un amministratore può modificare una stanza utilizzando l'icon
                    if(ut.getRuolo()==1){%>
                <td>
                    <span data-toggle="tooltip" title="Modifica"><a class="fas fa-edit icon-hover" data-toggle="modal" data-target="#modalEditStanza" onclick='editStanza(<%=s.getLettiSingoli()%>, <%=s.getLettiMatrimoniali()%>, <%=s.getCostoNotte()%>, <%=s.getAnimaleDomestico()%>, <%=s.getFumatore()%>, <%=s.getSconto()%>, "<%=ut.getTokenAuth()%>",<%=ut.getIdUtente()%>,<%=s.getIdStanza()%>)'></a></span>
                </td>
                <%}%>
            </tr>
            <%}%>

            </tbody>

            <tfoot>
            <tr>
                <th>Numero stanza</th>
                <th>Letti singoli</th>
                <th>Letti matrimoniali</th>
                <th>Costo notte</th>
                <th>Animale domestico</th>
                <th>Fumatore</th>
                <th>Sconto</th>
                <%if(ut.getRuolo()==1){%>
                <th>Azione</th>
                <%}%>
            </tr>
            </tfoot>

        </table>

        <!-- Fine content -->
    </div>
    <!-- Fine wrapper -->
</div>

<!-- Modal Creazione Stanza -->

<div class="modal fade" id="modalCreazioneStanza" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalCreazioneStanze">Creazione stanza</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="lettiSingoli">Letti singoli</label>
                                <input type="number" min="0" value="0" onchange="if(!(this.value>0)){this.value= 0}else{this.value = parseInt(this.value);}" id="lettiSingoli" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="lettiMatrimoniali">Letti matrimoniali</label>
                                <input type="number" min="0" value="0" onchange="if(!(this.value>0)){this.value= 0}else{this.value = parseInt(this.value);}" id="lettiMatrimoniali" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="costoNotte">Costo notte</label>
                                <input type="number" min="0" value="0" onchange="if(!(this.value>0)){this.value= 0}" id="costoNotte" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="sconto">Sconto</label>
                                <input type="number" min="0" value="0" onchange="if(!(this.value>0)){this.value= 0}" id="sconto" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="checkAnimDom">
                                <label class="form-check-label" for="checkAnimDom">Animale domestico</label>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="checkFumatore">
                                <label class="form-check-label" for="checkFumatore">fumatore</label>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="tokenUtente" value="">
                    <input type="hidden" id="idUtente" value="">

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="CreazioneStanza()">Conferma</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal Edit Stanza -->
<div class="modal fade" id="modalEditStanza" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModalEditStanze">Modifica stanza</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="lettiSingoliEdit">Letti singoli</label>
                                <input type="number" min="0" value="" onchange="if(!(this.value>0)){this.value= 0}else{this.value = parseInt(this.value);}" id="lettiSingoliEdit" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="lettiMatrimonialiEdit">Letti matrimoniali</label>
                                <input type="number" min="0" value="" onchange="if(!(this.value>0)){this.value= 0}else{this.value = parseInt(this.value);}" id="lettiMatrimonialiEdit" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="costoNotteEdit">Costo notte</label>
                                <input type="number" min="0" value="" onchange="if(!(this.value>0)){this.value= 0}" id="costoNotteEdit" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <label class="form-label" for="scontoEdit">Sconto</label>
                                <input type="number" min="0" value="" onchange="if(!(this.value>0)){this.value= 0}" id="scontoEdit" class="form-control form-control-lg" required/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-check">

                                <input type="checkbox" class="form-check-input" id="checkAnimDomEdit">
                                <label className="form-check-label" htmlFor="checkAnimDomEdit">Animale domestico</label>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-check">
                                <input type="checkbox" className="form-check-input" id="checkFumatoreEdit">

                                <label class="form-check-label" for="checkFumatoreEdit">fumatore</label>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="idStanza" value="">
                    <input type="hidden" id="tokenUtenteEdit" value="">
                    <input type="hidden" id="idUtenteEdit" value="">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" onclick="updateStanza()">Conferma</button>
            </div>
        </div>
    </div>
</div>


</body>
</html>