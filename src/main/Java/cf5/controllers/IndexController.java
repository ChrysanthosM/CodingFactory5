package cf5.controllers;

import cf5.AppConfig;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController extends AbstractController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToIndexPage(HttpSession httpSession, ModelMap modelMap) {
        String username = (String) httpSession.getAttribute("username");
        if (StringUtils.isBlank(username)) return AppConfig.ApplicationPages.INDEX_PAGE.getPage();
        return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
    }
}
