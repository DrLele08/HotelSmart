function SearchValidate(){

    var dataA = new Date(document.getElementById("dataArrivo").value);
    var dataP = new Date(document.getElementById("dataPartenza").value);
    var currentDate = new Date();

    var flag = false;
    if(dataA.getTime() > dataP.getTime() || dataA.getTime() == dataP.getTime()) flag = true;

    var flag1 = false;
    if(dataA.getTime() < currentDate.getTime() || dataP < currentDate.getTime()) flag1 = true;

    if(dataA == "" || dataA == null || dataP == "" || dataP == null || flag || flag1){
        document.getElementById("errore").style.display = "block";
        return false;
    }
    else {
        document.getElementById("errore").style.display = "none";
        return true;
    }
}