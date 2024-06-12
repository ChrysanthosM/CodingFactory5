package cf5.services.dao;

import cf5.dto.TeacherDTO;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class TeachersService extends AbstractService<TeacherDTO> {
    private static final String querySelectOne =
            "SELECT T.ID, T.USER_ID, U.FIRSTNAME, U.LASTNAME, T.EMAIL, T.PHONE, T.VERIFIED " +
                    "FROM TEACHERS T " +
                    "LEFT JOIN USERS U ON T.USER_ID = U.ID " +
                    "WHERE T.ID = ?";
    private static final String querySelectByUserId =
            "SELECT T.ID, T.USER_ID, U.FIRSTNAME, U.LASTNAME, T.EMAIL, T.PHONE, T.VERIFIED " +
                    "FROM TEACHERS T " +
                    "LEFT JOIN USERS U ON T.USER_ID = U.ID " +
                    "WHERE T.USER_ID = ?";
    private static final String querySelectAll =
            "SELECT T.ID, T.USER_ID, U.FIRSTNAME, U.LASTNAME, T.EMAIL, T.PHONE, T.VERIFIED " +
                    "FROM TEACHERS T " +
                    "LEFT JOIN USERS U ON T.USER_ID = U.ID";
    private static final String queryInsertOne = "INSERT INTO TEACHERS (USER_ID, PHONE, EMAIL, VERIFIED) VALUES (?, ?, ?, ?)";
    private static final String queryUpdateOne = "UPDATE TEACHERS SET USER_ID = ?, PHONE = ?, EMAIL = ?, VERIFIED = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM TEACHERS WHERE ID = ?";
    private static final String queryCountByName = "SELECT COUNT(*) FROM TEACHERS WHERE USER_ID = ?";


    @Override
    public Optional<TeacherDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(TeacherDTO.newConverter(), querySelectOne, keyValues);
    }
    public Optional<TeacherDTO> findByUserId(int userId) throws SQLException {
        return getJdbcIO().selectOne(getDefaultDataSource(), TeacherDTO.newConverter(), querySelectByUserId, userId);
    }
    @Override
    public List<TeacherDTO> getAll() throws SQLException {
        return super.defaultSelectAll(TeacherDTO.newConverter(), querySelectAll);
    }

    @Override
    public void insert(TeacherDTO teacherDTO) throws SQLException {
        if (checkTeacherExist(teacherDTO.userId())) throw new ValidationException("Teacher already exist");
        getJdbcIO().executeQuery(getDefaultDataSource(), queryInsertOne, Lists.newArrayList(teacherDTO.userId(), teacherDTO.phone(), teacherDTO.email(), teacherDTO.verified()).toArray());
    }
    @Override
    public void update(TeacherDTO teacherDTO) throws SQLException {
        getJdbcIO().executeQuery(getDefaultDataSource(), queryUpdateOne, Lists.newArrayList(teacherDTO.userId(), teacherDTO.phone(), teacherDTO.email(), teacherDTO.verified(),
                teacherDTO.recId()).toArray());
    }
    @Override
    public void delete(int id) throws SQLException {
        defaultDelete(id, queryDeleteOne);
    }

    private boolean checkTeacherExist(int userId) throws SQLException {
        return getJdbcIO().selectNumeric(getDefaultDataSource(), queryCountByName, userId).orElse(0L) > 0;
    }
}
