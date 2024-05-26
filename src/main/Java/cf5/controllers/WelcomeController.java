package cf5.controllers;

import cf5.AppConfig;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController extends AbstractController {
    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String showWelcomePage(HttpSession httpSession, ModelMap modelMap) {
        putValueToModelFromSession(httpSession, modelMap, "firstname");
        return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
    }
}
