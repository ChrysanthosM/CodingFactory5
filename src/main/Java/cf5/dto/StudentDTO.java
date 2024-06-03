package cf5.dto;

import cf5.db.loader.RowLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public record StudentDTO(int recId, int userId, String firstName, String lastName, String phone, String email, Date birthDate) {
    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<StudentDTO> {
        @Override
        public StudentDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new StudentDTO(
                    resultSet.getInt("ID"),
                    resultSet.getInt("USER_ID"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("PHONE"),
                    resultSet.getString("EMAIL"),
                    resultSet.getDate("BIRTHDATE")
            );
        }
        @Override
        public StudentDTO convertResultSet(List<Map.Entry<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new StudentDTO(
                    RowLoader.extractValue(columnNamesValues, "ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "USER_ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "FIRSTNAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "LASTNAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "PHONE", String.class),
                    RowLoader.extractValue(columnNamesValues, "EMAIL", String.class),
                    RowLoader.extractValue(columnNamesValues, "BIRTHDATE", Date.class)
            );
        }
    }
}
