package cf5.controllers;

import cf5.AppConfig;
import cf5.services.model.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String showTodos(HttpSession httpSession, ModelMap modelMap) {
        String username = (String) httpSession.getAttribute("username");

        try {
            modelMap.put("students", studentService.getAll());
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
    }

//    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
//    public String showAddTodoPage(ModelMap model) {
//        model.addAttribute("todo", new Todo(0, getLoggedInUserName(model),
//                "Default Desc", new Date(), false));
//        return "todo";
//    }
//
//    @RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
//    public String deleteTodo(@RequestParam int id) {
//        repository.deleteById(id);
//        //service.deleteTodo(id);
//        return "redirect:/list-todos";
//    }
//
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
//    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
//    public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
//        if (result.hasErrors()) {
//            return "todo";
//        }
//
//        todo.setUser(getLoggedInUserName(model));
//        repository.save(todo);
//		/*service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(),
//				false);*/
//        return "redirect:/list-todos";
//    }
}
