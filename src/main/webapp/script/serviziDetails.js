function checkForm(){

    document.getElementById("errore_ospiti").style.display = "none";
    document.getElementById("errore_ospiti").style.display = "none";

    let numero_ospiti = document.getElementById("numero_ospiti").value;
    let data = new Date(document.getElementById("data").value);
    let dataCorrente = new Date();

    let value = true;

    if((!isValidDate(data)) || data.getTime() < dataCorrente.getTime()){
        document.getElementById("errore_data").style.display = "block";
        value = false;
    }
    if(isNaN(numero_ospiti) || numero_ospiti <= 0){
        document.getElementById("errore_ospiti").style.display = "block";
        value = false;
    }

    return value;

}

function prezzoTotale(){

    let numero_ospiti = document.getElementById("numero_ospiti").value;
    let costo = document.getElementById("prezzo").value;

    if((!isNaN(numero_ospiti) && numero_ospiti > 0) && !isNaN(costo)){

        let temp = document.getElementById("totalPrice").innerHTML;

        let price = numero_ospiti*costo;

        document.getElementById("totalPrice").innerHTML = "Prezzo totale: " + price;
        document.getElementById("totalPrice").style.display = "block";

    } else{
        document.getElementById("totalPrice").style.display = "none";
        document.getElementById("errore_ospiti").style.display = "block";
    }
}

function isValidDate(date) {
    return date instanceof Date && !isNaN(date);
}