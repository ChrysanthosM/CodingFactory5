package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.StudentDTO;
import cf5.model.Student;
import cf5.services.dao.StudentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class StudentsController extends AbstractController {
    private @Autowired StudentService studentService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "listStudents", method = RequestMethod.GET)
    public String listStudents (ModelMap modelMap) {
        try {
            List<StudentDTO> studentDTOs = studentService.getAll();
            if (CollectionUtils.isEmpty(studentDTOs)) return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
            List<Student> studentsList = studentDTOs.stream().map(Student::convertFrom).toList();
            modelMap.put("studentsList", studentsList);
        } catch (SQLException e) {
            log.atError().log("listStudents failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
    }

    @RequestMapping(value = "addStudent", method = RequestMethod.GET)
    public String addStudents(ModelMap modelMap) {
        modelMap.put("student", Student.getEmpty());
        modelMap.put("submitButton", "Add");
        return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
    }
    @RequestMapping(value = "addStudent", method = RequestMethod.POST)
    public String addStudents(ModelMap modelMap, @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelMap.put("submitButton", "Add");
            return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
        }
        try {
            studentService.insert(student.toDTO());
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("addStudent failed: " + e.getMessage());
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
            log.atError().log("deleteStudent failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "updateStudent", method = RequestMethod.GET)
    public String updateStudent(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            Optional<StudentDTO> studentDTO = studentService.findByKeys(id);
            if (studentDTO.isEmpty()) return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
            Student student = Student.convertFrom(studentDTO.orElseThrow());
            modelMap.put("student", student);
            modelMap.put("submitButton", "Update");
        } catch (SQLException e) {
            log.atError().log("GET updateStudent failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
    }

    @RequestMapping(value = "updateStudent", method = RequestMethod.POST)
    public String updateStudent(ModelMap modelMap, @Valid Student student, BindingResult result) {
        if (result.hasErrors()) {
            modelMap.put("submitButton", "Update");
            return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
        }
        try {
            studentService.update(student.toDTO());
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("POST updateStudent failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
    }
}
