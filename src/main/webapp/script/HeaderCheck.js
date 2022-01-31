function headerCheck() {

    let headerValue = document.getElementById("headercheck").value;

    if(document.getElementById("navbar-home").classList.contains("active")) {
        document.getElementById("navbar-home").classList.remove("active");
    }
    if(document.getElementById("navbar-research").classList.contains("active")) {
        document.getElementById("navbar-research").classList.remove("active");
    }
    if(document.getElementById("navbar-servizi").classList.contains("active")) {
        document.getElementById("navbar-servizi").classList.remove("active");
    }
    if(document.getElementById("navbar-aboutus").classList.contains("active")) {
        document.getElementById("navbar-aboutus").classList.remove("active");
    }
    if(document.getElementById("navbar-contattaci").classList.contains("active")) {
        document.getElementById("navbar-contattaci").classList.remove("active");
    }

    if(headerValue === "null"){
        document.getElementById("navbar-home").classList.add("active");
    } else if(headerValue === "ricerca"){
        document.getElementById("navbar-research").classList.add("active");
    } else if(headerValue === "servizi"){
        document.getElementById("navbar-servizi").classList.add("active");
    } else if(headerValue === "aboutus"){
        document.getElementById("navbar-aboutus").classList.add("active");
    } else if(headerValue === "contattaci"){
        document.getElementById("navbar-contattaci").classList.add("active");
    }

}