$(document).ready(function() {
    $('#StoricoPrenotazioniTable').DataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
        rowReorder: {
            selector: 'td:nth-child(2)'
        },
        responsive: true
    } );
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

} );

function iconFillData(stato,idPrenotazione,tokenUtente,idUtente) {
    $(".stato").val(stato);
    $(".idPrenotazione").val(idPrenotazione);
    $(".tokeUtente").val(tokenUtente);
    $(".idUtente").val(idUtente);
}

function cambiaStato(){
    let stato = $(".stato").val();
    let idPrenotazione = $(".idPrenotazione").val();
    let tokenUtente = $(".tokeUtente").val();
    let idUtente = $(".idUtente").val();
    var data = {
        Stato : stato,
        idPreno : idPrenotazione,
        Token : tokenUtente,
        idUtente : idUtente
    }
    $.ajax({
        url: 'api/ModificaStato',
        dataType: "json",
        type: "post",
        data: data,
        success: function (result) {
            if (result.Ris == 1) {
                Swal.fire({
                    icon: 'success',
                    title: result.Mess,
                }).then(function(){
                    location.reload();
                })
                $(".modal .close").click();
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Errore',
                    text: result.Mess,
                })
            }
        }
    });
}

function iconTokenQr(tokeQr) {
    const divQr = document.getElementById('tokenQr');
    divQr.innerHTML = '';
    $("#tokenQr").val(tokeQr);
    // input
    console.log(tokeQr);
    let qrcode = new QRCode("tokenQr");
    qrcode.clear();
    qrcode.makeCode(tokeQr);

}

function cleanQr(){
    const divQr = document.getElementById('tokenQr');
    divQr.innerHTML = '';
}


