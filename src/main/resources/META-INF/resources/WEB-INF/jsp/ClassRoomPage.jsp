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
                    <c:choose>
                        <c:when test="${roleId == 1}">
                            <div class="form-control-plaintext">
                                <label><strong>Class Name:</strong></label>
                                <c:out value="${classRoom.classRoomName()}" />
                            </div>
                        </c:when>
                        <c:otherwise>
                            <form:label path="classRoomName" class="form-label">Class Name</form:label>
                            <form:input path="classRoomName" type="text" class="form-control" required="required" 
                                        style="height: calc(1.5em + .75rem + 2px);" />
                        </c:otherwise>
                    </c:choose>
                    <form:errors path="classRoomName" cssClass="text-warning" />
                </fieldset>
            </div>

            <div class="col-md-6">
                <fieldset class="form-group">
                    <c:choose>
                        <c:when test="${roleId == 1}">
                            <div class="form-control-plaintext">
                                <label><strong>Teacher:</strong></label>
                                <c:forEach items="${teachersList}" var="teacher">
                                    <c:if test="${classRoom.teacherUserId() == teacher.recId()}">
                                        <c:out value="${teacher.fullName()}" />
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <form:label path="teacherUserId" class="form-label">Select User for Teacher. New Teachers must be verified first from Admin</form:label>
                            <form:select path="teacherUserId" class="form-control">
                                <form:options items="${teachersList}" itemValue="recId" itemLabel="fullName" />
                            </form:select>
                            <form:errors path="teacherUserId" cssClass="text-warning" />
                        </c:otherwise>
                    </c:choose>
                </fieldset>
            </div>

            <div class="col-md-6">
                <fieldset class="form-group">
                    <c:choose>
                        <c:when test="${roleId == 1}">
                            <div class="form-control-plaintext">
                                <label><strong>Lesson:</strong></label>
                                <c:forEach items="${lessonsList}" var="lesson">
                                    <c:if test="${classRoom.lessonId() == lesson.recId()}">
                                        <c:out value="${lesson.name()}" />
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <form:label path="lessonId" class="form-label">Select Lesson</form:label>
                            <form:select path="lessonId" class="form-control">
                                <form:options items="${lessonsList}" itemValue="recId" itemLabel="name" />
                            </form:select>
                            <form:errors path="lessonId" cssClass="text-warning" />
                        </c:otherwise>
                    </c:choose>
                </fieldset>
            </div>

            <c:if test="${roleId == 0}">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <button type="submit" class="btn btn-success">${submitButton}</button>
                        <button type="button" class="btn btn-secondary" onclick="window.location.href='/cancelClassRoom';">Cancel</button>
                    </div>
                </div>
            </c:if>
            <c:if test="${roleId == 1}">
                <div class="col-md-12 text-center">
                    <button type="button" class="btn btn-secondary" onclick="window.location.href='/cancelClassRoom';">Back</button>
                </div>
            </c:if>
            
        </form:form>

        <!-- ListView for Students -->
        <c:if test="${studentsList != null}">
            <c:if test="${roleId == 0 || roleId == 1}">
                <div class="mt-5">
                    <h3>Student List</h3>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th class="d-none">ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Birth Date</th>
                                <th>Phone</th>
                                <th>Email</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${studentsList}" var="student">
                                <tr>
                                    <td class="d-none">${student.recId()}</td>
                                    <td>${student.firstName()}</td>
                                    <td>${student.lastName()}</td>
                                    <td><fmt:formatDate value="${student.birthDate()}" pattern="dd/MM/yyyy"/></td>
                                    <td>${student.phone()}</td>
                                    <td>${student.email()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </c:if>
    </div>
</body>

<%@ include file="common/footer.jspf"%>
</html>