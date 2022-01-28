
<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Registrazione"/>
        <jsp:param name="styles" value="Registrazione.css"/>
        <jsp:param name="styles" value="header.css"/>
    </jsp:include>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/Registrazione.js"></script>

</head>
<body>

    <section class="vh-100 gradient-custom">
        <div class="container py-5 h-100">
            <div class="row justify-content-center align-items-center h-100">
                <div class="col-12 col-lg-9 col-xl-7">
                    <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                        <div class="card-body p-4 p-md-5">
                            <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Registrazione</h3>
                            <form class = "needs-validation">

                                <div class="row">
                                    <div class="col-md-6 mb-4">

                                        <div class="form-outline">
                                            <label class="form-label" for="Nome">Nome</label>
                                            <input type="text" id="Nome" class="form-control form-control-lg" pattern="[A-z'-( *)]{2,30}$" required/>
                                            <div class="invalid-feedback">
                                                Inserisci un nome valido!
                                            </div>
                                        </div>

                                    </div>
                                    <div class="col-md-6 mb-4">

                                        <div class="form-outline">
                                            <label class="form-label" for="Cognome">Cognome</label>
                                            <input type="text"id="Cognome" class="form-control form-control-lg" pattern="[A-z'-( *)]{2,30}$" required/>
                                            <div class="invalid-feedback">
                                                Inserisci un cognome valido!
                                            </div>
                                        </div>

                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-4">

                                        <div class="form-outline">
                                            <label class="form-label" for="CodiceFiscale">Codice fiscale</label>
                                            <input type="text" id="CodiceFiscale" class="form-control form-control-lg" pattern="[A-z0-9]{16}$" required/>
                                            <div class="invalid-feedback">
                                                Inserisci un codice fiscale valido!
                                            </div>
                                        </div>

                                    </div>

                                    <div class="col-md-6 mb-4 d-flex align-items-center">

                                        <div class="form-outline w-100">
                                            <label for="DataNascita" class="form-label">Data di nascita</label>
                                            <input id="DataNascita" type="date" class="form-control form-control-lg" required/>
                                            <div class="invalid-feedback">
                                                Inserisci una Email valida!
                                            </div>
                                        </div>

                                    </div>

                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-4 pb-2">

                                        <div class="form-outline">
                                            <label class="form-label" for="IndirizzoEmail">Indirizzo Email</label>
                                            <input type="text" id="IndirizzoEmail" class="form-control form-control-lg" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required />
                                            <div class="invalid-feedback">
                                                Inserisci una Email valida!
                                            </div>

                                        </div>


                                    </div>
                                    <div class="col-md-6 mb-4 pb-2">

                                        <div class="form-outline">
                                            <label class="form-label" for="Password">Password</label>
                                            <input type="password" id="Password" class="form-control form-control-lg" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}:;',?+\*~$^=<>]).{8,20}$" required  />
                                            <div class="invalid-feedback">
                                                La password deve contenere almeno una lettera maiuscola,una minuscola, un numero e un carattere speciale e deve essere da 8 a 20 caratteri
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class="mt-4 pt-2">
                                    <Button onclick="Registrazione()" id="ButtonSub" class="btn btn-primary btn-lg" type="button">Conferma</button>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </section>
    <%@include file="/WEB-INF/views/partials/footer.jsp" %>
</body>