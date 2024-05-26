<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>
    <!-- Main content -->
    <div class="container">
    <form:form method="post" modelAttribute="student" class="mt-4">
        <form:hidden path="id" />

        <fieldset class="form-group">
            <form:label path="firstname" class="form-label">First Name</form:label>
            <form:input path="firstname" type="text" class="form-control" required="required" />
            <form:errors path="firstname" cssClass="text-warning" />
        </fieldset>

        <fieldset class="form-group">
            <form:label path="lastname" class="form-label">Last Name</form:label>
            <form:input path="lastname" type="text" class="form-control" required="required" />
            <form:errors path="lastname" cssClass="text-warning" />
        </fieldset>

        <fieldset class="form-group">
            <form:label path="birthDate" class="form-label">Birth Date</form:label>
            <form:input path="birthDate" type="text" class="form-control" required="required" />
            <form:errors path="birthDate" cssClass="text-warning" />
        </fieldset>

        <fieldset class="form-group">
            <form:label path="phone" class="form-label">Phone</form:label>
            <form:input path="phone" type="tel" class="form-control" required="required" />
            <form:errors path="phone" cssClass="text-warning" />
        </fieldset>

        <fieldset class="form-group">
            <form:label path="email" class="form-label">Email</form:label>
            <form:input path="email" type="email" class="form-control" required="required" />
            <form:errors path="email" cssClass="text-warning" />
        </fieldset>

        <button type="submit" class="btn btn-success">Add</button>
    </form:form>
</div>

    <%@ include file="common/footer.jspf"%>
</body>
</html>