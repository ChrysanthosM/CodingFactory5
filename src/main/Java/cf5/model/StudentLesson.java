package cf5.model;

import cf5.dto.StudentLessonDTO;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;

public record StudentLesson(@NonNegative int studentId, @NonNegative int lessonId,
                            String lessonName,
                            @Pattern(regexp = "[01]", message = "Value must be '0' or '1'") String selected) {
    public static StudentLesson getEmpty() { return new StudentLesson(0, 0, StringUtils.EMPTY, StringUtils.EMPTY); }
    public static StudentLesson convertFrom(StudentLessonDTO dto) {
        return new StudentLesson(dto.studentId(), dto.lessonId(), dto.lessonName(), "0");
    }

    public StudentLessonDTO toDTO() { return new StudentLessonDTO(studentId, lessonId, lessonName); };
}
