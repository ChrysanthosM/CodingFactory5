package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.StudentDTO;
import cf5.services.dao.StudentService;
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
public class StudentsController extends AbstractController {
    private @Autowired StudentService studentService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "listStudents", method = RequestMethod.GET)
    public String goToStudentsListPage (HttpSession httpSession, ModelMap modelMap) {
        String username = (String) httpSession.getAttribute("username");

        try {
            modelMap.put("studentDTOs", studentService.getAll());
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
    }

    @RequestMapping(value = "addStudents", method = RequestMethod.GET)
    public String goToStudentsAddPage(ModelMap modelMap) {
        StudentDTO studentDTO = StudentDTO.getEmpty();
        modelMap.put("studentDTO", studentDTO);
        modelMap.put("submitButton", "Add");
        return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
    }
    @RequestMapping(value = "addStudents", method = RequestMethod.POST)
    public String addStudent(ModelMap modelMap, @Valid StudentDTO studentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
        }
        try {
            studentService.insert(studentDTO);
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
        }

        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
    }

    @RequestMapping("cancelStudent")
    public String cancelStudent() {
        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "deleteStudent", method = RequestMethod.GET)
    public String deleteStudent(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            studentService.delete(id);
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "updateStudent", method = RequestMethod.GET)
    public String showUpdateStudentPage(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            Optional<StudentDTO> studentDTO = studentService.findByKeys(id);
            if (studentDTO.isEmpty()) return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
            modelMap.put("studentDTO", studentDTO.get());
            modelMap.put("submitButton", "Update");
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
    }

    @RequestMapping(value = "updateStudent", method = RequestMethod.POST)
    public String updateStudent(ModelMap modelMap, @Valid StudentDTO studentDTO, BindingResult result) {
        if (result.hasErrors()) {
            return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
        }

        try {
            studentService.update(studentDTO);

        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
    }
}
