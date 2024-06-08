<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <div class="container mt-5">
        <form:form method="post" modelAttribute="classRoom" class="mt-4">
            <pre class="text-danger">${errorMessage}</pre>
            <form:hidden path="recId" />
            
            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="classRoomName" class="form-label">Class Name</form:label>
                    <form:input path="classRoomName" type="text" class="form-control" required="required" 
                                style="height: calc(1.5em + .75rem + 2px);" />
                    <form:errors path="classRoomName" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="teacherUserId" class="form-label">Select User for Teacher</form:label>
                    <form:select path="teacherUserId" class="form-control">
                        <form:options items="${teachersList}" itemValue="recId" itemLabel="fullName" />
                    </form:select>
                    <form:errors path="teacherUserId" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="col-md-6">
                <fieldset class="form-group">
                    <form:label path="lessonId" class="form-label">Select Lesson</form:label>
                    <form:select path="lessonId" class="form-control">
                        <form:options items="${lessonsList}" itemValue="recId" itemLabel="name" />
                    </form:select>
                    <form:errors path="lessonId" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="row">
                <div class="col-md-12 text-center">
                    <button type="submit" class="btn btn-success">${submitButton}</button>
                    <button type="button" class="btn btn-secondary" onclick="window.location.href='/cancelClassRoom';">Cancel</button>
                </div>
            </div>
        </form:form>
    </div>
</body>

<%@ include file="common/footer.jspf"%>
</html>