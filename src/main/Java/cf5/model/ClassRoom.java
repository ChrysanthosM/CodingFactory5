package cf5.model;

import cf5.dto.ClassRoomDTO;
import cf5.dto.StudentLessonDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;

public record ClassRoom(@NonNegative int classRoomId,
                        @NotBlank @Size(max = 50, message="Name must have max 50 characters...") String classRoomName,
                        @NonNegative int teacherId,
                        @NonNegative int lessonId) {
    public static ClassRoom getEmpty() { return new ClassRoom(0, StringUtils.EMPTY, 0, 0); }
    public static ClassRoom convertFrom(ClassRoomDTO dto) {
        return new ClassRoom(dto.recId() , dto.name(), dto.teacherId(), dto.lessonId());
    }

    public ClassRoomDTO toDTO() { return new ClassRoomDTO(classRoomId, classRoomName, teacherId, lessonId); };
}
