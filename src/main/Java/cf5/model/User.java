package cf5.model;

import cf5.dto.UserDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.checkerframework.checker.index.qual.NonNegative;

public record User(@NonNegative int recId,
                   @NotBlank @Size(min = 10, max = 50, message="Username must be between 10 and 50 characters...") String userName,
                   @NotBlank @Size(min = 10, max = 128, message="Password must be between 10 and 128 characters...") String password,
                   @NotBlank @Size(min = 2, max = 70, message = "FirstName must be between 10 and 70 characters...") String firstName,
                   @NotBlank @Size(min = 2, max = 70, message = "LastName must be between 10 and 70 characters...") String lastName,
                   @NotBlank @Email String email) {
    public static User convertFrom(UserDTO dto) {
        return new User(dto.recId(), dto.userName(), dto.password(), dto.firstName(), dto.lastName(), dto.email());
    }
}
