<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <div class="container mt-4">
        <div class="jumbotron">
            <h1 class="display-4">Welcome, ${firstname} !!!</h1>
        </div>
        <div class="alert alert-primary" role="alert">
            <a href="/listStudents" class="alert-link">Click here</a> to manage your Students.
        </div>
    </div>

    <%@ include file="common/footer.jspf"%>
</body>
</html>