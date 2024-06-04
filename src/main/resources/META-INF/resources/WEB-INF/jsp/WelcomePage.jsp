<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>

    <!-- Main content -->
    <div class="container mt-4">
        <div class="jumbotron">
            <h1 class="display-4">Welcome, ${firstname} !!!</h1>
        </div>
        <div class="mt-4">
            <button class="btn btn-secondary btn-sm w-50 mb-2" onclick="location.href='/listLessons'">Lessons</button>
        </div>
        <div class="mt-4">
            <button class="btn btn-secondary btn-sm w-50" onclick="location.href='/listTeachers'">Teachers</button>
        </div>
        <c:if test="${roleId == 0 || roleId == 1}">
            <div class="mt-4">
                <button class="btn btn-secondary btn-sm w-50" onclick="location.href='/listStudents'">Students</button>
            </div>
        </c:if>
    </div>

</body>
<%@ include file="common/footer.jspf"%>
</html>