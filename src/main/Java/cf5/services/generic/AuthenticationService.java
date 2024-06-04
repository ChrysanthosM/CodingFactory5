package cf5.services.generic;

import cf5.dto.RoleDTO;
import cf5.dto.StudentDTO;
import cf5.dto.TeacherDTO;
import cf5.services.dao.StudentsService;
import cf5.services.dao.TeachersService;
import cf5.utils.PasswordUtils;
import cf5.dto.UserDTO;
import cf5.services.dao.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {
    private @Autowired UsersService usersService;
    private @Autowired TeachersService teachersService;
    private @Autowired StudentsService studentsService;

    public String authenticateSignUp(@Nonnull String username, @Nonnull String password, @Nonnull String confirmPassword) throws SQLException {
        if (!password.equals(confirmPassword)) return "Password and confirmPassword not same! Please try again";

        Optional<UserDTO> userDTO = usersService.getByUserName(username);
        if (userDTO.isPresent()) return "UserName already exist!";

        return StringUtils.EMPTY;
    }

    @Transactional
    public void insertNewUser(@Nonnull String username, @Nonnull String password,
                              @Nonnull String firstName, @Nonnull String lastName, @Nonnull String email, @Nonnull String phone,
                              int roleId, @Nonnull Date birthDate) throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException {
        usersService.insert(new UserDTO(0, username, PasswordUtils.hashPassword(password), firstName, lastName, email, phone, roleId, birthDate));
        if (roleId == RoleDTO.UserRole.TEACHER.getRoleId() || roleId == RoleDTO.UserRole.STUDENT.getRoleId()) {
            UserDTO userDTO = usersService.getByUserName(username).orElseThrow();
            if (roleId == RoleDTO.UserRole.TEACHER.getRoleId()) {
                teachersService.insert(new TeacherDTO(0, userDTO.recId(), null, null, phone, email, "0"));
            } else {
                studentsService.insert(new StudentDTO(0, userDTO.recId(), null, null, phone, email, null));
            }
        }
    }

    public boolean authenticateUser(@Nonnull String username, @Nonnull String password) throws SQLException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException {
        if (username.equalsIgnoreCase("admin") && password.equals("admin")) {
            Optional<UserDTO> userDTO = usersService.getByUserName("admin");
            if (userDTO.isEmpty()) insertNewUser("admin", "admin", "admin", "admin", StringUtils.EMPTY, StringUtils.EMPTY, RoleDTO.UserRole.ADMIN.getRoleId(), new Date());
        }
        return (usersService.countByUserNamePassword(username, PasswordUtils.hashPassword(password)).compareTo(0L) > 0);
    }

}
