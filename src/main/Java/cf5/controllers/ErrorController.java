package cf5.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller("error")
public class ErrorController extends AbstractController {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex){
        ModelAndView mv = new ModelAndView();

        mv.addObject("exception", ex.getLocalizedMessage());
        mv.addObject("url", request.getRequestURL());

        mv.setViewName("ErrorPage");
        return mv;
    }
}