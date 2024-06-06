<!DOCTYPE html>
<html lang="en">
<%@ include file="common/header.jspf"%>
<body>
    <%@ include file="common/navigation.jspf"%>
    <!-- Main content -->
    <div class="container mt-5">
        <form id="lessonsForm" method="POST" action="/submitStudentLessons">
            <table class="table table-striped">
                <caption style="caption-side: top; font-weight: bold;">My Lessons:</caption>
                <thead class="thead-dark">
                    <tr>
                        <th class="d-none">studentId</th>
                        <th class="d-none">lessonId</th>
                        <th>Name</th>
                        <th>Selected</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${studentLessonsList}" var="studentLesson">
                        <tr>
                            <td class="d-none">${studentLesson.studentId()}</td>
                            <td class="d-none">${studentLesson.lessonId()}</td>
                            <td>${studentLesson.lessonName()}</td>
                            <td>
                                <input type="checkbox" class="form-check-input lesson-checkbox" 
                                    data-student-id="${studentLesson.studentId()}" 
                                    data-lesson-id="${studentLesson.lessonId()}" 
                                    ${studentLesson.selected() == '1' ? 'checked' : ''} onchange="showSubmitButton()">
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div>
                <button type="button" class="btn btn-secondary" onclick="window.location.href='/welcome';">Back</button>
            </div>
            <div id="submit-container" class="mt-3" style="display: none;">
                <button type="button" class="btn btn-primary" onclick="submitSelections()">Submit</button>
            </div>
        </form>
    </div>

    <script>
        let submitButtonShown = false;

        function showSubmitButton() {
            if (!submitButtonShown) {
                document.getElementById('submit-container').style.display = 'block';
                submitButtonShown = true;
            }
        }

        function submitSelections() {
            const checkboxes = document.querySelectorAll('.lesson-checkbox');
            const form = document.getElementById('lessonsForm');

            checkboxes.forEach(checkbox => {
                const studentId = checkbox.getAttribute('data-student-id');
                const lessonId = checkbox.getAttribute('data-lesson-id');
                const selected = checkbox.checked ? 1 : 0;

                const inputStudentId = document.createElement('input');
                inputStudentId.type = 'hidden';
                inputStudentId.name = 'studentId[]';
                inputStudentId.value = studentId;

                const inputLessonId = document.createElement('input');
                inputLessonId.type = 'hidden';
                inputLessonId.name = 'lessonId[]';
                inputLessonId.value = lessonId;

                const inputSelected = document.createElement('input');
                inputSelected.type = 'hidden';
                inputSelected.name = 'selected[]';
                inputSelected.value = selected;

                form.appendChild(inputStudentId);
                form.appendChild(inputLessonId);
                form.appendChild(inputSelected);
            });

            form.submit();
        }
    </script>
</body>

<%@ include file="common/footer.jspf"%>
</html>