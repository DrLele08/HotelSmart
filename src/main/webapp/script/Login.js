function Login()
{
var username = $("#username").val();
var password = $("#password").val();
var data = {
    textEmail : username,
    textPwd : password
};


    $.ajax({
        url: '/HotelSmart_war/Login',
        dataType: "json",
        type: "post",
        data: data,
        success: function (result) {
            if(result)
                window.location.replace("Login");

        }
    });

}