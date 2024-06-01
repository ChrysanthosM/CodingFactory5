package cf5.model;

import cf5.dto.StudentDTO;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;

import java.util.Date;

public record Student(@NonNegative int recId,
                      @NotBlank @Size(min = 2, max = 70, message = "FirstName must be between 10 and 70 characters...") String firstName,
                      @NotBlank @Size(min = 2, max = 70, message = "LastName must be between 10 and 70 characters...") String lastName,
                      @Nonnull @Past(message = "Birth date must be in the past") Date birthDate,
                      @NotBlank @Size(min = 10, max = 10, message = "Phone must be 10 characters...") @Pattern(regexp = "\\d+", message = "Phone must contain only digits") String phone,
                      @NotBlank @Email String email) {
    public static Student getEmpty() { return new Student(0, StringUtils.EMPTY, StringUtils.EMPTY, new Date(), StringUtils.EMPTY, StringUtils.EMPTY); }
    public static Student convertFrom(StudentDTO dto) {
        return new Student(dto.recId(), dto.firstName(), dto.lastName(), dto.birthDate(), dto.phone(), dto.email());
    }

    public StudentDTO toDTO() { return new StudentDTO(recId, firstName, lastName, birthDate, phone, email); };
}
