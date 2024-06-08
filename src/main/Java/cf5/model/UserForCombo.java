package cf5.model;

import cf5.dto.UserDTO;
import cf5.utils.Converters;
import org.apache.commons.lang3.StringUtils;

public record UserForCombo(int recId, String fullName) {
    public static UserForCombo convertFrom(UserDTO dto) {
        return new UserForCombo(dto.recId(), Converters.getFullName(dto.firstName(), dto.lastName()));
    }
}
