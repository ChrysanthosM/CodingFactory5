package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.UserDTO;
import cf5.services.generic.AuthenticationService;
import cf5.services.dao.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@Controller
public class LoginController extends AbstractController {
    private @Autowired AuthenticationService authenticationService;
    private @Autowired UsersService usersService;

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public String postLogout(HttpSession httpSession, ModelMap modelMap) {
        clearAttributes(httpSession, modelMap);
        return AppConfig.ApplicationPages.LOGIN_PAGE.getRedirect();
    }
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String getLogout(HttpSession httpSession, ModelMap modelMap) {
        clearAttributes(httpSession, modelMap);
        return AppConfig.ApplicationPages.LOGIN_PAGE.getRedirect();
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpSession httpSession, ModelMap modelMap) {
        putValueToModelFromSession(httpSession, modelMap, "username");
        return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpSession httpSession, ModelMap modelMap,
                        @RequestParam @NotBlank String username, @RequestParam @NotBlank String password) {
        putValueToModel(httpSession, modelMap, "username", username);
        try {
            if (authenticationService.authenticateUser(username, password)) {
                UserDTO userDTO = usersService.getByUserName(username).orElseThrow();
                putValueToModel(httpSession, modelMap, "firstname", userDTO.firstName());
                putValueToModel(httpSession, modelMap, "lastname", userDTO.lastName());
                putValueToModel(httpSession, modelMap, "email", userDTO.email());
                putValueToModel(httpSession, modelMap, "roleId", userDTO.roleId());
                return AppConfig.ApplicationPages.WELCOME_PAGE.getRedirect();
            }
        } catch (SQLException | NoSuchAlgorithmException | InvocationTargetException | IllegalAccessException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
        }

        modelMap.put("errorMessage", "Invalid credentials! Please try again...");
        return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
    }
}
