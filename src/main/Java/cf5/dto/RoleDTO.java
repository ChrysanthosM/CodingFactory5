package cf5.dto;

import cf5.db.loader.RowLoader;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public record RoleDTO(int recId, String description) {
    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<RoleDTO> {
        @Override
        public RoleDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new RoleDTO(
                    resultSet.getInt("ID"),
                    resultSet.getString("DESCRIPTION")
            );
        }
        @Override
        public RoleDTO convertResultSet(List<Map.Entry<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new RoleDTO(
                    RowLoader.extractValue(columnNamesValues, "ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "DESCRIPTION", String.class)
            );
        }
    }

    @Getter @AllArgsConstructor
    public enum UserRole {
        ADMIN(0), TEACHER(1), STUDENT(2);
        private final int roleId;
    }
}
