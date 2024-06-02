package cf5.services.dao;

import cf5.dto.RoleDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public final class RolesService extends AbstractService<RoleDTO> {
    private static final String querySelectOne = "SELECT * FROM ROLES WHERE ID = ?";
    private static final String querySelectAll = "SELECT * FROM ROLES";
    private static final String queryInsertOne = "INSERT INTO ROLES (DESCRIPTION) VALUES (?)";
    private static final String queryUpdateOne = "UPDATE ROLES SET DESCRIPTION = ? WHERE ID = ?";
    private static final String queryDeleteOne = "DELETE FROM ROLES WHERE ID = ?";

    private static final String querySelectForSignUp = "SELECT * FROM ROLES WHERE ID > 0";

    @Override
    public Optional<RoleDTO> findByKeys(Object... keyValues) throws SQLException {
        return super.defaultSelectOne(RoleDTO.newConverter(), querySelectOne, keyValues);
    }
    @Override
    public List<RoleDTO> getAll() throws SQLException {
        return super.defaultSelectAll(RoleDTO.newConverter(), querySelectAll);
    }
    @Override
    public void insert(RoleDTO RoleDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        super.defaultInsert(RoleDTO, queryInsertOne);
    }
    @Override
    public void update(RoleDTO RoleDTO) throws SQLException, InvocationTargetException, IllegalAccessException {
        defaultUpdate(RoleDTO, queryUpdateOne);
    }
    @Override
    public void delete(int id) throws SQLException {
        defaultDelete(id, queryDeleteOne);
    }

    public List<RoleDTO> getForSignUp() throws SQLException {
        return getJdbcIO().select(getDefaultDataSource(), RoleDTO.newConverter(), querySelectForSignUp);
    }
}
