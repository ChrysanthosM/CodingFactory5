package cf5.services.dao;

import cf5.dto.StudentDTO;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsService extends AbstractService<StudentDTO> {
    private static final String querySelectOne =
            "SELECT T.ID, T.USER_ID, U.FIRSTNAME, U.LASTNAME, T.EMAIL, T.PHONE, U.BIRTHDATE " +
                    "FROM STUDENTS T " +
                    "LEFT JOIN USERS U ON T.USER_ID = U.ID " +
                    "WHERE T.ID = ?";
    private static final String querySelectAll =
            "SELECT T.ID, T.USER_ID, U.FIRSTNAME, U.LASTNAME, T.EMAIL, T.PHONE, U.BIRTHDATE " +
                    "FROM STUDENTS T " +
                    "LEFT JOIN USERS U ON T.USER_ID = U.ID";
    private static final String queryInsertOne = "INSERT INTO STUDENTS (USER_ID, PHONE, EMAIL) VALUES (?, ?, ?)";
    private static final String queryUpdateOne = "UPDATE STUDENTS SET USER_ID = ?, PHONE = ?, EMAIL = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM STUDENTS WHERE ID = ?";
    private static final String queryCountByName = "SELECT COUNT(*) FROM STUDENTS WHERE USER_ID = ?";

    private static final String querySelectStudentsForLesson =
            "SELECT T.ID, T.USER_ID, U.FIRSTNAME, U.LASTNAME, T.EMAIL, T.PHONE, U.BIRTHDATE " +
                    "FROM STUDENTS T " +
                    "JOIN USERS U ON T.USER_ID = U.ID " +
                    "WHERE T.USER_ID IN (SELECT STUDENT_ID FROM STUDENT_LESSONS WHERE LESSON_ID = ?)";

    @Override
    public Optional<StudentDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(StudentDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<StudentDTO> getAll() throws SQLException {
        return super.defaultSelectAll(StudentDTO.newConverter(), querySelectAll);
    }
    public List<StudentDTO> getStudentsForLesson(int lessonId) throws SQLException {
        return getJdbcIO().select(getDefaultDataSource(), StudentDTO.newConverter(), querySelectStudentsForLesson, lessonId);
    }

    @Override
    public void insert(StudentDTO studentDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        if (checkStudentExist(studentDTO.userId())) throw new ValidationException("Student already exist");
        getJdbcIO().executeQuery(getDefaultDataSource(), queryInsertOne, Lists.newArrayList(studentDTO.userId(), studentDTO.phone(), studentDTO.email()).toArray());
    }
    @Override
    public void update(StudentDTO studentDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryUpdateOne, Lists.newArrayList(studentDTO.userId(), studentDTO.phone(), studentDTO.email(), studentDTO.recId()).toArray());
    }
    @Override
    public void delete(int id) throws SQLException {
        defaultDelete(id, queryDeleteOne);
    }

    private boolean checkStudentExist(int userId) throws SQLException {
        return getJdbcIO().selectNumeric(getDefaultDataSource(), queryCountByName, userId).orElse(0L) > 0;
    }
}
