package cf5.services.dao;

import cf5.dto.LessonDTO;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
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
    private static final String queryCountByName = "SELECT COUNT(*) FROM LESSONS WHERE UPPER(NAME) = UPPER(?)";

    @Override
    public Optional<LessonDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(LessonDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<LessonDTO> getAll() throws SQLException {
        return super.defaultSelectAll(LessonDTO.newConverter(), querySelectAll);
    }
    @Override
    public void insert(LessonDTO lessonDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        if (checkLessonExist(lessonDTO.name())) throw new ValidationException("Lesson already exist");
        super.defaultInsert(lessonDTO, queryInsertOne);
    }
    @Override
    public void update(LessonDTO lessonDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        defaultUpdate(lessonDTO, queryUpdateOne);
    }
    @Override
    public void delete(int id) throws SQLException {
        defaultDelete(id, queryDeleteOne);
    }

    private boolean checkLessonExist(String name) throws SQLException {
        return getJdbcIO().selectNumeric(getDefaultDataSource(), queryCountByName, name).orElse(0L) > 0;
    }
}
