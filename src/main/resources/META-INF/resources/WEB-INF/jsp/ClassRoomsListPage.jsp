<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>
    <!-- Main content -->
    <div class="container">
        <table class="table table-striped">
            <caption style="caption-side: top; font-weight: bold;">Classes:</caption>
            <thead class="thead-dark">
                <tr>
                   <th class="d-none">ID</th>
                    <th>Name</th>
                    <th class="d-none">TeacherUserID</th>
                    <th>Teacher</th>
                    <th class="d-none">LessonID</th>
                    <th>Lesson</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${classRoomsList}" var="classRoom">
                    <tr>
                        <td class="d-none">${classRoom.recId()}</td>
                        <td>${classRoom.classRoomName()}</td>
                        <td class="d-none">${classRoom.teacherUserId()}</td>
                        <td>${classRoom.teacherName()}</td>
                        <td class="d-none">${classRoom.lessonId()}</td>
                        <td>${classRoom.lessonName()}</td>
                        
                        <c:if test="${roleId == 0}">
                            <td><a class="btn btn-success" href="/updateClassRoom?id=${classRoom.recId()}">Update</a></td>
                            <td><a class="btn btn-warning" href="/deleteClassRoom?id=${classRoom.recId()}" onclick="return confirmDelete()">Delete</a></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <c:if test="${roleId == 0}">
                <button type="button" class="btn btn-primary" onclick="window.location.href='/addClassRoom';">Add Class</button>
            </c:if>
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