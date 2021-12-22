<%@ page import="it.hotel.model.utente.Utente" %>
<%@ page import="it.hotel.Utility.Utility" %>
<link rel="stylesheet" href="css/header.css">

<nav class="navbar sticky-top navbar-dark bg-dark navbar-expand" style="background-color: #02172d;">
    <a class="navbar-brand" href="#">
        <img class="rounded"
             src="http://localhost:8080/HotelSmart_war_exploded/images/Logo.jpeg" alt="LogoHotelSmart">
    </a>
    <div class="navbar-nav mr-auto">
        <a class="nav-item nav-link active" href="#">Home</a>
        <a class="nav-item nav-link" href="#">Stanze</a>
        <a class="nav-item nav-link" href="#">Servizi</a>
        <a class="nav-item nav-link" href="#">Chi siamo</a>
        <a class="nav-item nav-link" href="#">Contattaci</a>
    </div>

    <span class="nav-item dropdown">
        <a class="nav-link dropdownIcon btn btn-info dropdown-toggle" data-toggle="dropdown">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                 class="feather feather-user">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                <circle cx="12" cy="7" r="4"></circle>
            </svg>
        </a>

        <ul class="dropdown-menu dropdown-menu-right" style="right: 0;left: auto;">

            <%
                Utente u = (Utente) session.getAttribute(Utility.SESSION_USER);
                if (u == null) {
            %>
            <li><a class="dropdown-item dropdownItem" href="Login">Login</a></li>
            <li><a class="dropdown-item dropdownItem" href="">Registrati</a></li>
            <%
            }
            else if(u.getRuolo() == 1) {
            %>
            <li><a class="dropdown-item dropdownItem" href="">Area privata</a></li>
            <%
            }
            else if(u.getRuolo() == 2) {
            %>
            <li><a class="dropdown-item dropdownItem" href="">Area privata</a></li>
            <%
            }
            else if(u.getRuolo() == 3) {
            %>
            <li><a class="dropdown-item dropdownItem" href="">Area privata</a></li>
            <%
                }
            %>
        </ul>
    </span>
</nav>
