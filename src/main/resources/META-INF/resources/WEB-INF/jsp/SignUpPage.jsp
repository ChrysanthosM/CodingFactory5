<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up Page</title>
    <!-- Bootstrap CSS -->
    <link href="/webjars/bootstrap/5.3.3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Datepicker CSS -->
    <link href="/webjars/bootstrap-datepicker/1.10.0/css/bootstrap-datepicker3.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">MyApp</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/login">Login</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h1 class="mb-4">Sign Up</h1>
                <pre class="text-danger">${errorMessage}</pre>

                <form action="/signup" method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="username" th:value="${username}" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" th:value="${email}" required>
                    </div>
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" th:value="${firstName}" required>
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" th:value="${lastName}" required>
                    </div>
                    <div class="mb-3">
                        <label for="birthDate" class="form-label">BirthDate</label>
                        <input type="text" class="form-control datepicker" id="birthDate" name="birthDate" th:value="${birthDate}" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Sign Up</button>
                </form>
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="/webjars/jquery/3.7.1/dist/jquery.min.js"></script>
    <!-- Popper.js -->
    <script src="/webjars/popper.js/1.16.1/umd/popper.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="/webjars/bootstrap/5.3.3/js/bootstrap.min.js"></script>
    <!-- Bootstrap Datepicker JS -->
    <script src="/webjars/bootstrap-datepicker/1.10.0/js/bootstrap-datepicker.min.js"></script>
    <!-- Initialize Datepicker -->
    <script>
        $(document).ready(function(){
            $('.datepicker').datepicker({
                format: 'dd/mm/yyyy',
                autoclose: true
            });
        });
    </script>
</body>
</html>
