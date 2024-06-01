<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <div class="container mt-5">
        <form:form method="post" modelAttribute="student" class="mt-4">
            <form:hidden path="recId" />

            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="firstName" class="form-label">First Name</form:label>
                    <form:input path="firstName" type="text" class="form-control" required="required" style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="firstName" cssClass="text-warning" />
                </fieldset>
            </div>
            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="lastName" class="form-label">Last Name</form:label>
                    <form:input path="lastName" type="text" class="form-control" required="required" style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="lastName" cssClass="text-warning" />
                </fieldset>
            </div>
            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="birthDate" class="form-label">Birth Date</form:label>
                    <form:input id="birthDate" path="birthDate" class="form-control datepicker" required="required" style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="birthDate" cssClass="text-warning" />
                </fieldset>
            </div>
            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="phone" class="form-label">Phone</form:label>
                    <form:input path="phone" type="tel" class="form-control" required="required" style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="phone" cssClass="text-warning" />
                </fieldset>
            </div>
            <div class="col-md-12">
                <fieldset class="form-group">
                    <form:label path="email" class="form-label">Email</form:label>
                    <form:input path="email" type="email" class="form-control" required="required" style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="email" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="row">
                <div class="col-md-12 text-center">
                    <button type="submit" class="btn btn-success">${submitButton}</button>
                    <button type="button" class="btn btn-secondary" onclick="window.location.href='/cancelStudent';">Cancel</button>
                </div>
            </div>
        </form:form>
    </div>
</body>
<%@ include file="common/footer.jspf"%>
</html>