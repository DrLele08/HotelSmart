<form action="${pageContext.request.contextPath}/ricerca/goDetailForm" method="post">
    <div class="card mb-3">
        <div class="row g-0">
            <div class="col-md-4">
                <img src="${pageContext.request.contextPath}/images/hotelroom6.jpg" class="img-fluid rounded-start" style="height: auto" alt="...">
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">Stanza${param.id}</h5>
                    <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                    <input type="hidden" name="stanzaId" value="${param.id}">
                    <input type="submit" class="btn btn-dark">
                </div>
            </div>
        </div>
    </div>
</form>