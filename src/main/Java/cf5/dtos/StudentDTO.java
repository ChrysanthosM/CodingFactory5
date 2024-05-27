package cf5.dtos;

import cf5.db.loader.RowLoader;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public record StudentDTO(int recId,
                         @Size(min = 2, max = 70, message = "FirstName must be between 10 and 70 characters...") String firstName,
                         @Size(min = 2, max = 70, message = "LastName must be between 10 and 70 characters...") String lastName,
                         @Past(message = "Birth date must be in the past") Date birthDate,
                         @Size(min = 10, max = 10, message = "Phone must be 10 characters...") @Pattern(regexp = "\\d+", message = "Phone must contain only digits") String phone,
                         @Email String email) {
    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<StudentDTO> {
        @Override
        public StudentDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new StudentDTO(
                    resultSet.getInt("ID"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getDate("BIRTHDATE"),
                    resultSet.getString("PHONE"),
                    resultSet.getString("EMAIL")
            );
        }
        @Override
        public StudentDTO convertResultSet(List<Map.Entry<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new StudentDTO(
                    RowLoader.extractValue(columnNamesValues, "ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "FIRSTNAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "LASTNAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "BIRTHDATE", Date.class),
                    RowLoader.extractValue(columnNamesValues, "PHONE", String.class),
                    RowLoader.extractValue(columnNamesValues, "EMAIL", String.class)
            );
        }
    }
    public static StudentDTO getEmpty() { return  new StudentDTO(0, StringUtils.EMPTY, StringUtils.EMPTY, new Date(), StringUtils.EMPTY, StringUtils.EMPTY); }
}
