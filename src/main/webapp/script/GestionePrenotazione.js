$(document).ready(function() {
    $('#StoricoPrenotazioniTable').DataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
    } );
    $('#iconAnnullaOrdine').on("click", function () {
        var idPrenotazione = $(this).data('prenotazione-id');
        $("#idPrenotazioneAO").val(idPrenotazione);
    });
    $('#iconRichiediRimborso').on("click", function () {
        var idPrenotazione = $(this).data('prenotazione-id');
        $("#idPrenotazioneRR").val(idPrenotazione);
    });
} );

function annullaOrdine(){
    let idPrenotazione = $("#idPrenotazioneAO").val();
    var data = {
        textId : idPrenotazione
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
    var data = {
        textId : idPrenotazione
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

