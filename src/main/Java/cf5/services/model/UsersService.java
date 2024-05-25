package cf5.services.model;

import cf5.Utils.RecordUtils;
import cf5.dtos.UserDTO;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public final class UsersService extends AbstractService<UserDTO> {
    private static final String querySelectOne = "SELECT * FROM USERS WHERE ID = ?";
    private static final String querySelectAll = "SELECT * FROM USERS";
    private static final String queryInsertOne = "INSERT INTO USERS (USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, BIRTHDATE) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String queryUpdateOne = "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, BIRTHDATE = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM USERS WHERE ID = ?";

    private static final String querySelectByUserName = "SELECT * FROM USERS WHERE USERNAME = ?";

    @Override
    public Optional<UserDTO> get(Object... keyValues) throws SQLException {
        return getJdbcIO().selectOne(getDefaultDataSource(), UserDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<UserDTO> getAll() throws SQLException {
        return getJdbcIO().select(getDefaultDataSource(), UserDTO.newConverter(), querySelectAll);
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
    public void delete(UserDTO userDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultDelete(userDTO, queryDeleteOne);
    }

    public Optional<UserDTO> getByUserName(Object... keyValues) throws SQLException {
        return getJdbcIO().selectOne(getDefaultDataSource(), UserDTO.newConverter(), querySelectByUserName, keyValues);
    }
}
