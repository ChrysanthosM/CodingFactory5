<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>
    <!-- Main content -->
    <div class="container">
        <table class="table table-striped">
            <caption style="caption-side: top; font-weight: bold;">Teachers:</caption>
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${teachersList}" var="teacher">
                    <tr>
                        <td>${teacher.recId()}</td>
                        <td>${teacher.firstName()}</td>
                        <td>${teacher.lastName()}</td>
                        <td>${teacher.phone()}</td>
                        <td>${teacher.email()}</td>
                        <td><a class="btn btn-success" href="/updateTeacher?id=${teacher.recId()}">Update</a></td>
                        <td><a class="btn btn-warning" href="/deleteTeacher?id=${teacher.recId()}" onclick="return confirmDelete()">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <button type="button" class="btn btn-primary" onclick="window.location.href='/addTeacher';">Add Teacher</button>
            <button type="button" class="btn btn-secondary" onclick="window.location.href='/welcome';">Back</button>
        </div>
    </div>
</body>

<%@ include file="common/footer.jspf"%>

<script>
    function confirmDelete() {
        return confirm("Are you sure you want to delete this teacher?");
    }
</script>
</html>