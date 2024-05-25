package cf5.services.generic;

import cf5.Utils.Converters;
import cf5.dtos.UserDTO;
import cf5.services.model.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
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

    public void insertNewUser(@Nonnull String username, @Nonnull String password, @Nonnull String firstName, @Nonnull String lastName, @Nonnull String birthDate, @Nonnull String email) throws ParseException, SQLException, InvocationTargetException, IllegalAccessException {
        UserDTO userDTO = new UserDTO(0, firstName, password, firstName, lastName, email, Converters.convertStringToDate(birthDate));
        usersService.insert(userDTO);
    }

    public boolean authenticateUser(@Nonnull String username, @Nonnull String password) throws SQLException {
        return (usersService.getByUserNamePassword(username, password).compareTo(0L) > 0);
    }

}