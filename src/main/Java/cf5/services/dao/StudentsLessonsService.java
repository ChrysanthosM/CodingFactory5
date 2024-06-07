package cf5.services.dao;

import cf5.dto.StudentLessonDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsLessonsService extends AbstractService<StudentLessonDTO> {
    private static final String querySelectOne =
            "SELECT T.STUDENT_ID, T.LESSON_ID, L.NAME AS LESSON_NAME " +
                    "FROM STUDENT_LESSONS T " +
                    "LEFT JOIN LESSONS L ON T.LESSON_ID = L.ID " +
                    "WHERE T.STUDENT_ID = ? AND L.LESSON_ID = ?";
    private static final String querySelectAll =
            "SELECT T.STUDENT_ID, T.LESSON_ID, L.NAME AS LESSON_NAME " +
                    "FROM STUDENT_LESSONS T " +
                    "LEFT JOIN LESSONS L ON T.LESSON_ID = L.ID";
    private static final String queryInsertOne = "INSERT INTO STUDENT_LESSONS (STUDENT_ID, LESSON_ID) VALUES (?, ?)";
    private static final String queryDeleteLessonsForStudent = "DELETE FROM STUDENT_LESSONS WHERE STUDENT_ID = ?";

    private static final String querySelectLessonsForStudent =
            "SELECT L.ID AS LESSON_ID, L.NAME AS LESSON_NAME, {STUDENT_ID} AS STUDENT_ID, CASE WHEN SL.STUDENT_ID IS NOT NULL THEN '1' ELSE '0' END AS SELECTED " +
                    "FROM LESSONS L " +
                    "LEFT JOIN STUDENT_LESSONS SL ON L.ID = SL.LESSON_ID AND SL.STUDENT_ID = ?";

    @Override
    public Optional<StudentLessonDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(StudentLessonDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<StudentLessonDTO> getAll() throws SQLException {
        return super.defaultSelectAll(StudentLessonDTO.newConverter(), querySelectAll);
    }

    @Transactional
    public void replaceStudentLessons(List<StudentLessonDTO> studentLessonDTOs) throws SQLException {
        if (CollectionUtils.isEmpty(studentLessonDTOs)) return;
        getJdbcIO().executeQuery(getDefaultDataSource(), queryDeleteLessonsForStudent, studentLessonDTOs.getFirst().studentId());
        for (StudentLessonDTO studentLessonDTO : studentLessonDTOs) {
            if ("1".equals(studentLessonDTO.selected())) {
                getJdbcIO().executeQuery(getDefaultDataSource(), queryInsertOne, studentLessonDTO.studentId(), studentLessonDTO.lessonId());
            }
        }
    }

    public List<StudentLessonDTO> getLessonsForStudent(int studentId) throws SQLException {
        String query = querySelectLessonsForStudent.replace("{STUDENT_ID}", String.valueOf(studentId));
        return getJdbcIO().select(getDefaultDataSource(), StudentLessonDTO.newConverter(), query, studentId);
    }
}
