package cf5.controllers;

import cf5.AppConfig;
import cf5.services.generic.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class LoginController extends AbstractController {
    private @Autowired AuthenticationService authenticationService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String goToLoginPage() {
        return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String goToWelcomePage(HttpSession httpSession, ModelMap modelMap,
                                  @RequestParam String username, @RequestParam String password) {
        putValueToModel(httpSession, modelMap, "username", username);
        try {
            if (authenticationService.authenticateUser(username, password)) {
                return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
            }
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
        }
        modelMap.put("errorMessage", "Invalid credentials! Please try again");
        return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
    }
}
