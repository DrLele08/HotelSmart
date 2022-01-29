function SearchValidate(){

    document.getElementById("date_error").style.display = "none";
    document.getElementById("errore_ospiti").style.display = "none";

    let numero_ospiti = document.getElementById("numero_ospiti").value;

    let dataArrivo = new Date(document.getElementById("dataArrivo").value);
    let dataPartenza = new Date(document.getElementById("dataPartenza").value);
    let dataCorrente = new Date();

    let returnvalue = true;

    if((!isValidDate(dataArrivo)) || (!isValidDate(dataPartenza))){
        document.getElementById("date_error").style.display = "block";
        returnvalue = false;
    }

    if(dataArrivo.getTime() >= dataPartenza.getTime() || dataArrivo.getTime() < dataCorrente.getTime()){
        document.getElementById("date_error").style.display = "block";
        returnvalue = false;
    }

    if(isNaN(numero_ospiti) || numero_ospiti <= 0){
        document.getElementById("errore_ospiti").style.display = "block";
        returnvalue = false;
    }

    return returnvalue;

}

function isValidDate(date) {
    return date instanceof Date && !isNaN(date);
}