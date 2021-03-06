//Autore Alessandro d'Esposito

$(document).ready(function() {
    jQuery.fn.isValid = function () {
        return this[0].checkValidity()
    }
    new initValidation();
});
//Esegue la chiamata ajax se tutti i campi rispettano le regex
//e restituisce un oggetto json dove Ris=1 nel caso il login va a buon fine
//0 se il login non va a buon fine
function Login()
{
    var username = $("#username").val();
    var password = $("#password").val();
    var ricordami = $("#ricordami").is(":checked");
    var data = {
        textEmail : username,
        textPwd : password,
        textRicordami : ricordami
    };

    if($('form').isValid()){
        $.ajax({
            url: 'Login',
            dataType: "json",
            type: "post",
            data: data,
            success: function (result) {
                if(result.Ris == 1) {
                    Swal.fire({
                        icon: 'success',
                        title: result.Mess,
                    }).then(function () {
                        window.location.href = "./";
                    })
                }else {
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

//Aggiunge la classe was-validated alla form se tutti i campi rispettano le regex
function initValidation() {
    this.form = $('form');
    this.submit = $('button.btn-primary');

    this.submit.click({$this:this}, function (event) {
        var $this = event.data.$this;
        $this.form.addClass('was-validated');
    });
}
