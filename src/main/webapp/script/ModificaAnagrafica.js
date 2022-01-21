$(document).ready(function() {
    jQuery.fn.isValid = function () {
        return this[0].checkValidity()
    }
    new initValidation();
    new initValidationPass();
});

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
                    if (result.status)
                        window.location.replace("index.jsp");
                    if (result.data == "EMAIL PROBLEMA")
                        Swal.fire({
                            icon: 'error',
                            title: 'Errore registrazione...',
                            text: 'Email esistente!',
                        })
                }
            });
        }
    }
}
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
                            title: 'Modifica password avvenuta',
                        })
                        $("#VecchiaPassword").val("");
                        $("#NuovaPassword").val("");
                        $("#RipetiPassword").val("");
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Errore modifica password',
                        })
                    }
                }
            });
        }
    }
}
function initValidation() {
    this.form = $('#formAnagr');
    this.submit = $('#ButtonSub');

    this.submit.click({$this:this}, function (event) {
        var $this = event.data.$this;
        $this.form.addClass('was-validated');
    });
}
function initValidationPass() {
    this.form = $('#formPass');
    this.submit = $('#ButtonSubPass');

    this.submit.click({$this:this}, function (event) {
        var $this = event.data.$this;
        $this.form.addClass('was-validated');
    });
}

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