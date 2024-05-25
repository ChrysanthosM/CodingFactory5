package cf5.services.model;

import cf5.dtos.TeacherDTO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public final class TeachersService extends AbstractService<TeacherDTO> {
    private static final String querySelectOne = "SELECT * FROM Teachers WHERE ID = ?";
    private static final String querySelectAll = "SELECT * FROM Teachers";
    private static final String queryInsertOne = "INSERT INTO Teachers (USER_ID) VALUES (?)";
    private static final String queryUpdateOne = "UPDATE Teachers SET USER_ID = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM Teachers WHERE ID = ?";

    @Override
    public Optional<TeacherDTO> get(Object... keyValues) throws SQLException {
        return getJdbcIO().selectOne(getDefaultDataSource(), TeacherDTO.newConverter(), querySelectOne, keyValues);
    }

    @Override
    public List<TeacherDTO> getAll() throws SQLException {
        return getJdbcIO().select(getDefaultDataSource(), TeacherDTO.newConverter(), querySelectAll);
    }

    @Override
    public void insert(TeacherDTO teacherDTO) throws SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryInsertOne, teacherDTO.USER_ID());
    }

    @Override
    public void update(TeacherDTO teacherDTO) throws SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryUpdateOne, teacherDTO.USER_ID());
    }

    @Override
    public void delete(TeacherDTO teacherDTO) throws SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryDeleteOne, teacherDTO.recId());
    }
}
