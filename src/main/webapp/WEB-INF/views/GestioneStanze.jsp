<%--
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
    <script src="${pageContext.request.contextPath}/script/AreaPrivataSidebar.js"></script>
    <script src="${pageContext.request.contextPath}/script/GestioneStanze.js"></script>
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
                Gestione stanze
            </h4>
        </div>
        <button type="button" id="buttonCreazione" class="btn btn-success" data-toggle="modal"  data-target="#modalCreazioneStanza">Nuova stanza</button>
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
                <th>Azione</th>

            </tr>
            </thead>
            <tbody>

            <tr>
                <td>4</td>
                <td>2</td>
                <td>1</td>
                <td>35</td>
                <td><i class="bi bi-check-square"></i></td>
                <td><i class="bi bi-x-square"></i></td>
                <td>2</td>
                <td><span data-toggle="tooltip" title="Elimina"><a class="bi bi-trash"></a></span>
                    <span data-toggle="tooltip" title="Modifica"><a class="fas fa-edit"></a></span>
                </td>
            </tr>
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
                <th>Azione</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
<div class="modal fade" id="modalCreazioneStanza" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="titleModalRimborso">Creazione stanza</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form class = "needs-validation">
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <div class="form-outline">
                            <label class="form-label" for="lettiSingoli">Letti singoli</label>
                            <input type="number" id="lettiSingoli" class="form-control form-control-lg" required/>
                        </div>
                    </div>
                    <div class="col-md-6 mb-4">
                        <div class="form-outline">
                            <label class="form-label" for="lettiMatrimoniali">Letti matrimoniali</label>
                            <input type="number"id="lettiMatrimoniali" class="form-control form-control-lg" required/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <div class="form-outline">
                            <label class="form-label" for="costoNotte">Costo notte</label>
                            <input type="number" id="costoNotte" class="form-control form-control-lg" required/>
                        </div>
                    </div>
                    <div class="col-md-6 mb-4">
                        <div class="form-outline">
                            <label class="form-label" for="sconto">Sconto</label>
                            <input type="number" id="sconto" class="form-control form-control-lg" required/>
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
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Chiudi</button>
            <button type="button" class="btn btn-primary" onclick="CreazioneStanza()">Conferma</button>
        </div>
    </div>
</div>
</div>
</body>
</html>