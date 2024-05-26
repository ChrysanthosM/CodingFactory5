<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h1 class="mb-4">User Info</h1>
                <pre class="text-danger">${errorMessage}</pre>

                <div class="border-bottom mb-3 pb-3">
                    <label class="form-label">Username:</label>
                    <p class="form-control-static">${username}</p>
                </div>
                <div class="border-bottom mb-3 pb-3">
                    <label class="form-label">Email:</label>
                    <p class="form-control-static">${email}</p>
                </div>
                <div class="border-bottom mb-3 pb-3">
                    <label class="form-label">First Name:</label>
                    <p class="form-control-static">${firstname}</p>
                </div>
                <div class="border-bottom mb-3 pb-3">
                    <label class="form-label">Last Name:</label>
                    <p class="form-control-static">${lastname}</p>
                </div>
            </div>
        </div>

        <!-- Centered Logout Button -->
        <div class="row justify-content-center mt-4">
            <div class="col-md-2 text-center">
                <form action="/logout" method="post">
                    <button type="submit" class="btn btn-danger mb-2">Logout</button>
                </form>
            </div>
        </div>
    </div>

    <%@ include file="common/footer.jspf"%>
</body>
</html>
