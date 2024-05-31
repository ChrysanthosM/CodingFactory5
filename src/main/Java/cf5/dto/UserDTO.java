package cf5.dto;

import cf5.db.loader.RowLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public record UserDTO(int recId, String userName, String password, String firstName, String lastName, String email) {
    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<UserDTO> {
        @Override
        public UserDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new UserDTO(
                    resultSet.getInt("ID"),
                    resultSet.getString("USERNAME"),
                    resultSet.getString("PASSWORD"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("EMAIL")
            );
        }
        @Override
        public UserDTO convertResultSet(List<Map.Entry<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new UserDTO(
                    RowLoader.extractValue(columnNamesValues, "ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "USERNAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "PASSWORD", String.class),
                    RowLoader.extractValue(columnNamesValues, "FIRSTNAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "LASTNAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "EMAIL", String.class)
            );
        }
    }
}
