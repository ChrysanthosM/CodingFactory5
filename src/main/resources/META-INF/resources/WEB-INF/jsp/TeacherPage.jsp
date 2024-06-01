<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <div class="container mt-5">
        <form:form method="post" modelAttribute="teacher" class="mt-4">
            <form:hidden path="recId" />

            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="userId" class="form-label">Select User for Teacher</form:label>
                    <form:select path="userId" class="form-control">
                        <form:options items="${usersList}" itemValue="recId" itemLabel="fullName" />
                    </form:select>
                    <form:errors path="userId" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="phone" class="form-label">Phone</form:label>
                    <form:input path="phone" type="tel" class="form-control" required="required" 
                                style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="phone" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="col-md-12">
                <fieldset class="form-group">
                    <form:label path="email" class="form-label">Email</form:label>
                    <form:input path="email" type="email" class="form-control" required="required" 
                                style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="email" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="row">
                <div class="col-md-12 text-center">
                    <button type="submit" class="btn btn-success">${submitButton}</button>
                    <button type="button" class="btn btn-secondary" onclick="window.location.href='/cancelTeacher';">Cancel</button>
                </div>
            </div>
        </form:form>
    </div>
</body>

<%@ include file="common/footer.jspf"%>
</html>