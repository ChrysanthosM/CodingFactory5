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
                <c:forEach items="${studentDTOs}" var="studentDTO">
                    <tr>
                        <td>${studentDTO.recId()}</td>
                        <td>${studentDTO.firstName()}</td>
                        <td>${studentDTO.lastName()}</td>
                        <td><fmt:formatDate value="${studentDTO.birthDate()}" pattern="dd/MM/yyyy"/></td>
                        <td>${studentDTO.phone()}</td>
                        <td>${studentDTO.email()}</td>
                        <td><a class="btn btn-success" href="/updateStudent?id=${studentDTO.recId()}">Update</a></td>
                        <td><a class="btn btn-warning" href="/deleteStudent?id=${studentDTO.recId()}">Delete</a></td>
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