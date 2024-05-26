package cf5.controllers;

import cf5.AppConfig;
import cf5.dtos.StudentDTO;
import cf5.services.model.StudentService;
import jakarta.servlet.http.HttpSession;
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
            modelMap.put("students", studentService.getAll());
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
        return AppConfig.ApplicationPages.STUDENTS_ADD_PAGE.getPage();
    }
    @RequestMapping(value = "addStudents", method = RequestMethod.POST)
    public String addStudent(ModelMap modelMap, StudentDTO studentDTO) {
        try {
            studentService.insert(studentDTO);
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENTS_ADD_PAGE.getPage();
        }

        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "deleteStudent", method = RequestMethod.DELETE)
    public String deleteTodo(HttpSession httpSession, ModelMap modelMap,
                             @RequestParam @NotNull int id) {
        try {
            studentService.delete(id);
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
    }

//    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
//    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
//        Todo todo = repository.findById(id).get();
//        //Todo todo = service.retrieveTodo(id);
//        model.put("todo", todo);
//        return "todo";
//    }
//
//    @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
//    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
//
//        if (result.hasErrors()) {
//            return "todo";
//        }
//
//        todo.setUser(getLoggedInUserName(model));
//
//        repository.save(todo);
//        //service.updateTodo(todo);
//
//        return "redirect:/list-todos";
//    }
//

}
