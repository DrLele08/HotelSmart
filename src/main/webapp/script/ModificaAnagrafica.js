//Autore Alessandro d'Esposito

$(document).ready(function() {
    jQuery.fn.isValid = function () {
        return this[0].checkValidity()
    }
    new initValidation();
    new initValidationPass();
});
//Esegue la chiamata ajax se l'utente è maggiorenne e se tutti i campi rispettano le regex
//e restituisce un oggetto json dove Ris=1 nel caso la Modifica va a buon fine
//0 se la Modifica non va a buon fine
function ModificaAnagrafica()
{
    var id = $("#idUtenteAna").val();
    var token = $("#tokenUtenteAna").val();
    var nome = $("#Nome").val();
    var cognome = $("#Cognome").val();
    var codiceFiscale = $("#CodiceFiscale").val();
    var dataNascita = $("#DataNascita").val();
    var username = $("#IndirizzoEmail").val();
    var data = {
        textIdUtente : id,
        textToken : token,
        textNome : nome,
        textCognome : cognome,
        textCodiceFiscale : codiceFiscale,
        textDataNascita : dataNascita,
        textEmail : username,
    };
    if(!this.validateMaggiorenne()){
        Swal.fire({
            icon : 'error',
            title: 'utente minorenne',
        })
    }else {
        if ($('#formAnagr').isValid()) {
            $.ajax({
                url: 'api/ModificaAnagrafica',
                dataType: "json",
                type: "post",
                data: data,
                success: function (result) {
                    if (result.Ris == 1) {
                        Swal.fire({
                            icon: 'success',
                            title: result.Mess,
                        })
                        $("#formAnagr").removeClass("was-validated");
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
    }
}
//Esegue la chiamata ajax se il campo ripetiPassword è uguale a nuovaPassword
// e se tutti i campi rispettano le regex
//e restituisce un oggetto json dove Ris=1 nel caso la Modifica va a buon fine
//0 se la Modifica non va a buon fine
function ModificaPassword()
{
    var vecchiaPassword = $("#VecchiaPassword").val();
    var nuovaPassword = $("#NuovaPassword").val();
    var ripetiPassword = $("#RipetiPassword").val();
    var token = $("#tokenUtente").val();
    var id = $("#idUtentePsw").val();
    var data = {
        textToken : token,
        textOldPwd : vecchiaPassword,
        textNewPwd : nuovaPassword,
        textRepeatPwd : ripetiPassword,
        textIdUtente : id
    };
    if(!this.validateRipetiPassw()){
        Swal.fire({
            icon : 'error',
            title: 'password differenti',
        })
    }else {
        if ($('#formPass').isValid()) {
            $.ajax({
                url: 'api/ModificaPwd',
                dataType: "json",
                type: "post",
                data: data,
                success: function (result) {
                    if (result.Ris == 1) {
                        Swal.fire({
                            icon: 'success',
                            title: result.Mess,
                        })
                        $("#VecchiaPassword").val("");
                        $("#NuovaPassword").val("");
                        $("#RipetiPassword").val("");
                        $("#formPass").removeClass("was-validated");

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
    }
}
//Aggiunge la classe was-validated alla form Anagrafica se tutti i campi rispettano le regex
function initValidation() {
    this.form = $('#formAnagr');
    this.submit = $('#ButtonSub');

    this.submit.click({$this:this}, function (event) {
        var $this = event.data.$this;
        $this.form.addClass('was-validated');
    });
}
//Aggiunge la classe was-validated alla form Password se tutti i campi rispettano le regex
function initValidationPass() {
    this.form = $('#formPass');
    this.submit = $('#ButtonSubPass');

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

function validateRipetiPassw(){
    var nuovaPassword = $("#NuovaPassword").val();
    var ripetiPassword = $("#RipetiPassword").val();
    if(nuovaPassword == ripetiPassword)
        return true;
    else {
        swal.fire({
            icon : 'error',
            title: 'password differenti',
        })
        return false;
    }
}