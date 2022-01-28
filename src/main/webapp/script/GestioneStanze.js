$(document).ready(function() {
    $('#GestioneStanzeTable').DataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Italian.json"
        },
    } );
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })
} );

function createStanza(tokenUtente,idUtente) {
    $("#tokenUtente").val(tokenUtente);
    $("#idUtente").val(idUtente);
}

function CreazioneStanza(){
    let tokenUtente = $("#tokenUtente").val();
    let idUtente = $("#idUtente").val();
    let lettiSingoli = $("#lettiSingoli").val();
    let lettiMatrimoniali = $("#lettiMatrimoniali").val();
    let costoNotte = $("#costoNotte").val();
    let sconto = $("#sconto").val();
    let checkAnimDom = $("#checkAnimDom").is(":checked");
    let checkFumatore = $("#checkFumatore").is(":checked");

    var data = {
        idUtente : idUtente,
        Token :tokenUtente,
        LettiS : lettiSingoli,
        LettiM : lettiMatrimoniali,
        Costo : costoNotte,
        Sconto : sconto,
        Animale : checkAnimDom,
        Fumatore : checkFumatore
    }

    $.ajax({
        url: 'api/CreateStanza',
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
                    title: 'Errore creazione stanza',
                    text: result.Mess,
                })
            }
        }
    });
}




function editStanza(lettiSingoli, lettiMatrimoniali, costoNotte, animaleDomestico, fumatore, sconto, tokenUtente, idUtente) {
    $("#lettiSingoliEdit").val(lettiSingoli);
    $("#lettiMatrimonialiEdit").val(lettiMatrimoniali);
    $("#costoNotteEdit").val(costoNotte);
    $("#scontoEdit").val(sconto);
    $("#tokenUtenteEdit").val(tokenUtente);
    $("#idUtenteEdit").val(idUtente);


    // se val = true allora seleziona la checkbox altrimenti non la seleziona
    animaleDomestico ? $("#checkAnimDomEdit").prop('checked', true) : $("#checkAnimDomEdit").prop('checked', false);
    fumatore ? $("#checkFumatoreEdit").prop('checked', true) : $("#checkFumatoreEdit").prop('checked', false);

}

function updateStanza(){

    let lettiSingoli = $("#lettiSingoliEdit").val();
    let lettiMatrimoniali = $("#lettiMatrimonialiEdit").val();
    let costoNotte = $("#costoNotteEdit").val();
    let sconto = $("#scontoEdit").val();
    let checkAnimDom = $("#checkAnimDomEdit").is(":checked");
    let checkFumatore = $("#checkFumatoreEdit").is(":checked");
    let tokenUtente = $("#tokenUtenteEdit").val();
    let idUtente = $("#idUtenteEdit").val();

    var data = {
        idUtente : idUtente,
        Token : tokenUtente,
        LettiS : lettiSingoli,
        LettiM : lettiMatrimoniali,
        Costo : costoNotte,
        Sconto : sconto,
        Animale : checkAnimDom,
        Fumatore : checkFumatore
    }

    $.ajax({
        url: 'api/UpdateStanza',
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
                     title: 'Errore creazione stanza',
                     text: result.Mess,
                 })
             }
        }
    });

}