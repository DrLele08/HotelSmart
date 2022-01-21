<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="ricerca"/>
        <jsp:param name="styles" value="header.css"/>
        <jsp:param name="styles" value="Registrazione.css"/>
    </jsp:include>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/Registrazione.js"></script>
    <script src="${pageContext.request.contextPath}/script/Ricerca.js"></script>

    <style>

        .invalid-feedback {
            display: none;
        }

        @media screen and (max-width: 1203px) {
           .card {
               width: 30rem;
               height: 30rem;
           }
           .card-body {
               width: 30rem;
               height: 30rem;
           }
           .card-img-top {
               width: 30rem;
               height: 30rem;
           }
           .carousel-item {
               width: 30rem;
               height: 30rem;
           }
        }

        @media screen and (max-width: 1000px) {
            .card {
                width: 20rem;
                height: 30rem;
            }
            .card-body {
                width: 20rem;
                height: 30rem;
            }
            .card-img-top {
                width: 20rem;
                height: 30rem;
            }
            .carousel-item {
                width: 20rem;
                height: 30rem;
            }
        }

        @media screen and (max-width: 820px) {
            .carousel {
                display: none;
            }
        }
    </style>
</head>

<body style="background-color: #cdd7e2">

<%@include file="/WEB-INF/views/partials/header.jsp" %>

<%
    Double max = (Double) request.getAttribute("max_price");
    Double min = (Double) request.getAttribute("min_price");
    ArrayList<Integer> Letti_s = (ArrayList<Integer>) request.getAttribute("Letti_s");
    ArrayList<Integer> Letti_m = (ArrayList<Integer>) request.getAttribute("Letti_m");
%>
<div class="mt-3 mx-5 jumbotron" style="background-color: whitesmoke">
    <div class="container">

        <h1 class="display-4">Cerca la stanza giusta per te</h1><br>

        <div class="container row">
            <form action="${pageContext.request.contextPath}/ricerca/dosearch" method="post">

                <div class="form-group w-100">
                    <label for="dataArrivo">Data di arrivo</label><br>
                    <input type="date" id="dataArrivo" name="dataArrivo">
                </div>

                <div class="form-group w-100">
                    <label for="dataPartenza">Data di partenza</label><br>
                    <input type="date" id="dataPartenza" name="dataPartenza">
                    <div class="invalid-feedback" id="errore">
                        Date non selezionate correttamente
                    </div>
                </div>

                <div class="form-group w-100">
                    <label for="prezzoMinimo">Prezzo Minimo</label>
                    <select class="form-control w-auto" name="prezzoMinimo" id="prezzoMinimo">
                        <option><%=min%></option>
                        <option><%=min+20.0%></option>
                        <option><%=min+40.0%></option>
                        <option><%=max-40.0%></option>
                        <option><%=max%></option>
                    </select>
                </div>

                <div class="form-group w-100">
                    <label for="prezzoMassimo">Prezzo Massimo</label>
                    <select class="form-control w-auto" name="prezzoMassimo" id="prezzoMassimo">
                        <option><%=min%></option>
                        <option><%=min+20.0%></option>
                        <option><%=min+40.0%></option>
                        <option><%=max-40.0%></option>
                        <option><%=max%></option>
                    </select>
                </div>

                <div class="form-group w-100">
                    <label for="letti_matrimoniali">Numero letti matrimoniali</label>
                    <select class="form-control w-auto" name="letti_matrimoniali" id="letti_matrimoniali">
                        <%for(Integer i: Letti_m){ %>
                        <option><%=i%></option>
                        <%}%>
                    </select>
                </div>

                <div class="form-group w-100">
                    <label for="letti_singoli">Numero letti singoli</label>
                    <select class="form-control w-auto" name="letti_singoli" id="letti_singoli">
                        <%for(Integer i: Letti_s){ %>
                        <option><%=i%></option>
                        <%}%>
                    </select>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="animaleDom" value="animaleDom"
                           id="animaleDom">
                    <label class="form-check-label" for="animaleDom">
                        Animale domestico
                    </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="fumatore" value="vistaMare" id="fumatore">
                    <label class="form-check-label" for="fumatore">
                        Fumatore
                    </label>
                </div>
                <br>

                <p style="font-size: smaller">I prezzi selezionati si riferiscono per camera</p><br>
                <button type="submit" onclick="return SearchValidate()" class="btn btn-dark">Cerca</button>
            </form>

            <div id="carouselResearch" class="carousel slide ml-auto" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="carouselHome" data-slide-to="0" class="active"></li>
                    <li data-target="carouselHome" data-slide-to="1"></li>
                    <li data-target="carouselHome" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <div class="card"
                             style="width: 40rem;margin-left: auto; height: 35rem; margin-top: 2rem; background-color: #cdd7e2;">
                            <img class="card-img-top img-fluid" src="${pageContext.request.contextPath}/images/hotelroom6.jpg"
                                 alt="Card image cap">
                            <div class="card-body" style="background-color: #cdd7e2;">
                                <h5 class="card-title">Scopri le nostre offerte speciali!</h5>
                                <p class="card-text">Suite matrimoniale con balcone e possibilità di animale domestico a
                                    partire da
                                    150€ a notte.</p>
                                <a href="#" class="btn btn-dark">Prenota ora</a>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <div class="card"
                             style="width: 40rem;margin-left: auto; height: 35rem; margin-top: 2rem; background-color: #cdd7e2;">
                            <img class="card-img-top img-fluid" src="${pageContext.request.contextPath}/images/hotelroom7.jpg"
                                 alt="Card image cap">
                            <div class="card-body" style="background-color: #cdd7e2;">
                                <h5 class="card-title">Scopri le nostre offerte speciali!</h5>
                                <p class="card-text">Suite matrimoniale con balcone e possibilità di animale domestico a
                                    partire da
                                    150€ a notte.</p>
                                <a href="#" class="btn btn-dark">Prenota ora</a>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <div class="card"
                             style="width: 40rem;margin-left: auto; height: 35rem; margin-top: 2rem; background-color: #cdd7e2;">
                            <img class="card-img-top img-fluid" src="${pageContext.request.contextPath}/images/hotelroom9.jpg"
                                 alt="Card image cap">
                            <div class="card-body" style="background-color: #cdd7e2;">
                                <h5 class="card-title">Scopri le nostre offerte speciali!</h5>
                                <p class="card-text">Suite matrimoniale con balcone e possibilità di animale domestico a
                                    partire da
                                    150€ a notte.</p>
                                <a href="#" class="btn btn-dark">Prenota ora</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="partials/footer.jsp" %>

</body>
</html>
