package cf5.model;

import cf5.dto.TeacherDTO;
import org.checkerframework.checker.index.qual.NonNegative;

public record Teacher(@NonNegative int recId,
                      int userId) {
    public static Teacher getEmpty() { return new Teacher(0, 0); }
    public static Teacher convertFrom(TeacherDTO dto) {
        return new Teacher(dto.recId(), dto.userId());
    }

    public TeacherDTO toDTO() { return new TeacherDTO(recId, userId); };
}
