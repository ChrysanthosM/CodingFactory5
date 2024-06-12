package cf5.model;

import cf5.dto.LessonDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;

public record Lesson(@NonNegative int recId,
                     @NotBlank @Size(min = 1, max = 100, message = "Lesson Name must be between 1 and 100 characters...") String name) {
    public static Lesson getEmpty() { return new Lesson(0, StringUtils.EMPTY); }
    public static Lesson convertFrom(LessonDTO dto) {
        return new Lesson(dto.recId(), dto.name());
    }

    public LessonDTO toDTO() { return new LessonDTO(recId, name); }
}
