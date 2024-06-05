<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h1 class="card-title">Welcome to the Login Page</h1>
                        <h2 class="card-subtitle mb-2 text-muted">Login</h2>
                        <p class="small">For Administration, put admin/admin.</p>
                        <p class="small">Otherwise signup for new Teacher or Student</p>

                        <pre class="text-danger">${errorMessage}</pre>
                        
                        <form action="/login" method="post">
                            <div class="form-group">
                                <label for="username">Username:</label>
                                <input type="text" id="username" name="username" class="form-control" value="${username}" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Password:</label>
                                <input type="password" id="password" name="password" class="form-control" required>
                            </div>
                            <div class="d-flex justify-content-between">
                                <button type="submit" class="btn btn-primary">Login</button>
                                <a href="/signup" class="btn btn-secondary ml-2">Sign Up</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<%@ include file="common/footer.jspf"%>
</html>