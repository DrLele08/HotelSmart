$(document).ready(function() {
    $('#GestioneStanzeTable').DataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
    } );
} );
function CreazioneStanza(){
    let lettiSingoli = $("#lettiSingoli").val();
    let lettiMatrimoniali = $("#lettiMatrimoniali").val();
    let costoNotte = $("#costoNotte").val();
    let sconto = $("#sconto").val();
    let checkAnimDom = $("#checkAnimDom").is(":checked");
    let checkFumatore = $("#checkFumatore").is(":checked");

    var data = {
        textLettiSingoli : lettiSingoli,
        textLettiMatrimoniali : lettiMatrimoniali,
        textCostoNotte : costoNotte,
        textSconto : sconto,
        textCheckAnimDom : checkAnimDom,
        textCheckFumatore : checkFumatore
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