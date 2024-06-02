package cf5.model;

import cf5.dto.RoleDTO;
import jakarta.validation.constraints.*;
import org.checkerframework.checker.index.qual.NonNegative;

public record Role(@NonNegative int roleId,
                   @NotBlank @Size(min = 2, max = 20, message = "Description must be between 2 and 20 characters...") String roleDescription) {
    public static Role convertFrom(RoleDTO dto) {
        return new Role(dto.recId(), dto.description());
    }
}
