function checkReservationsState() {

    let idUtente = document.getElementById("userId").value;
    let token = document.getElementById("token").value;

    let returnvalue = false;

    const xhttp = new XMLHttpRequest();
    const path = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));

    if(idUtente != "" && token != "") {
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState === XMLHttpRequest.DONE) {
                if (xhttp.status >= 100 && xhttp.status < 300) {

                    var result = JSON.parse(xhttp.responseText).InCorso;

                    if (result == true) {
                        returnvalue = false;
                    } else {
                        returnvalue = true;
                    }

                }
            }
        }

        xhttp.open("GET", path + "/api/CheckPrenoSospesa?idUtente=" + idUtente + "&token=" + token, false);
        xhttp.send();
    }else{
        returnvalue = true;
    }

    if(!returnvalue) {
        Swal.fire({
            icon: 'error',
            title: 'Errore',
            text: 'Non è possibile prenotare nuove camere perché hai dei pagamenti in sospeso'
        })
    }
        return returnvalue;


}