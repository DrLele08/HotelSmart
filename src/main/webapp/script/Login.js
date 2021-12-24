$(document).ready(function() {
    jQuery.fn.isValid = function () {
        return this[0].checkValidity()
    }
    new initValidation();
});

function Login()
{
    var username = $("#username").val();
    var password = $("#password").val();
    var data = {
        textEmail : username,
        textPwd : password
    };

    if($('form').isValid()){
        $.ajax({
            url: '/HotelSmart_war/Login',
            dataType: "json",
            type: "post",
            data: data,
            success: function (result) {
                if(result.status)
                    window.location.replace("Login");
                if(!result.status)
                    Swal.fire({
                        icon: 'error',
                        title: 'Errore login...',
                        text: 'Username e/o password errate!',
                    })
            }
        });
    }
}

// Example starter JavaScript for disabling form submissions if there are invalid fields
function initValidation() {
    this.form = $('form');
    this.submit = $('button.btn-primary');

    this.submit.click({$this:this}, function (event) {
        var $this = event.data.$this;
        $this.form.addClass('was-validated');
    });
}
