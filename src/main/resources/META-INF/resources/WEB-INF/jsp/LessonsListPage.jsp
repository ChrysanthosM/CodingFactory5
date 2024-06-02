<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>
    <!-- Main content -->
    <div class="container">
        <table class="table table-striped">
            <caption style="caption-side: top;">Lessons:</caption>
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${lessonsList}" var="lesson">
                    <tr>
                        <td>${lesson.recId()}</td>
                        <td>${lesson.name()}</td>
                        <td><a class="btn btn-success" href="/updateLesson?id=${lesson.recId()}">Update</a></td>
                        <td><a class="btn btn-warning" href="/deleteLesson?id=${lesson.recId()}" onclick="return confirmDelete()">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <button type="button" class="btn btn-primary" onclick="window.location.href='/addLesson';">Add Lesson</button>
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