$(document).ready(function() {
    $('#GestioneServiziTable').DataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
    } );
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })
} );

function createServizi(tokenUtente,idUtente) {
    $("#tokenUtente").val(tokenUtente);
    $("#idUtente").val(idUtente);
}

function editServizio(tokenUtente,idUtente , idServizio, nome, costo, limitePosti,descrizione){
    $("#tokenUtenteEdit").val(tokenUtente);
    $("#idUtenteEdit").val(idUtente);
    $("#idServizio").val(idUtente);
    $("#nomeServizioEdit").val(nome);
    $("#costoServizioEdit").val(costo);
    $("#limitePostiEdit").val(limitePosti);
    $("#descrizioneEdit").val(descrizione);
}

function creazioneServizio(){
    let tokenUtente = $("#tokenUtente").val();
    let idUtente = $("#idUtente").val();
    let nomeServizio = $("#nomeServizio").val();
    let costoServizio = $("#costoServizio").val();
    let limitePosti = $("#limitePosti").val();
    let descrizione = $("#descrizione").val();

    var data = {
        idUtente : idUtente,
        Token : tokenUtente,
        Nome : nomeServizio,
        Prezzo : costoServizio,
        Posti : limitePosti,
        Descrizione : descrizione,
    }

    $.ajax({
        url: 'api/CreateServizio',
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


function updateServizio(){
    let tokenUtente = $("#tokenUtenteEdit").val();
    let idUtente = $("#idUtenteEdit").val();
    let idServizio = $("#idServizio").val();
    let nomeServizio = $("#nomeServizioEdit").val();
    let costoServizio = $("#costoServizioEdit").val();
    let limitePosti = $("#limitePostiEdit").val();
    let descrizione = $("#descrizioneEdit").val();

    var data = {
        idUtente : idUtente,
        Token : tokenUtente,
        idServizio : idServizio,
        Nome : nomeServizio,
        Prezzo : costoServizio,
        Posti : limitePosti,
        Descrizione : descrizione,
    }

    $.ajax({
        url: 'api/UpdateServizio',
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

