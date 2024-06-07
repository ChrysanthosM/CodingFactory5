package cf5.services.dao;

import cf5.dto.ClassRoomDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService extends AbstractService<ClassRoomDTO> {
    private static final String querySelectOne = "SELECT * FROM CLASSROOM WHERE ID = ?";
    private static final String querySelectAll = "SELECT * FROM CLASSROOM";
    private static final String queryInsertOne = "INSERT INTO CLASSROOM (NAME, TEACHER_ID, LESSON_ID) VALUES (?, ?, ?)";
    private static final String queryUpdateOne = "UPDATE CLASSROOM SET NAME = ?, TEACHER_ID = ?, LESSON_ID = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM CLASSROOM WHERE ID = ?";

    @Override
    public Optional<ClassRoomDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(ClassRoomDTO.newConverter(), querySelectOne, keyValues);
    }

    @Override
    public List<ClassRoomDTO> getAll() throws SQLException {
        return super.defaultSelectAll(ClassRoomDTO.newConverter(), querySelectAll);
    }

    @Override
    public void insert(ClassRoomDTO classRoomDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultInsert(ClassRoomDTO.newConverter(), queryInsertOne);
    }

    @Override
    public void update(ClassRoomDTO classRoomDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultUpdate(ClassRoomDTO.newConverter(), queryUpdateOne);
    }

    @Override
    public void delete(int id) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultDelete(id, queryDeleteOne);
    }
}
