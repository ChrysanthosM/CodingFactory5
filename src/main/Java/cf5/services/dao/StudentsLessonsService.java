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
                    "WHERE T.STUDENT_ID = ?";
    private static final String querySelectAll =
            "SELECT T.STUDENT_ID, T.LESSON_ID, L.NAME AS LESSON_NAME " +
                    "FROM STUDENT_LESSONS T " +
                    "LEFT JOIN LESSONS L ON T.LESSON_ID = L.ID";
    private static final String queryInsertOne = "INSERT INTO STUDENT_LESSONS (STUDENT_ID, LESSON_ID) VALUES (?, ?)";
    private static final String queryDeleteOne = "DELETE FROM STUDENTS WHERE STUDENT_ID = ? AND LESSON_ID = ?";

    @Override
    public Optional<StudentLessonDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(StudentLessonDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<StudentLessonDTO> getAll() throws SQLException {
        return super.defaultSelectAll(StudentLessonDTO.newConverter(), querySelectAll);
    }
    @Override
    public void insert(StudentLessonDTO StudentLessonDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryInsertOne, Lists.newArrayList(StudentLessonDTO.studentId(), StudentLessonDTO.lessonId()).toArray());
    }
    public void delete(int studentId, int lessonId) throws SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryDeleteOne, Lists.newArrayList(studentId, lessonId).toArray());
    }
}
