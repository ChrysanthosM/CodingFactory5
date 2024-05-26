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
                <c:forEach items="${myStudents}" var="student">
                    <tr>
                        <td>${student.id}</td>
                        <td>${student.firstname}</td>
                        <td>${student.lastname}</td>
                        <td><fmt:formatDate value="${student.birthDate}" pattern="dd/MM/yyyy"/></td>
                        <td>${student.phone}</td>
                        <td>${student.email}</td>
                        <td><a class="btn btn-success" href="/updateStudent?id=${student.id}">Update</a></td>
                        <td><a class="btn btn-warning" href="/deleteStudent?id=${student.id}">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <a class="btn btn-primary" href="/addStudents">Add Student</a>
        </div>
    </div>

    <%@ include file="common/footer.jspf"%>
</body>
</html>