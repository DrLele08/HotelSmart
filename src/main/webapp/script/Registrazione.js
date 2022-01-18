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

    if($('form').isValid()){
        $.ajax({
            url: 'Registrazione',
            dataType: "json",
            type: "post",
            data: data,
            success: function (result) {
                if(result.status)
                    window.location.replace("index.jsp");
                if(result.data == "EMAIL PROBLEMA" )
                    Swal.fire({
                        icon: 'error',
                        title: 'Errore registrazione...',
                        text: 'Email esistente!',
                    })
            }
        });
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