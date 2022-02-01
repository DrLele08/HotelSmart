<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Home"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <script src="${pageContext.request.contextPath}/script/HeaderCheck.js"></script>

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

<body onload="headerCheck()" style="background-color: #cdd7e2">

<%@include file="partials/header.jsp"%>

<div class="mt-3 mx-3 jumbotron" style="background-color: whitesmoke">
    <div class="container text-center">

        <h1 class="display-4">About us</h1><br>

        <div class="mx-3 d-flex flex-column align-items-center rounded" style="background-color: whitesmoke;">

            <div id="carouselHome" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="carouselHome" data-slide-to="0" class="active"></li>
                    <li data-target="carouselHome" data-slide-to="1"></li>
                    <li data-target="carouselHome" data-slide-to="2"></li>
                    <li data-target="carouselHome" data-slide-to="3"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="${pageContext.request.contextPath}/images/trollino/img1.jpeg" alt="First slide">
                    </div>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/images/trollino/img2.jpeg" alt="Second slide">
                    </div>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/images/trollino/img3.jpeg" alt="Third slide">
                    </div>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/images/trollino/img4.jpeg" alt="Third slide">
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<%@include file="partials/footer.jsp"%>

</body>
</html>