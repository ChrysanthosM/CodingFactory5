package cf5.dto;

import cf5.db.loader.RowLoader;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public record LessonDTO(int recId, String name) {
    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<LessonDTO> {
        @Override
        public LessonDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new LessonDTO(
                    resultSet.getInt("ID"),
                    resultSet.getString("NAME")
            );
        }
        @Override
        public LessonDTO convertResultSet(List<Map.Entry<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new LessonDTO(
                    RowLoader.extractValue(columnNamesValues, "ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "NAME", String.class)
            );
        }
    }
}
