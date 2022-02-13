//Autore Alessandro d'Esposito

$(document).ready(function() {
    jQuery.fn.isValid = function () {
        return this[0].checkValidity()
    }
    new initValidation();
});
//Esegue la chiamata ajax se l'utente è maggiorenne e se tutti i campi rispettano le regex
//e restituisce un oggetto json dove Ris=1 nel caso la registrazione va a buon fine
//0 se la registrazione non va a buon fine
function Registrazione()
{
    var nome = $("#Nome").val();
    var cognome = $("#Cognome").val();
    var codiceFiscale = $("#CodiceFiscale").val();
    var dataNascita = $("#DataNascita").val();
    var username = $("#IndirizzoEmail").val();
    var password = $("#Password").val();
    var data = {
        textNome : nome,
        textCognome : cognome,
        textCodiceFiscale : codiceFiscale,
        textDataNascita : dataNascita,
        textEmail : username,
        textPwd : password
    };


    if(!this.validateMaggiorenne()){
        Swal.fire({
            icon : 'error',
            title: 'utente minorenne',
        })
    }else {
        if ($('form').isValid()) {
            $.ajax({
                url: 'Registrazione',
                dataType: "json",
                type: "post",
                data: data,
                success: function (result) {
                    if (result.Ris == 1) {
                        Swal.fire({
                            icon: 'success',
                            title: result.Mess,
                        }).then(function () {
                            window.location.replace("./");
                        });
                    }
                   else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Errore',
                            text: result.Mess,
                        })
                    }
                }
            });
        }
    }
}
//Aggiunge la classe was-validated alla form se tutti i campi rispettano le regex
function initValidation() {
    this.form = $('form');
    this.submit = $('#ButtonSub');

    this.submit.click({$this:this}, function (event) {
        var $this = event.data.$this;
        $this.form.addClass('was-validated');
    });
}
//Esegue i vari confronti sulla data di nascita per vedere se l'utente è maggiorenne
function validateMaggiorenne(){
    //document.getElementById("DataNascita").val();
    const dataNascita = $('#DataNascita').val();
    const dataSplit = dataNascita.split("-");
    let anno = Number(dataSplit[0]);
    let mese = Number(dataSplit[1]);
    let giorno = Number(dataSplit[2]);
    const dataAttuale = new Date();
    let annoAttuale = dataAttuale.getFullYear();
    let meseAttuale = dataAttuale.getMonth()+1;
    let giornoAttuale = dataAttuale.getDate();

    if(annoAttuale - anno < 18){
        swal.fire({
            icon : 'error',
            title: 'utente minorenne',
        })
        return false;
    } else if((annoAttuale - anno == 18)&&(meseAttuale<mese)){
        swal.fire({
            icon : 'error',
            title: 'utente minorenne',
        })
        return false;
    }else if((annoAttuale - anno == 18)&&(meseAttuale==mese)&&(giornoAttuale<giorno)){
        swal.fire({
            icon : 'error',
            title: 'utente minorenne',
        })
        return false;
    }
    return true;

}