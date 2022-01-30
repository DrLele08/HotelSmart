function checkPayment(){

    let idPreno = document.getElementById("idPreno").value;

    $.ajax({
        url: 'api/CheckPayment',
        dataType: "json",
        type: "GET",
        data: {
            idPreno:idPreno
        },
        success: function (result) {
            if (result.Ris == 1) {
                document.getElementById("success").style.display = "block";
                document.getElementById("failure").style.display = "none";
            } else {
                document.getElementById("failure").style.display = "block";
                document.getElementById("success").style.display = "none";
            }
        }
    });
}