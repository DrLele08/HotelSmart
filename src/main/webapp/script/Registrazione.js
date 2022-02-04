//Autore Alessandro d'Esposito

$(document).ready(function() {
    jQuery.fn.isValid = function () {
        return this[0].checkValidity()
    }
    new initValidation();
});
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
function initValidation() {
    this.form = $('form');
    this.submit = $('#ButtonSub');

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