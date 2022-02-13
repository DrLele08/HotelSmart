//Autore Alessandro d'Esposito

$(document).ready(function() {
    $('#GestioneStanzeTable').DataTable( {
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
//Inserisce nel modal i valori in campi hidden
function createStanza(tokenUtente,idUtente) {
    $("#tokenUtente").val(tokenUtente);
    $("#idUtente").val(idUtente);
}
//Esegue la chiamata ajax e restituisce un oggetto json dove Ris=1 nel caso la creazione va a buon fine
//0 se la creazione non va a buon fine
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
                }).then(function(){
                    location.reload();
                })
                $(".modal .close").click();
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



//Inserisce nel modal i valori in campi hidden e nei vari campi della modifiac
function editStanza(lettiSingoli, lettiMatrimoniali, costoNotte, animaleDomestico, fumatore, sconto, tokenUtente, idUtente,idStanza) {
    $("#lettiSingoliEdit").val(lettiSingoli);
    $("#lettiMatrimonialiEdit").val(lettiMatrimoniali);
    $("#costoNotteEdit").val(costoNotte);
    $("#scontoEdit").val(sconto);
    $("#tokenUtenteEdit").val(tokenUtente);
    $("#idUtenteEdit").val(idUtente);
    $("#idStanza").val(idStanza);

    // se val = true allora seleziona la checkbox altrimenti non la seleziona
    animaleDomestico ? $("#checkAnimDomEdit").prop('checked', true) : $("#checkAnimDomEdit").prop('checked', false);
    fumatore ? $("#checkFumatoreEdit").prop('checked', true) : $("#checkFumatoreEdit").prop('checked', false);

}
//Esegue la chiamata ajax e restituisce un oggetto json dove Ris=1 nel caso la Modifica va a buon fine
//0 se la Modifica non va a buon fine
function updateStanza(){

    let lettiSingoli = $("#lettiSingoliEdit").val();
    let lettiMatrimoniali = $("#lettiMatrimonialiEdit").val();
    let costoNotte = $("#costoNotteEdit").val();
    let sconto = $("#scontoEdit").val();
    let checkAnimDom = $("#checkAnimDomEdit").is(":checked");
    let checkFumatore = $("#checkFumatoreEdit").is(":checked");
    let tokenUtente = $("#tokenUtenteEdit").val();
    let idUtente = $("#idUtenteEdit").val();
    let idStanza = $("#idStanza").val();

    var data = {
        idUtente : idUtente,
        Token : tokenUtente,
        idStanza : idStanza,
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
                 }).then(function(){
                     location.reload();
                 })
                 $(".modal .close").click();
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