<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Home"/>
        <jsp:param name="styles" value="header.css"/>
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

<%@include file="WEB-INF/views/partials/header.jsp"%>

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

<%@include file="WEB-INF/views/partials/footer.jsp"%>


</body>
</html>