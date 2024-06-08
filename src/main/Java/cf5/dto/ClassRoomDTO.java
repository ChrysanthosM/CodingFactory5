package cf5.dto;

import cf5.db.loader.RowLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public record ClassRoomDTO(int recId, String name, int teacherUserId, String teacherFirstName, String teacherLastName, int lessonId, String lessonName) {
    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<ClassRoomDTO> {
        @Override
        public ClassRoomDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new ClassRoomDTO(
                    resultSet.getInt("ID"),
                    resultSet.getString("NAME"),
                    resultSet.getInt("TEACHER_USER_ID"),
                    resultSet.getString("TEACHER_FIRSTNAME"),
                    resultSet.getString("TEACHER_LASTNAME"),
                    resultSet.getInt("LESSON_ID"),
                    resultSet.getString("LESSON_NAME")
            );
        }
        @Override
        public ClassRoomDTO convertResultSet(List<Map.Entry<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new ClassRoomDTO(
                    RowLoader.extractValue(columnNamesValues, "ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "NAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "TEACHER_USER_ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "TEACHER_FIRSTNAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "TEACHER_LASTNAME", String.class),
                    RowLoader.extractValue(columnNamesValues, "LESSON_ID", Integer.class),
                    RowLoader.extractValue(columnNamesValues, "LESSON_NAME", String.class)
            );
        }
    }
}
