package cf5.model;

import cf5.dto.TeacherDTO;
import org.apache.commons.lang3.StringUtils;

public record Teachers(int recId,
                       int userId,
                       String firstName,
                       String lastName,
                       String phone,
                       String email) {
    public static Teachers getEmpty() { return new Teachers(0, 0, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY); }
    public static Teachers convertFrom(TeacherDTO dto) {
        return new Teachers(dto.recId(), dto.userId(), dto.firstName(), dto.lastName(), dto.phone(), dto.email());
    }

    public TeacherDTO toDTO() { return new TeacherDTO(recId, userId, firstName, lastName, phone, email); };
}
