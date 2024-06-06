package cf5.services.dao;

import cf5.dto.StudentLessonDTO;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public final class StudentsLessonsService extends AbstractService<StudentLessonDTO> {
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
    private static final String queryDeleteOne = "DELETE FROM STUDENTS WHERE STUDENT_ID = ? AND LESSON_ID = ?";

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
    public void delete(int studentId, int lessonId) throws SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryDeleteOne, studentId, lessonId);
    }

    public List<StudentLessonDTO> getLessonsForStudent(int studentId) throws SQLException {
        String query = querySelectLessonsForStudent.replace("{STUDENT_ID}", String.valueOf(studentId));
        return getJdbcIO().select(getDefaultDataSource(), StudentLessonDTO.newConverter(), query, studentId);
    }
}
