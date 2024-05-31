package cf5.model;

import cf5.dto.TeacherDTO;
import org.checkerframework.checker.index.qual.NonNegative;

public record Teacher(@NonNegative int recId,
                      int USER_ID) {
    public static Teacher convertFrom(TeacherDTO dto) {
        return new Teacher(dto.recId(), dto.USER_ID());
    }
}
