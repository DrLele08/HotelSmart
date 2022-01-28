$(document).ready(function() {
    $('#GestioneUtentiTable').DataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
    } );
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

} );

function iconModificaPermessi(idUtente,tokenUtenteLoggato,idUtenteLoggato) {
    $("#idUtenteLoggato").val(idUtenteLoggato);
    $("#tokenUtenteLoggato").val(tokenUtenteLoggato);
    $("#idUtenteInput").val(idUtente);
}

function ModificaPermessi(){
    let idUtenteLoggato = $("#idUtenteLoggato").val();
    let tokenUtenteLoggato = $("#tokenUtenteLoggato").val();
    let idUtente = $("#idUtenteInput").val();
    let tipoPersmessi = $("#select").val();
    var data = {
        idUtente : idUtenteLoggato,
        Token : tokenUtenteLoggato,
        idUtenteCambio : idUtente,
        idRuolo : tipoPersmessi
    }
    $.ajax({
        url: 'api/ModificaPermessi',
        dataType: "json",
        type: "post",
        data: data,
        success: function (result) {
            if (result.Ris == 1) {
                Swal.fire({
                    icon: 'success',
                    title: result.Mess,
                })
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