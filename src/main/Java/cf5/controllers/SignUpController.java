package cf5.controllers;

import cf5.AppConfig;
import cf5.services.generic.AuthenticationService;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.SQLException;

@Controller
public class SignUpController extends AbstractController {
    private @Autowired AuthenticationService authenticationService;

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String goToSignUpPage(HttpSession httpSession, ModelMap modelMap) {
        keepValueToModel(httpSession, modelMap, "username");
        keepValueToModel(httpSession, modelMap, "firstName");
        keepValueToModel(httpSession, modelMap, "lastName");
        keepValueToModel(httpSession, modelMap, "birthDate");
        keepValueToModel(httpSession, modelMap, "email");

        return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String goToWelcomePage(HttpSession httpSession, ModelMap modelMap,
                                  @RequestParam @NotBlank @Size(min = 10, max = 50) String username,
                                  @RequestParam @NotBlank @Size(min = 10, max = 256) String password, @RequestParam @NotBlank @Size(min = 10, max = 256) String confirmPassword,
                                  @RequestParam @NotBlank @Size(min = 2, max = 70) String firstName, @RequestParam @NotBlank @Size(min = 2, max = 70) String lastName,
                                  @DateTimeFormat(pattern = "dd/mm/yyyy") @RequestParam @NotNull String birthDate,
                                  @RequestParam @NotBlank @Email String email) {
        putValueToModel(httpSession, modelMap, "username", StringUtils.trimToEmpty(username));
        putValueToModel(httpSession, modelMap, "firstName", StringUtils.trimToEmpty(firstName));
        putValueToModel(httpSession, modelMap, "lastName", StringUtils.trimToEmpty(lastName));
        putValueToModel(httpSession, modelMap, "birthDate", StringUtils.trimToEmpty(birthDate));
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
            authenticationService.insertNewUser(username, password, firstName, lastName, birthDate, email);
        } catch (Exception e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
        }

        return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
    }
}
