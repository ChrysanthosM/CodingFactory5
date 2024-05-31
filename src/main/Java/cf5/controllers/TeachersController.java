package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.TeacherDTO;
import cf5.services.dao.TeachersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class TeachersController extends AbstractController {
    private @Autowired TeachersService teachersService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "listTeachers", method = RequestMethod.GET)
    public String goToTeachersListPage (HttpSession httpSession, ModelMap modelMap) {
        String username = (String) httpSession.getAttribute("username");

        try {
            modelMap.put("TeacherDTOs", teachersService.getAll());
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
    }

    @RequestMapping(value = "addTeachers", method = RequestMethod.GET)
    public String goToTeachersAddPage(ModelMap modelMap) {
        TeacherDTO TeacherDTO = cf5.dto.TeacherDTO.getEmpty();
        modelMap.put("TeacherDTO", TeacherDTO);
        modelMap.put("submitButton", "Add");
        return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
    }
    @RequestMapping(value = "addTeachers", method = RequestMethod.POST)
    public String addTeacher(ModelMap modelMap, @Valid TeacherDTO TeacherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
        }
        try {
            teachersService.insert(TeacherDTO);
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
        }

        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getRedirect();
    }

    @RequestMapping("cancelTeacher")
    public String cancelTeacher() {
        return AppConfig.ApplicationPages.TEACHER_PAGE.getRedirect();
    }

    @RequestMapping(value = "deleteTeacher", method = RequestMethod.GET)
    public String deleteTeacher(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            teachersService.delete(id);
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "updateTeacher", method = RequestMethod.GET)
    public String showUpdateTeacherPage(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            Optional<TeacherDTO> TeacherDTO = teachersService.findByKeys(id);
            if (TeacherDTO.isEmpty()) return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
            modelMap.put("TeacherDTO", TeacherDTO.get());
            modelMap.put("submitButton", "Update");
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
    }

    @RequestMapping(value = "updateTeacher", method = RequestMethod.POST)
    public String updateTeacher(ModelMap modelMap, @Valid TeacherDTO TeacherDTO, BindingResult result) {
        if (result.hasErrors()) {
            return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
        }

        try {
            teachersService.update(TeacherDTO);

        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getRedirect();
    }
}
