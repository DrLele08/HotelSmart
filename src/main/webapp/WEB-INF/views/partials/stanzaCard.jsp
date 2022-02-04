<div class="card">
    <img src="${pageContext.request.contextPath}/images/hotelroom0.jpg" class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title">Stanza ${param.id}</h5>
        <ul>
            <li class="card-text">Letti matrimoniali: ${param.numLetti_M}</li>
            <li class="card-text">Letti singoli: ${param.numLetti_S}</li>
            <li class="card-text">Costo per notte: ${param.costoNotte}</li>
            <li class="card-text">Sconto applicato (per notte): ${param.sconto}%</li>
        </ul>
        <form onsubmit="checkReservationsState()" action="${pageContext.request.contextPath}/ricerca/goDetailForm" method="post">
            <input type="hidden" name="stanzaId" value="${param.id}">
            <input type="hidden" name="numero_ospiti" value="${param.numOspiti}">
            <input type="hidden" name="dataArrivo" value="${param.dataArrivo}">
            <input type="hidden" name="dataPartenza" value="${param.dataPartenza}">
            <button type="submit" class="btn btn-dark">Seleziona</button>
        </form>
    </div>
</div>