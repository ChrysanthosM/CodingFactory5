<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="alert alert-danger">
                    <h2>Error Occurred... Please contact Support!</h2>
                    <p><strong>Message:</strong> ${exception}</p>
                    <p><strong>URL:</strong> ${url}</p>
                </div>
                <a href="/" class="btn btn-primary">Go Home</a>
            </div>
        </div>
    </div>

    <%@ include file="common/footer.jspf"%>
</body>
</html>