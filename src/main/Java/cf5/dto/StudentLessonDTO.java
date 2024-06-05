package cf5.dto;

import cf5.db.loader.RowLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public record StudentLessonDTO(int studentId, int lessonId, String lessonName) {
    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<StudentLessonDTO> {
        @Override
        public StudentLessonDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new StudentLessonDTO(
                    resultSet.getInt("STUDENT_ID"),
                    resultSet.getInt("LESSON_ID"),
                    resultSet.getString("LESSON_NAME")
            );
        }
        @Override
        public StudentLessonDTO convertResultSet(List<Map.Entry<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new StudentLessonDTO(
                    RowLoader.extractValue(columnNamesValues, "STUDENT_ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "LESSON_ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "LESSON_NAME", String.class)
            );
        }
    }
}
