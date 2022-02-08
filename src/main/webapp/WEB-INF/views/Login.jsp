
<%--
  Autore: Alessandro d'Esposito
--%>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Login"/>
        <jsp:param name="styles" value="Login.css"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/Login.js"></script>
</head>
<body>

<section class="vh-100">
    <div class="container-fluid h-custom">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp" class="img-fluid image" alt="Sample image">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                <form class = "needs-validation" >
                    <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
                        <p class="lead fw-normal mb-0 me-3">Accedi</p>
                    </div>

                    <div class="form-outline mb-4">
                        <label for="username">Indirizzo email</label>
                        <input type="text" class="form-control" id="username" pattern="[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>
                        <div class="invalid-feedback">
                            Inserisci una Email valida!
                        </div>
                    </div>
                    <div class="form-outline mb-3">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}:;',?+\*~$^=<>]).{8,20}$" required>
                        <div class="invalid-feedback">
                            Inserisci una Password valida!
                        </div>
                    </div>
                    <div class="d-flex justify-content-between align-items-center">
                        <!-- Checkbox -->
                        <div class="form-check mb-0">
                            <input class="form-check-input me-2" type="checkbox" id="ricordami" />
                            <label class="form-check-label" for="ricordami">
                                Ricordami
                            </label>
                        </div>
                    </div>

                    <div class="text-center text-lg-start mt-4 pt-2">
                        <button onclick="Login()" type="button" class="btn btn-primary btn-lg"
                                style="padding-left: 2.5rem; padding-right: 2.5rem;">Login</button>
                        <p class="small fw-bold mt-2 pt-1 mb-0">Non hai un account? <a href="${pageContext.request.contextPath}/Registrazione" class="link-danger">Registrati</a></p>
                    </div>

                </form>
            </div>

        </div>

    </div>
</section>
<%@include file="/WEB-INF/views/partials/footer.jsp" %>
</body>
</html>