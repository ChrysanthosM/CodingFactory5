package cf5.services.dao;

import cf5.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public final class UsersService extends AbstractService<UserDTO> {
    private static final String querySelectOne = "SELECT * FROM USERS WHERE ID = ?";
    private static final String querySelectAll = "SELECT * FROM USERS";
    private static final String queryInsertOne = "INSERT INTO USERS (USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL) VALUES (?, ?, ?, ?, ?)";
    private static final String queryUpdateOne = "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, FIRSTNAME = ?, LASTNAME = ?, EMAIL = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM USERS WHERE ID = ?";

    private static final String querySelectByUserName = "SELECT * FROM USERS WHERE USERNAME = ?";
    private static final String queryCountByUserNamePassword = "SELECT COUNT(*) FROM USERS WHERE UPPER(USERNAME) = UPPER(?) AND PASSWORD = ?";

    @Override
    public Optional<UserDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(UserDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<UserDTO> getAll() throws SQLException {
        return super.defaultSelectAll(UserDTO.newConverter(), querySelectAll);
    }
    @Override
    public void insert(UserDTO userDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultInsert(userDTO, queryInsertOne);
    }
    @Override
    public void update(UserDTO userDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultUpdate(userDTO, queryUpdateOne);
    }
    @Override
    public void delete(int id) throws SQLException {
        super.defaultDelete(id, queryDeleteOne);
    }

    public Optional<UserDTO> getByUserName(Object... keyValues) throws SQLException {
        return getJdbcIO().selectOne(getDefaultDataSource(), UserDTO.newConverter(), querySelectByUserName, keyValues);
    }
    public Long countByUserNamePassword(Object... keyValues) throws SQLException {
        return getJdbcIO().selectNumeric(getDefaultDataSource(), queryCountByUserNamePassword, keyValues).orElse(0L);
    }
}
