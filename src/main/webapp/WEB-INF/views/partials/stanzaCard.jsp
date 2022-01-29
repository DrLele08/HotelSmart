<form action="${pageContext.request.contextPath}/ricerca/goDetailForm" method="post">
    <div class="card mb-3">
        <div class="row g-0">
            <div class="col-md-4">
                <img src="${pageContext.request.contextPath}/images/hotelroom0.jpg" class="img-fluid rounded-start" style="height: auto" alt="...">
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">Stanza ${param.id}</h5>
                    <ul>
                        <li class="card-text">Letti matrimoniali: ${param.numLetti_M}</li>
                        <li class="card-text">Letti singoli: ${param.numLetti_S}</li>
                        <li class="card-text">Costo per notte: ${param.costoNotte}</li>
                        <li class="card-text">Sconto applicato (per notte): ${param.sconto}%</li>
                    </ul>
                    <input type="hidden" name="stanzaId" value="${param.id}">
                    <input type="hidden" name="numero_ospiti" value="${param.numOspiti}">
                    <input type="submit" class="btn btn-dark">
                </div>
            </div>
        </div>
    </div>
</form>