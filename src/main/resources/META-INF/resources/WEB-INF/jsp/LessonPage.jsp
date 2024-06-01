<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <div class="container mt-5">
        <form:form method="post" modelAttribute="lesson" class="mt-4">
            <form:hidden path="recId" />

            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="name" class="form-label">Name</form:label>
                    <form:input path="name" type="text" class="form-control" required="required" style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="name" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="row">
                <div class="col-md-12 text-center">
                    <button type="submit" class="btn btn-success">${submitButton}</button>
                    <button type="button" class="btn btn-secondary" onclick="window.location.href='/cancelLesson';">Cancel</button>
                </div>
            </div>
        </form:form>
    </div>
</body>
<%@ include file="common/footer.jspf"%>
</html>