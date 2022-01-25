function SearchValidate(){

    var dataA = new Date(document.getElementById("dataArrivo").value);
    var dataP = new Date(document.getElementById("dataPartenza").value);
    var currentDate = new Date();

    var numero_letti_m = document.getElementById("letti_matrimoniali").value;
    var numero_letti_s = document.getElementById("letti_singoli").value;
    var numero_ospiti = document.getElementById("numero_ospiti").value;

    var posti_letto = (numero_letti_m*2) + numero_letti_s

    var flag = false;
    if(dataA.getTime() > dataP.getTime() || dataA.getTime() == dataP.getTime()) flag = true;

    var flag1 = false;
    if(dataA.getTime() < currentDate.getTime() || dataP < currentDate.getTime()) flag1 = true;

    var flag2 = false;
    if(dataA instanceof Date) flag2 = true;

    var flag3 = false;
    if(dataP instanceof Date) flag3 = true;

    if(dataA == "" || dataA == null || dataP === "" || dataP == null || flag || flag1 || !flag2 || !flag3){
        document.getElementById("errore").style.display = "block";
        return false;
    }else if(numero_ospiti == "" || numero_ospiti == null ||
        parseInt(numero_ospiti) === false || isNaN(numero_ospiti) || posti_letto < numero_ospiti){
        document.getElementById("errore_ospiti").style.display = "block";
        return false;
    }
    else{
        document.getElementById("errore").style.display = "none";
        return true;
    }
}