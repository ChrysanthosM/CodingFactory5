<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>
    <!-- Main content -->
    <div class="container">
        <table class="table table-striped">
            <caption style="caption-side: top; font-weight: bold;">Lessons:</caption>
            <thead class="thead-dark">
                <tr>
                    <th class="d-none">ID</th>
                    <th>Name</th>
                    <c:if test="${roleId == 0 || roleId == 1}">
                        <th></th>
                        <th></th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${lessonsList}" var="lesson">
                    <tr>
                        <td class="d-none">${lesson.recId()}</td>
                        <td>${lesson.name()}</td>
                        <c:if test="${roleId == 0 || roleId == 1}">
                            <td><a class="btn btn-success" href="/updateLesson?id=${lesson.recId()}">Update</a></td>
                            <td><a class="btn btn-warning" href="/deleteLesson?id=${lesson.recId()}" onclick="return confirmDelete()">Delete</a></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <c:if test="${roleId == 0 || roleId == 1}">
                <button type="button" class="btn btn-primary" onclick="window.location.href='/addLesson';">Add Lesson</button>
            </c:if>
            <button type="button" class="btn btn-secondary" onclick="window.location.href='/welcome';">Back</button>
        </div>
    </div>
</body>

<%@ include file="common/footer.jspf"%>

<script>
    function confirmDelete() {
        return confirm("Are you sure you want to delete this lesson?");
    }
</script>
</html>