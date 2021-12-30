
<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html>

<head>
    <title>Registrazione Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
    <link rel="stylesheet" href="css/Registrazione.css">
    <script src="script/Registrazione.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<!--Coded with love by Mutiullah Samim-->
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
                                            <input type="text" id="Nome" class="form-control form-control-lg" pattern="[A-z  À-ù ‘-]{2,30}$" required/>
                                            <div class="invalid-feedback">
                                                Inserisci un nome valido!
                                            </div>
                                        </div>

                                    </div>
                                    <div class="col-md-6 mb-4">

                                        <div class="form-outline">
                                            <label class="form-label" for="Cognome">Cognome</label>
                                            <input type="text"id="Cognome" class="form-control form-control-lg" pattern="[A-z  À-ù ‘-]{2,30}$" required/>
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
                                            <input type="text" id="CodiceFiscale" class="form-control form-control-lg" pattern="[A-z 0-9]{16}$" required/>
                                            <div class="invalid-feedback">
                                                Inserisci un codice fiscale valido!
                                            </div>
                                        </div>

                                    </div>

                                    <div class="col-md-6 mb-4 d-flex align-items-center">

                                        <div class="form-outline w-100">
                                            <label for="DataNascita" class="form-label">Data di nascita</label>
                                            <input id="DataNascita" type="date" class="form-control form-control-lg" required/>
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
</body>