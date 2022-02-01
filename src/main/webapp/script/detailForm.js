
$(document).ready(function() {
    jQuery.fn.isValid = function () {
        return this[0].checkValidity()
    }
    new initValidation();
});

function ValidateForm(){

    let value = false;

    if(validateMaggiorenne()){

        if ($('#form').isValid()) {
            value = true;
        }
    }
    return value;

}

function initValidation() {
    this.form = $('#form');
    this.submit = $('#ButtonSub');

    this.submit.click({$this:this}, function (event) {
        var $this = event.data.$this;
        $this.form.addClass('was-validated');
    });
}

function validateMaggiorenne(){
    const dataNascita = $('#dataNascita1_notLogged').val();
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
            title: 'utente da registrare minorenne',
        })
        return false;
    } else if((annoAttuale - anno == 18)&&(meseAttuale<mese)){
        swal.fire({
            icon : 'error',
            title: 'utente da registrare minorenne',
        })
        return false;
    }else if((annoAttuale - anno == 18)&&(meseAttuale==mese)&&(giornoAttuale<giorno)){
        swal.fire({
            icon : 'error',
            title: 'utente da registrare minorenne',
        })
        return false;
    }
    return true;
}