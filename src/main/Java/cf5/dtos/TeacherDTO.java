package cf5.dtos;

import cf5.db.loader.RowLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public record TeacherDTO(int recId, int USER_ID) {
    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<TeacherDTO> {
        @Override
        public TeacherDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new TeacherDTO(
                    resultSet.getInt("ID"),
                    resultSet.getInt("USER_ID")
            );
        }
        @Override
        public TeacherDTO convertResultSet(List<Map.Entry<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new TeacherDTO(
                    RowLoader.extractValue(columnNamesValues, "ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "USER_ID", Integer.class)
            );
        }
    }
}
