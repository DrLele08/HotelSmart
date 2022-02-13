//Autore Alessandro d'Esposito

$(document).ready(function() {
    $('#GestioneUtentiTable').DataTable( {
        //Rende la tabella in italiano
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
        //Rende la tabella responsive
        rowReorder: {
            selector: 'td:nth-child(2)'
        },
        responsive: true
    } );
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

} );
//Inserisce nel modal i valori in campi hidden e nel campo ruolo
function iconModificaPermessi(idUtente,tokenUtenteLoggato,idUtenteLoggato,idRuolo) {
    $("#idUtenteLoggato").val(idUtenteLoggato);
    $("#tokenUtenteLoggato").val(tokenUtenteLoggato);
    $("#idUtenteInput").val(idUtente);
    $("#selectPermessi").val(idRuolo);
}
//Esegue la chiamata ajax e restituisce un oggetto json dove Ris=1 nel caso la Modifica va a buon fine
//0 se la Modifica non va a buon fine
function ModificaPermessi(){
    let idUtenteLoggato = $("#idUtenteLoggato").val();
    let tokenUtenteLoggato = $("#tokenUtenteLoggato").val();
    let idUtente = $("#idUtenteInput").val();
    let tipoPersmessi = $("#selectPermessi").val();
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
                }).then(function(){
                    location.reload();
                })
                $("#modalModificaPermessi .close").click();

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
