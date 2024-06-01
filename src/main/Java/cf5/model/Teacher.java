package cf5.model;

import cf5.dto.TeacherDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;

public record Teacher(@NonNegative int recId, @NonNegative int userId,
                      @NotBlank @Size(min = 10, max = 10, message = "Phone must be 10 characters...") @Pattern(regexp = "\\d+", message = "Phone must contain only digits") String phone,
                      @NotBlank @Email String email) {
    public static Teacher getEmpty() { return new Teacher(0, 0, StringUtils.EMPTY, StringUtils.EMPTY); }
    public static Teacher convertFrom(TeacherDTO dto) {
        return new Teacher(dto.recId(), dto.userId(), dto.phone(), dto.email());
    }

    public TeacherDTO toDTO() { return new TeacherDTO(recId, userId, StringUtils.EMPTY, StringUtils.EMPTY, phone, email); };
}
