<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>



<link rel="stylesheet" href="css/Login.css">
<script src="script/Login.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html>

<head>
    <title>My Awesome Login Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<!--Coded with love by Mutiullah Samim-->
<body>
<section class="vh-100">
    <div class="container-fluid h-custom">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp" class="img-fluid" alt="Sample image">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                <form class = "needs-validation" >
                    <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
                        <p class="lead fw-normal mb-0 me-3">Accedi</p>
                    </div>

                    <div class="form-outline mb-4">
                        <label for="username">Indirizzo email</label>
                        <input type="text" class="form-control" id="username" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>
                        <div class="invalid-feedback">
                            Inserisci una Email valida!
                        </div>
                    </div>
                    <div class="form-outline mb-3">
                        <label for="password">Indirizzo email</label>
                        <input type="password" class="form-control" id="password" pattern="^[a-zA-Z0-9]{4,20}$" required>
                        <div class="invalid-feedback">
                            Inserisci una Password!
                        </div>
                    </div>
                    <!-- Email input -->
                    <!--
                    <div class="form-outline mb-4">
                        <input type="email" id="username" class="form-control form-control-lg"
                               placeholder="Inserisci indirizzo email" required />
                        <label class="form-label" for="username">Indirizzo email</label>
                    </div>-->

                    <!-- Password input -->
                    <!--<div class="form-outline mb-3">
                        <input type="password" id="password" class="form-control form-control-lg"
                               placeholder="Inserisci password" required />
                        <label class="form-label" for="password">Password</label>
                    </div>-->

                    <div class="d-flex justify-content-between align-items-center">
                        <!-- Checkbox -->
                        <div class="form-check mb-0">
                            <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3" />
                            <label class="form-check-label" for="form2Example3">
                                Ricordami
                            </label>
                        </div>
                        <a href="#!" class="text-body">Forgot password?</a>
                    </div>

                    <div class="text-center text-lg-start mt-4 pt-2">
                        <button onclick="Login()" type="button" class="btn btn-primary btn-lg"
                                style="padding-left: 2.5rem; padding-right: 2.5rem;">Login</button>
                        <p class="small fw-bold mt-2 pt-1 mb-0">Non hai un account? <a href="#!"
                                                                                          class="link-danger">Registrati</a></p>
                    </div>

                </form>
            </div>
        </div>
    </div>
    <div class="d-flex flex-column flex-md-row text-center text-md-start justify-content-between py-4 px-4 px-xl-5 bg-primary">
        <h5 class="text-uppercase font-weight-bold">Team di sviluppo</h5>
        <p>Pierpaolo Cammardella, Alessandro D'Esposito, Raffaele Sais, Giovanni De Pierro</p>
    </div>
</section>
</body>
</html>