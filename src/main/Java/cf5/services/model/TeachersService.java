package cf5.services.model;

import cf5.dtos.TeacherDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public final class TeachersService extends AbstractService<TeacherDTO> {
    private static final String querySelectOne = "SELECT * FROM TEACHERS WHERE ID = ?";
    private static final String querySelectAll = "SELECT * FROM TEACHERS";
    private static final String queryInsertOne = "INSERT INTO TEACHERS (USER_ID) VALUES (?)";
    private static final String queryUpdateOne = "UPDATE TEACHERS SET USER_ID = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM TEACHERS WHERE ID = ?";

    @Override
    public Optional<TeacherDTO> get(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(TeacherDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<TeacherDTO> getAll() throws SQLException {
        return super.defaultSelectAll(TeacherDTO.newConverter(), querySelectAll);
    }
    @Override
    public void insert(TeacherDTO teacherDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultInsert(teacherDTO, queryInsertOne);
    }
    @Override
    public void update(TeacherDTO teacherDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        defaultUpdate(teacherDTO, queryUpdateOne);
    }
    @Override
    public void delete(TeacherDTO teacherDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        defaultDelete(teacherDTO, queryDeleteOne);
    }
}
