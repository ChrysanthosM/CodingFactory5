package cf5.model;

import cf5.dto.UserDTO;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;

public record User(@NonNegative int recId,
                   @NotBlank @Size(min = 10, max = 50, message="Username must be between 10 and 50 characters...") String username,
                   @NotBlank @Size(min = 10, max = 128, message="Password must be between 10 and 128 characters...") String password,
                   @NotBlank @Size(min = 10, max = 128, message="Confirm Password must be between 10 and 128 characters...") String confirmPassword,
                   @NotBlank @Size(min = 2, max = 70, message = "FirstName must be between 10 and 70 characters...") String firstName,
                   @NotBlank @Size(min = 2, max = 70, message = "LastName must be between 10 and 70 characters...") String lastName,
                   @NotBlank @Email String email,
                   @NotBlank @Size(min = 10, max = 10, message = "Phone must be 10 characters...") @Pattern(regexp = "\\d+", message = "Phone must contain only digits") String phone,
                   @Min(value = 1, message = "Invalid Role") @Max(value = 2, message = "Invalid Role") int roleId) {
    public static User getEmpty() { return new User(0, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, 2); }
    public static User convertFrom(UserDTO dto) {
        return new User(dto.recId(), dto.userName(), dto.password(), dto.password(), dto.firstName(), dto.lastName(), dto.email(), dto.phone(), dto.roleId());
    }

    public UserDTO toDTO() { return new UserDTO(recId, username, password, firstName, lastName, email, phone, roleId); };
}
