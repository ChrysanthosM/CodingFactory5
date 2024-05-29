package cf5.services.generic;

import cf5.utils.PasswordUtils;
import cf5.dtos.UserDTO;
import cf5.services.model.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class AuthenticationService {
    private @Autowired UsersService usersService;

    public String authenticateSignUp(@Nonnull String username, @Nonnull String password, @Nonnull String confirmPassword) throws SQLException {
        if (!password.equals(confirmPassword)) return "Password and confirmPassword not same! Please try again";

        Optional<UserDTO> userDTO = usersService.getByUserName(username);
        if (userDTO.isPresent()) return "UserName already exist!";

        return StringUtils.EMPTY;
    }

    public void insertNewUser(@Nonnull String username, @Nonnull String password, @Nonnull String firstName, @Nonnull String lastName, @Nonnull String email) throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException {
        UserDTO userDTO = new UserDTO(0, firstName, PasswordUtils.hashPassword(password), firstName, lastName, email);
        usersService.insert(userDTO);
    }

    public boolean authenticateUser(@Nonnull String username, @Nonnull String password) throws SQLException, NoSuchAlgorithmException {
        return (usersService.countByUserNamePassword(username, PasswordUtils.hashPassword(password)).compareTo(0L) > 0);
    }

}
