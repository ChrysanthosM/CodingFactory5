<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <%-- <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h1 class="mb-4">Sign Up</h1>
                <pre class="text-danger">${errorMessage}</pre>

                <form:form action="/signup" method="post" modelAttribute="user">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <form:input path="username" id="username" class="form-control" required />
                        <form:errors path="username" cssClass="text-warning" />
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <form:password path="password" id="password" class="form-control" required />
                        <form:errors path="password" cssClass="text-warning" />
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <form:password path="confirmPassword" id="confirmPassword" class="form-control" required />
                        <form:errors path="confirmPassword" cssClass="text-warning" />
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <form:input path="email" id="email" class="form-control" required />
                        <form:errors path="email" cssClass="text-warning" />
                    </div>
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <form:input path="firstName" id="firstName" class="form-control" required />
                        <form:errors path="firstName" cssClass="text-warning" />
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <form:input path="lastName" id="lastName" class="form-control" required />
                        <form:errors path="lastName" cssClass="text-warning" />
                    </div>
                    <div class="mb-3">
                        <label for="roleId" class="form-label">Select Role</label>
                        <form:select path="roleId" id="roleId" class="form-control">
                            <form:options items="${rolesList}" itemValue="roleId" itemLabel="roleDescription" />
                        </form:select>
                        <form:errors path="roleId" cssClass="text-warning" />
                    </div>

                    <button type="submit" class="btn btn-primary">Sign Up</button>
                </form:form>
            </div>
        </div>
    </div> --%>

    <div class="container mt-5">
        <form:form method="post" modelAttribute="user" class="mt-4">
            <pre class="text-danger">${errorMessage}</pre>
            <form:hidden path="recId" />

            <form:hidden path="roleId" />
            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="roleId" class="form-label">Select Role</form:label>
                    <form:select path="roleId" class="form-control">
                        <form:options items="${rolesList}" itemValue="roleId" itemLabel="roleDescription" />
                    </form:select>
                    <form:errors path="roleId" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="col-md-12">
                <fieldset class="form-group">
                    <form:label path="username" class="form-label">Username</form:label>
                    <form:input path="username" type="text" class="form-control" required="required" 
                                style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="username" cssClass="text-warning" />
                </fieldset>
            </div>
            <div class="col-md-12">
                <fieldset class="form-group">
                    <form:label path="password" class="form-label">Password</form:label>
                    <form:input path="password" type="password" class="form-control" required="required" 
                                style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="password" cssClass="text-warning" />
                </fieldset>
            </div>
            <div class="col-md-12">
                <fieldset class="form-group">
                    <form:label path="confirmPassword" class="form-label">Confirm Password</form:label>
                    <form:input path="confirmPassword" type="password" class="form-control" required="required" 
                                style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="confirmPassword" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="col-md-12">
                <fieldset class="form-group">
                    <form:label path="firstName" class="form-label">FirstName</form:label>
                    <form:input path="firstName" type="text" class="form-control" required="required" 
                                style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="firstName" cssClass="text-warning" />
                </fieldset>
            </div>
            <div class="col-md-12">
                <fieldset class="form-group">
                    <form:label path="lastName" class="form-label">LastName</form:label>
                    <form:input path="lastName" type="text" class="form-control" required="required" 
                                style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="lastName" cssClass="text-warning" />
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

            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="phone" class="form-label">Phone</form:label>
                    <form:input path="phone" type="tel" class="form-control" required="required" 
                                style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="phone" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="row">
                <div class="col-md-12 text-center">
                    <button type="submit" class="btn btn-primary">Sign Up</button>
                </div>
            </div>
        </form:form>
    </div>

</body>
<%@ include file="common/footer.jspf"%>
</html>
