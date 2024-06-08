package cf5.model;

import cf5.dto.ClassRoomDTO;
import cf5.dto.StudentLessonDTO;
import cf5.utils.Converters;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;

public record ClassRoom(@NonNegative int recId,
                        @NotBlank @Size(max = 50, message="Name must have max 50 characters...") String classRoomName,
                        @NonNegative int teacherId, String teacherName,
                        @NonNegative int lessonId, String lessonName) {
    public static ClassRoom getEmpty() { return new ClassRoom(0, StringUtils.EMPTY, 0, StringUtils.EMPTY, 0, StringUtils.EMPTY); }
    public static ClassRoom convertFrom(ClassRoomDTO dto) {
        return new ClassRoom(dto.recId() , dto.name(), dto.teacherId(), Converters.getFullName(dto.teacherFirstName(), dto.teacherLastName()), dto.lessonId(), dto.lessonName());
    }

    public ClassRoomDTO toDTO() { return new ClassRoomDTO(recId, classRoomName, teacherId, null, null, lessonId, lessonName); };
}
