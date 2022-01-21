$(document).ready(function() {
    $('#StoricoPrenotazioniTable').DataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
    } );
} );

function annullaOrdine(){
        $.ajax({
            url: 'Login',
            dataType: "json",
            type: "post",
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
    $.ajax({
        url: 'Login',
        dataType: "json",
        type: "post",
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