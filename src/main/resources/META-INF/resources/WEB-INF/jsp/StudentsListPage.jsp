<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>
    <!-- Main content -->
    <div class="container">
        <table class="table table-striped">
            <caption style="caption-side: top;">All students:</caption>
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Birth Date</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${studentsList}" var="student">
                    <tr>
                        <td>${student.recId()}</td>
                        <td>${student.firstName()}</td>
                        <td>${student.lastName()}</td>
                        <td><fmt:formatDate value="${student.birthDate()}" pattern="dd/MM/yyyy"/></td>
                        <td>${student.phone()}</td>
                        <td>${student.email()}</td>
                        <td><a class="btn btn-success" href="/updateStudent?id=${student.recId()}">Update</a></td>
                        <td><a class="btn btn-warning" href="/deleteStudent?id=${student.recId()}" onclick="return confirmDelete()">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <a class="btn btn-primary" href="/addStudents">Add Student</a>
        </div>
    </div>
</body>

<%@ include file="common/footer.jspf"%>

<script>
    function confirmDelete() {
        return confirm("Are you sure you want to delete this student?");
    }
</script>
</html>