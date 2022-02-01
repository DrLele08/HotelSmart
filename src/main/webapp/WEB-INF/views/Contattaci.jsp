<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="partials/head.jsp">
        <jsp:param name="title" value="Home"/>
        <jsp:param name="styles" value="header.css"/>
        <jsp:param name="styles" value="Contattaci.css"/>
    </jsp:include>

    <script src="${pageContext.request.contextPath}/script/HeaderCheck.js"></script>

    <script>

        function fakeSend(){

            document.getElementById("failure_send").style.display = "none";
            document.getElementById("success_send").style.display = "none";

            let nome = document.getElementById("form104").value;
            let email = document.getElementById("form105").value;
            let oggetto = document.getElementById("form106").value;
            let textarea = document.getElementById("form107").value;

            if(nome === "" || email === "" || oggetto === "" || textarea === ""){
                document.getElementById("failure_send").style.display = "block";
                return false;
            } else {
                document.getElementById("success_send").style.display = "block";
                return true;
            }
        }

    </script>
</head>

<body onload="headerCheck()" style="background-color: #cdd7e2">

<%@include file="partials/header.jsp"%>

<div class="mt-3 mx-3 jumbotron" style="background-color: whitesmoke">
    <div class="container">

        <section class="form-gradient mb-5">

            <div class="card">

                <div class="header peach-gradient">

                    <div class="row d-flex justify-content-center">
                        <h3 class="display-4 mt-2">Contattaci</h3>
                    </div>

                </div>

                <div class="card-body mx-4">

                    <div class="md-form">
                        <i class="fas fa-user prefix grey-text"></i>
                        <label for="form104">Nome</label>
                        <input type="text" id="form104" class="form-control">
                    </div>

                    <div class="md-form">
                        <i class="fas fa-envelope prefix grey-text"></i>
                        <label for="form105">Email</label>
                        <input type="text" id="form105" class="form-control">
                    </div>

                    <div class="md-form">
                        <i class="fas fa-tag prefix grey-text"></i>
                        <label for="form106">Oggetto</label>
                        <input type="text" id="form106" class="form-control">
                    </div>

                    <div class="md-form">
                        <i class="fas fa-pencil-alt prefix grey-text"></i>
                        <label for="form107">Il tuo messaggio</label>
                        <textarea id="form107" class="md-textarea form-control" rows="5"></textarea>
                    </div><br>

                    <p id="success_send" style="color: green; display: none">Abbiamo ricevuto il tuo messaggio!
                        Ti ricontatteremo via email il prima possibile.</p>

                    <p id="failure_send" style="color: red; display: none">Riempi tutti i campi.</p>

                    <div class="row d-flex align-items-center mb-3 mt-4">

                        <div class="col-md-12">
                            <div class="text-center">
                                <button type="submit" onclick="return fakeSend()" class="btn btn-dark btn-rounded z-depth-1a">Invia</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </section>



    </div>
</div>

<%@include file="partials/footer.jsp"%>

</body>
</html>