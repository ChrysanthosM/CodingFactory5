package cf5.services.dao;

import cf5.dto.TeacherDTO;
import cf5.utils.RecordUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class TeachersService extends AbstractService<TeacherDTO> {
    private static final String querySelectOne =
            "SELECT T.ID, T.USER_ID, U.FIRSTNAME, U.LASTNAME, T.EMAIL, T.PHONE " +
            "FROM TEACHERS T " +
            "LEFT JOIN USERS U ON T.USER_ID = U.ID " +
            "WHERE T.ID = ?";
    private static final String querySelectAll =
            "SELECT T.ID, T.USER_ID, U.FIRSTNAME, U.LASTNAME, T.EMAIL, T.PHONE " +
            "FROM TEACHERS T " +
            "LEFT JOIN USERS U ON T.USER_ID = U.ID";
    private static final String queryInsertOne = "INSERT INTO TEACHERS (USER_ID, EMAIL, PHONE) VALUES (?, ?, ?)";
    private static final String queryUpdateOne = "UPDATE TEACHERS SET USER_ID = ?, EMAIL = ?, PHONE = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM TEACHERS WHERE ID = ?";

    @Override
    public Optional<TeacherDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(TeacherDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<TeacherDTO> getAll() throws SQLException {
        return super.defaultSelectAll(TeacherDTO.newConverter(), querySelectAll);
    }
    @Override
    public void insert(TeacherDTO teacherDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryInsertOne, Lists.newArrayList(teacherDTO.userId(), teacherDTO.email(), teacherDTO.phone()).toArray());
    }
    @Override
    public void update(TeacherDTO teacherDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryUpdateOne, Lists.newArrayList(teacherDTO.userId(), teacherDTO.email(), teacherDTO.phone(), teacherDTO.recId()).toArray());
    }
    @Override
    public void delete(int id) throws SQLException {
        defaultDelete(id, queryDeleteOne);
    }
}
