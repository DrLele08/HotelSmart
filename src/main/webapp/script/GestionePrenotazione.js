$(document).ready(function() {
    $('#StoricoPrenotazioniTable').DataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
    } );

} );

function iconAnnullaOrdine(idPrenotazione,tokenUtente,idUtente) {
    $("#idPrenotazioneAO").val(idPrenotazione);
    $("#tokeUtenteAO").val(tokenUtente);
    $("#idUtenteAO").val(idUtente);
}
function iconRichiediRimborso(idPrenotazione,tokenUtente,idUtente) {
    $("#idPrenotazioneRR").val(idPrenotazione);
    $("#tokeUtenteRR").val(tokenUtente);
    $("#idUtenteRR").val(idUtente);
}
function iconTokenQr(tokeQr) {
    $("#tokenQr").val(tokeQr);
}

function annullaOrdine(){
    let idPrenotazione = $("#idPrenotazioneAO").val();
    let tokenUtente = $("#tokeUtenteAO").val();
    let idUtente = $("#idUtenteAO").val();
    var data = {
        textId : idPrenotazione,
        textTokenUs : tokenUtente,
        textIdUs : idUtente
    }
    $.ajax({
        url: 'Login',
        dataType: "json",
        type: "post",
        data: data,
        success: function (result) {
            if (result.Ris == 1) {
                Swal.fire({
                    icon: 'success',
                    title: 'Ordine annullato',
                })
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Errore annullamento ordine',
                    text: 'Ci sono stati dei problemi',
                })
            }
        }
    });
}
function effettuaRimborso(){
    let idPrenotazione = $("#idPrenotazioneRR").val();
    let tokenUtente = $("#tokeUtenteRR").val();
    let idUtente = $("#idUtenteRR").val();
    var data = {
        textId : idPrenotazione,
        textTokenUs : tokenUtente,
        textIdUs : idUtente
    }
    $.ajax({
        url: 'Login',
        dataType: "json",
        type: "post",
        data : data,
        success: function (result) {
            if (result.Ris == 1) {
                Swal.fire({
                    icon: 'success',
                    title: 'Rimborso effettuato',
                })
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Errore rimborso',
                    text: 'Ci sono stati dei problemi',
                })
            }
        }
    });
}

