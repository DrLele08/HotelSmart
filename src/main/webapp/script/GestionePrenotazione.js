//Autore Alessandro d'Esposito

$(document).ready(function() {
    $('#StoricoPrenotazioniTable').DataTable( {
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
function iconFillData(stato,idPrenotazione,tokenUtente,idUtente) {
    $(".stato").val(stato);
    $(".idPrenotazione").val(idPrenotazione);
    $(".tokeUtente").val(tokenUtente);
    $(".idUtente").val(idUtente);
}
//Esegue la chiamata ajax e restituisce un oggetto json dove Ris=1 nel caso il cambio dello stato va a buon fine
//0 se il cambio dello stato non va a buon fine
function cambiaStato(){
    let stato = $(".stato").val();
    let idPrenotazione = $(".idPrenotazione").val();
    let tokenUtente = $(".tokeUtente").val();
    let idUtente = $(".idUtente").val();
    var data = {
        Stato : stato,
        idPreno : idPrenotazione,
        Token : tokenUtente,
        idUtente : idUtente
    }
    $.ajax({
        url: 'api/ModificaStato',
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
                    title: 'Errore',
                    text: result.Mess,
                })
            }
        }
    });
}
//crea il qrCode
function iconTokenQr(tokeQr) {
    const divQr = document.getElementById('tokenQr');
    divQr.innerHTML = '';
    $("#tokenQr").val(tokeQr);
    // input
    console.log(tokeQr);
    let qrcode = new QRCode("tokenQr");
    qrcode.clear();
    qrcode.makeCode(tokeQr);


}
//Permette il dowload del qrCode
function clickQrCode(){
    //dowload qrcode
    let qelem = document.querySelector('#tokenQr img')
    let dowloadQr = document.querySelector('#dowloadQr')
    let qr = qelem.getAttribute('src');
    dowloadQr.setAttribute('href', qr);
    dowloadQr.setAttribute('download', 'qrCode');
}
//Obsolete
function cleanQr(){
    const divQr = document.getElementById('tokenQr');
    divQr.innerHTML = '';
}


