package cf5.model;

import cf5.dto.StudentDTO;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;

import java.util.Date;

public record Student(@NonNegative int recId,  @NonNegative int userId,
                      String firstName, String lastName,
                      @NotBlank @Size(min = 10, max = 10, message = "Phone must be 10 characters...") @Pattern(regexp = "\\d+", message = "Phone must contain only digits") String phone,
                      @NotBlank @Email String email,
                      Date birthDate) {
    public static Student getEmpty() { return new Student(0, 0, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, new Date()); }
    public static Student convertFrom(StudentDTO dto) {
        return new Student(dto.recId(), dto.userId(), dto.firstName(), dto.lastName(), dto.phone(), dto.email(), dto.birthDate());
    }

    public StudentDTO toDTO() { return new StudentDTO(recId, userId, firstName, lastName, phone, email, new Date()); };
}
