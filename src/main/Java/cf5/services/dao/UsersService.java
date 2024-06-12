package cf5.services.dao;

import cf5.dto.UserDTO;
import cf5.model.UserForCombo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService extends AbstractService<UserDTO> {
    private static final String querySelectOne = "SELECT * FROM USERS WHERE ID = ?";
    private static final String querySelectAll = "SELECT * FROM USERS";
    private static final String queryInsertOne = "INSERT INTO USERS (USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONE, ROLE_ID, BIRTHDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String queryUpdateOne = "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, FIRSTNAME = ?, LASTNAME = ?, PHONE = ?, EMAIL = ?, ROLE_ID = ?, BIRTHDATE = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM USERS WHERE ID = ?";

    private static final String querySelectByUserName = "SELECT * FROM USERS WHERE USERNAME = ?";
    private static final String queryCountByUserNamePassword = "SELECT COUNT(*) FROM USERS WHERE UPPER(USERNAME) = UPPER(?) AND PASSWORD = ?";
    private static final String querySelectAllTeachers = "SELECT * FROM USERS WHERE ID IN (SELECT USER_ID FROM TEACHERS WHERE VERIFIED = '1')";

    @Override
    public Optional<UserDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(UserDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<UserDTO> getAll() throws SQLException {
        return super.defaultSelectAll(UserDTO.newConverter(), querySelectAll);
    }

    public List<UserForCombo> getAllTeachers() throws SQLException {
        List<UserForCombo> teachersList = Lists.newArrayList();
        List<UserDTO> userDTOs = getJdbcIO().select(getDefaultDataSource(), UserDTO.newConverter(), querySelectAllTeachers);
        if (CollectionUtils.isNotEmpty(userDTOs)) teachersList = userDTOs.stream().map(UserForCombo::convertFrom).toList();
        return teachersList;
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
