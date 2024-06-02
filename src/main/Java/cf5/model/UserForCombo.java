package cf5.model;

import cf5.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;

public record UserForCombo(int recId, String fullName) {
    public static UserForCombo convertFrom(UserDTO dto) {
        return new UserForCombo(dto.recId(), getFullName(dto.firstName(), dto.lastName()));
    }

    private static String getFullName(String firstName, String lastName) {
        return StringUtils.trimToEmpty(firstName).concat(StringUtils.SPACE).concat(StringUtils.trimToEmpty(lastName)).trim();
    }
}
