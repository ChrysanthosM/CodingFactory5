package cf5.controllers;

import cf5.AppConfig;
import cf5.dtos.UserDTO;
import cf5.services.generic.AuthenticationService;
import cf5.services.model.UsersService;
import cf5.utils.Converters;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.SQLException;

@Controller
public class UserController extends AbstractController {
    private @Autowired AuthenticationService authenticationService;
    private @Autowired UsersService usersService;

    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    public String goToUserInfoPage(HttpSession httpSession, ModelMap modelMap) {
        return AppConfig.ApplicationPages.USER_INFO_PAGE.getPage();
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String goToSignUpPage(HttpSession httpSession, ModelMap modelMap) {
        putValueToModelFromSession(httpSession, modelMap, "username");
        putValueToModelFromSession(httpSession, modelMap, "firstname");
        putValueToModelFromSession(httpSession, modelMap, "lastname");
        putValueToModelFromSession(httpSession, modelMap, "email");

        return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String goToWelcomePage(HttpSession httpSession, ModelMap modelMap,
                                  @RequestParam @NotBlank @Size(min = 10, max = 50, message="Username must be between 10 and 50 characters...") String username,
                                  @RequestParam @NotBlank @Size(min = 10, max = 128, message="Password must be between 10 and 128 characters...") String password,
                                  @RequestParam @NotBlank @Size(min = 10, max = 128, message="ConfirmPassword must be between 10 and 128 characters...") String confirmPassword,
                                  @RequestParam @NotBlank @Size(min = 2, max = 70, message="FirstName must be between 2 and 70 characters...") String firstname,
                                  @RequestParam @NotBlank @Size(min = 2, max = 70, message="LastName must be between 2 and 70 characters...") String lastname,
                                  @RequestParam @NotBlank @Email String email) {
        putValueToModel(httpSession, modelMap, "username", StringUtils.trimToEmpty(username));
        putValueToModel(httpSession, modelMap, "firstname", StringUtils.trimToEmpty(firstname));
        putValueToModel(httpSession, modelMap, "lastname", StringUtils.trimToEmpty(lastname));
        putValueToModel(httpSession, modelMap, "email", StringUtils.trimToEmpty(email));

        try {
            String authMessage = authenticationService.authenticateSignUp(username, password, confirmPassword);
            if (StringUtils.isNotBlank(authMessage)) {
                modelMap.put("errorMessage", "Error: " + authMessage);
                return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
            }
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
        }

        try {
            authenticationService.insertNewUser(username, password, firstname, lastname, email);
        } catch (Exception e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
        }

        return AppConfig.ApplicationPages.WELCOME_PAGE.getRedirect();
    }


}
