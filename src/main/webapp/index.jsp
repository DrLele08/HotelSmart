<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/views/head.jsp">
        <jsp:param name="title" value="Home"/>
    </jsp:include>

    <style>

        .carousel-inner > .carousel-item > img {
            margin: auto;
            display: block;
            height: auto;
            max-width: 100%;
            line-height: 1;
            width: 100%;
        }

    </style>
</head>

<body style="background-color: #cdd7e2">

<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="mt-3 mx-3 jumbotron" style="background-color: whitesmoke">
    <div class="container">
        <h1 class="display-4">Benvenuto su HotelSmart</h1>
        <p class="lead">HotelSmart è la piattafroma giusta per soddisfare tutte le necessità di gestione di alberghi</p>
        <hr class="my-4">
        <p>Scopri tutti i servizi di HotelSmart registrandoti.</p>
        <p class="lead">
            <a class="btn btn-primary btn-lg" href="#" role="button" style="background-color: #02172d">Registrati</a>
        </p>
    </div>
</div>

<div class="mx-3 d-flex flex-column align-items-center rounded" style="background-color: whitesmoke;">

    <div id="carouselHome" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="carouselHome" data-slide-to="0" class="active"></li>
            <li data-target="carouselHome" data-slide-to="1"></li>
            <li data-target="carouselHome" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="images/hotelroom6.jpg" alt="First slide">
                <div class="carousel-caption d-none d-md-block">
                    <h2>Le migliori stanze per tutte le necessità</h2>
                </div>
            </div>
            <div class="carousel-item">
                <img src="images/hotelroom7.jpg" alt="Second slide">
                <div class="carousel-caption d-none d-md-block">
                    <h2>Scegli HotelSmart per un servizio di qualità</h2>
                </div>
            </div>
            <div class="carousel-item">
                <img src="images/hotelroom9.jpg" alt="Third slide">
                <div class="carousel-caption d-none d-md-block">
                    <h2>Diocane</h2>
                </div>
            </div>
        </div>
    </div>
</div>

<footer class="page-footer font-small pt-4 mt-3" style="background-color: #02172d; color:whitesmoke">
    <div class="container-fluid text-center text-md-left">
        <div class="row">
            <div class="col-md-6 mt-md-0 mt-3">
                <h5 class="text-uppercase font-weight-bold">About us</h5>
                <p>I can say categorically that no one in the White House staff, no one in this Administration,
                    presently employed, was involved in the copy pasting of this footer template. </p>
            </div>
            <hr class="clearfix w-100 d-md-none pb-3">
            <div class="col-md-6 mb-md-0 mb-3">
                <h5 class="text-uppercase font-weight-bold">Team di sviluppo</h5>
                <p>Pierpaolo Cammardella, Alessandro D'Esposito, Raffaele Sais, Giovanni De Pierro</p>
            </div>
        </div>
    </div>
    <div class="footer-copyright text-center py-3">
        <p><em>© 2021 Copyright: Tutti i diritti riservati.</em></p>
    </div>
</footer>


</body>
</html>