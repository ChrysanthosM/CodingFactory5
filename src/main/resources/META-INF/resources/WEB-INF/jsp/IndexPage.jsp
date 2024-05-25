<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <!-- Bootstrap CSS -->
    <link href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">MyApp</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/login">Login</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- Main content -->
    <div class="container mt-5">
        <div class="jumbotron">
            <h1 class="display-4">Welcome to my first Spring Boot WEB Application!</h1>
            <p class="lead">This is a simple web application built with Spring Boot and enhanced with Bootstrap for styling.</p>
            <hr class="my-4">
            <p>Click the button below to go to the login page.</p>
            <a class="btn btn-primary btn-lg" href="/login" role="button">Go to Login</a>
        </div>
    </div>

    <!-- jQuery -->
    <script src="/webjars/jquery/3.7.1/dist/jquery.min.js"></script>
    <!-- Popper.js -->
    <script src="/webjars/popper.js/1.16.1/umd/popper.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
</body>
</html>