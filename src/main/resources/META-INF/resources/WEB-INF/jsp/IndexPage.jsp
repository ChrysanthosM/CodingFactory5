<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

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

    <%@ include file="common/footer.jspf"%>
</body>
</html>