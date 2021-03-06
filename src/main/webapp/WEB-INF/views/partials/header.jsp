<%@ page import="it.hotel.model.utente.Utente" %>
<%@ page import="it.hotel.Utility.Utilita" %>

<nav class="navbar sticky-top navbar-dark navbar-expand" style="background-color: #02172d;">

    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
        <img class="rounded"
             src="${pageContext.request.contextPath}/images/logo.jpeg" alt="LogoHotelSmart">
    </a>

    <%
        String temp = (String) request.getAttribute("active");
        request.removeAttribute("active");
    %>
    <span class="navbar-nav nav-item dropdown onlyMobile">
        <a class="nav-link dropdownIcon btn btn-info dropdown-toggle" data-toggle="dropdown" style="color: black">
            Menu
        </a>
            <ul class="dropdown-menu dropdown-menu-left">
                <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}">Home</a></li>
                <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/ricerca/gosearch">Stanze</a></li>
                <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/servizi/goservizi">Servizi</a></li>
                <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/home/aboutus">Chi siamo</a></li>
                <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/home/contattaci">Contattaci</a></li>
            </ul>
    </span>

    <div class="navbar-nav mr-auto" id="navbar">
        <a class="nav-item nav-link onlyDesktop" id="navbar-home" href="${pageContext.request.contextPath}">Home</a>
        <a class="nav-item nav-link onlyDesktop" id="navbar-research" href="${pageContext.request.contextPath}/ricerca/gosearch">Stanze</a>
        <a class="nav-item nav-link onlyDesktop" id="navbar-servizi" href="${pageContext.request.contextPath}/servizi/goservizi">Servizi</a>
        <a class="nav-item nav-link onlyDesktop" id="navbar-aboutus" href="${pageContext.request.contextPath}/home/aboutus">Chi siamo</a>
        <a class="nav-item nav-link onlyDesktop" id="navbar-contattaci" href="${pageContext.request.contextPath}/home/contattaci">Contattaci</a>
    </div>

    <input type="hidden" name="headercheck" id="headercheck" value="<%=temp%>">

    <%
        Utente u = (Utente) session.getAttribute(it.hotel.Utility.Utilita.SESSION_USER);
    %>
    <span class="nav-item dropdown">
        <a class="nav-link dropdownIcon btn btn-info dropdown-toggle" data-toggle="dropdown">
            <%=u == null ? "" : u.getNome()%>
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                 class="feather feather-user">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                <circle cx="12" cy="7" r="4"></circle>
            </svg>
        </a>

        <ul class="dropdown-menu dropdown-menu-right" style="right: 0;left: auto;">

            <%
                if (u == null) {
            %>
            <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/Login">Login</a></li>
            <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/Registrazione">Registrati</a></li>
            <%
            } else if (u.getRuolo() == 1) {
            %>
            <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/Anagrafica">Area privata</a></li>
            <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/Logout">Logout</a></li>
            <%
            } else if (u.getRuolo() == 2) {
            %>
            <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/Anagrafica">Area privata</a></li>
            <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/Logout">Logout</a></li>
            <%
            } else if (u.getRuolo() == 3) {
            %>
            <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/Anagrafica">Area privata</a></li>
            <li><a class="dropdown-item dropdownItem" href="${pageContext.request.contextPath}/Logout">Logout</a></li>
            <%
                }
            %>
        </ul>
    </span>
</nav>
