$(document).ready(function() {
    $('#GestioneUtentiTable').DataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
    } );
    $('#iconModificaPermessi').on("click", function () {
        var idUtente = $(this).data('utente-id');
        $("#idUtenteInput").val(idUtente);
    });
} );
function ModificaPermessi(){
    let idUtente = $("#idUtenteInput").val();
    let tipoPersmessi = $("#select").val();
    var data = {
        textId : idUtente,
        textTipoPermessi : tipoPersmessi
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