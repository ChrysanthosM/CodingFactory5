package cf5.controllers;

import cf5.AppConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToIndexPage() {
        return AppConfig.ApplicationPages.INDEX_PAGE.getPage();
    }
}
