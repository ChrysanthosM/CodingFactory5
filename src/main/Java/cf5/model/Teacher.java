package cf5.model;

import cf5.dto.TeacherDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;
import org.hibernate.validator.constraints.Range;

public record Teacher(@NonNegative int recId, @NonNegative int userId,
                      String firstName, String lastName,
                      @NotBlank @Size(min = 10, max = 10, message = "Phone must be 10 characters...") @Pattern(regexp = "\\d+", message = "Phone must contain only digits") String phone,
                      @NotBlank @Email String email,
                      @Pattern(regexp = "[01]", message = "Value must be '0' or '1'") String verified) {
    public static Teacher getEmpty() { return new Teacher(0, 0, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, "0"); }
    public static Teacher convertFrom(TeacherDTO dto) {
        return new Teacher(dto.recId(), dto.userId(), dto.firstName(), dto.lastName(), dto.phone(), dto.email(), dto.verified());
    }

    public Teacher {
        if (verified == null) verified = StringUtils.defaultString("0");
    }

    public TeacherDTO toDTO() { return new TeacherDTO(recId, userId, firstName, lastName, phone, email, verified); };
}
