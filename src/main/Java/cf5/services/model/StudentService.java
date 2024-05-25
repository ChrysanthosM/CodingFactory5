package cf5.services.model;

import cf5.dtos.StudentDTO;
import cf5.dtos.TeacherDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public final class StudentService extends AbstractService<StudentDTO> {
    private static final String querySelectOne = "SELECT * FROM STUDENTS WHERE ID = ?";
    private static final String querySelectAll = "SELECT * FROM STUDENTS";
    private static final String queryInsertOne = "INSERT INTO STUDENTS (USER_ID) VALUES (?)";
    private static final String queryUpdateOne = "UPDATE STUDENTS SET USER_ID = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM STUDENTS WHERE ID = ?";

    @Override
    public Optional<StudentDTO> get(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(StudentDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<StudentDTO> getAll() throws SQLException {
        return super.defaultSelectAll(StudentDTO.newConverter(), querySelectAll);
    }
    @Override
    public void insert(StudentDTO studentDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultInsert(studentDTO, queryInsertOne);
    }
    @Override
    public void update(StudentDTO studentDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        defaultUpdate(studentDTO, queryUpdateOne);
    }
    @Override
    public void delete(StudentDTO studentDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        defaultDelete(studentDTO, queryDeleteOne);
    }
}
