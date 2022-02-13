//Autore Alessandro d'Esposito

$(document).ready(function() {
    $('#GestioneServiziTable').DataTable( {
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
function createServizi(tokenUtente,idUtente) {
    $("#tokenUtente").val(tokenUtente);
    $("#idUtente").val(idUtente);
}
//Inserisce nel modal i valori in campi hidden e nei vari campi di modifica
function editServizio(tokenUtente,idUtente , idServizio, nome, costo, limitePosti,descrizione){
    $("#tokenUtenteEdit").val(tokenUtente);
    $("#idUtenteEdit").val(idUtente);
    $("#idServizio").val(idServizio);
    $("#nomeServizioEdit").val(nome);
    $("#costoServizioEdit").val(costo);
    $("#limitePostiEdit").val(limitePosti);
    $("#descrizioneEdit").val(descrizione);
}
//Esegue la chiamata ajax e restituisce un oggetto json dove Ris=1 nel caso la creazione va a buon fine
//0 se la creazione non va a buon fine
function creazioneServizio(){
    let tokenUtente = $("#tokenUtente").val();
    let idUtente = $("#idUtente").val();
    let nomeServizio = $("#nomeServizio").val();
    let costoServizio = $("#costoServizio").val();
    let limitePosti = $("#limitePosti").val();
    let descrizione = $("#descrizione").val();

    const regexNome = new RegExp("[A-z0-9'-( *)]{2}$");

    var data = {
        idUtente : idUtente,
        Token : tokenUtente,
        Nome : nomeServizio,
        Prezzo : costoServizio,
        Posti : limitePosti,
        Descrizione : descrizione,
    }
    if((regexNome.test(nomeServizio))&&(nomeServizio.length<=30)&&(descrizione.length<=255)) {
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
                    }).then(function () {
                        location.reload();
                    })
                    $(".modal .close").click();
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Errore',
                        text: result.Mess,
                    })
                }
            }
        });
    } else if((regexNome.test(nomeServizio))&&(nomeServizio.length<=30)){
        Swal.fire({
            icon: 'error',
            title: 'Errore',
            text: "Inserisci una descrizione di massimo 255 caratteri",
        })
    }else{
        Swal.fire({
            icon: 'error',
            title: 'Errore',
            text: "Inserisci un nome valido",
        })
    }
}

//Esegue la chiamata ajax e restituisce un oggetto json dove Ris=1 nel caso la Modifica va a buon fine
//0 se la Modifica non va a buon fine
function updateServizio(){
    let tokenUtente = $("#tokenUtenteEdit").val();
    let idUtente = $("#idUtenteEdit").val();
    let idServizio = $("#idServizio").val();
    let nomeServizio = $("#nomeServizioEdit").val();
    let costoServizio = $("#costoServizioEdit").val();
    let limitePosti = $("#limitePostiEdit").val();
    let descrizione = $("#descrizioneEdit").val();

    const regexNome = new RegExp("[A-z0-9/!'-( *)]{2,30}$");

    var data = {
        idUtente : idUtente,
        Token : tokenUtente,
        idServizio : idServizio,
        Nome : nomeServizio,
        Prezzo : costoServizio,
        Posti : limitePosti,
        Descrizione : descrizione,
    }

    if((regexNome.test(nomeServizio))&&(descrizione.length<=255)) {
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
                    }).then(function () {
                        location.reload();
                    })
                    $(".modal .close").click();
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Errore',
                        text: result.Mess,
                    })
                }
            }
        });
    }else if(regexNome.test(nomeServizio)){
        Swal.fire({
            icon: 'error',
            title: 'Errore',
            text: "Inserisci una descrizione di massimo 255 caratteri",
        })
    }else{
        Swal.fire({
            icon: 'error',
            title: 'Errore',
            text: "Inserisci un nome valido",
        })
    }

}

