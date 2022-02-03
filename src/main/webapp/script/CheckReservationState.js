function checkReservationsState(){

    let utenteId = document.getElementById("userId").value;
    let token = document.getElementById("token").value;

    var data = {
        idUtente : utenteId,
        token : "'"+token+"'",
    }

    if(utenteId != "" && token != "") {

        $.ajax({
            url: 'api/CheckPrenoSospesa',
            dataType: "json",
            type: "GET",
            data: data,
            success: function (result) {
                if (result.Ris == 1) {
                    Swal.fire({
                        icon: 'error',
                        title: Errore,
                        text: result.Mess,
                    })
                    return false;
                }
            },
            error: function (r,e,t){
                alert(r);
                alert(e);
                alert(t);
            }
        });
    } else {
        return true;
    }
}