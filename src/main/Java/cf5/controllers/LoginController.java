package cf5.controllers;

import cf5.services.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private @Autowired AuthenticationService authenticationService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String goToLoginPage() {
        return "loginPage";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String goToWelcomePage(@RequestParam String username, @RequestParam String password, ModelMap modelMap) {
        modelMap.put("username", StringUtils.trimToEmpty(username));
        if (authenticationService.authenticateUser(username, password)) {
            return "welcomePage";
        } else {
            modelMap.put("errorMessage", "Invalid credentials! Please try again");
            return "loginPage";
        }
    }
}
