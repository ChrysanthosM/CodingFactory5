package cf5.services.dao;

import cf5.dto.LessonDTO;
import cf5.dto.TeacherDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public final class LessonsService extends AbstractService<LessonDTO> {
    private static final String querySelectOne = "SELECT * FROM LESSONS WHERE ID = ?";
    private static final String querySelectAll = "SELECT * FROM LESSONS";
    private static final String queryInsertOne = "INSERT INTO LESSONS (NAME) VALUES (?)";
    private static final String queryUpdateOne = "UPDATE LESSONS SET NAME = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM LESSONS WHERE ID = ?";

    @Override
    public Optional<LessonDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(LessonDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<LessonDTO> getAll() throws SQLException {
        return super.defaultSelectAll(LessonDTO.newConverter(), querySelectAll);
    }
    @Override
    public void insert(LessonDTO teacherDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultInsert(teacherDTO, queryInsertOne);
    }
    @Override
    public void update(LessonDTO teacherDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        defaultUpdate(teacherDTO, queryUpdateOne);
    }
    @Override
    public void delete(int id) throws SQLException {
        defaultDelete(id, queryDeleteOne);
    }
}
