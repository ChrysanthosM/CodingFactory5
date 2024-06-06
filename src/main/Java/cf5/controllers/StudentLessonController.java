package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.StudentDTO;
import cf5.dto.StudentLessonDTO;
import cf5.dto.UserDTO;
import cf5.model.Student;
import cf5.model.StudentLesson;
import cf5.services.dao.StudentsLessonsService;
import cf5.services.dao.StudentsService;
import cf5.services.dao.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@Slf4j
public class StudentLessonController extends AbstractController {
    private @Autowired StudentsLessonsService studentsLessonsService;
    private @Autowired UsersService usersService;

    @RequestMapping(value = "listStudentLessons", method = RequestMethod.GET)
    public String listStudentLessons (HttpSession httpSession, ModelMap modelMap) {
        try {
            putValueToModelFromSession(httpSession, modelMap, "username");
            String username = (String) modelMap.getAttribute ("username");
            if (StringUtils.isBlank(username)) throw new NoSuchElementException("username not detected");

            Optional<UserDTO> userDto = usersService.getByUserName(username);
            if (userDto.isEmpty()) throw new RuntimeException("username not found");
            List<StudentLessonDTO> studentLessonDTOs = studentsLessonsService.getLessonsForStudent(userDto.orElseThrow().recId());
            if (CollectionUtils.isEmpty(studentLessonDTOs)) return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();

            List<StudentLesson> studentLessonsList = studentLessonDTOs.stream().map(StudentLesson::convertFrom).toList();
            modelMap.put("studentLessonsList", studentLessonsList);
        } catch (SQLException e) {
            log.atError().log("listStudentLessons failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.STUDENT_LESSONS_LIST_PAGE.getPage();
    }

    @PostMapping("/submitStudentLessons")
    public ModelAndView submitLessons(
            @RequestParam("studentId[]") List<Integer> studentIds,
            @RequestParam("lessonId[]") List<Integer> lessonIds,
            @RequestParam("selected[]") List<Integer> selected) {

        // Process the received data
        for (int i = 0; i < studentIds.size(); i++) {
            Integer studentId = studentIds.get(i);
            Integer lessonId = lessonIds.get(i);
            Integer isSelected = selected.get(i);
            // Save or process the data as needed
        }

        // Return a view or redirect as necessary
        return new ModelAndView(AppConfig.ApplicationPages.WELCOME_PAGE.getRedirect());
    }


//
//    @RequestMapping(value = "addStudent", method = RequestMethod.GET)
//    public String addStudents(ModelMap modelMap) {
//        modelMap.put("student", Student.getEmpty());
//        modelMap.put("submitButton", "Add");
//        return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
//    }
//    @RequestMapping(value = "addStudent", method = RequestMethod.POST)
//    public String addStudents(ModelMap modelMap, @Valid Student student, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            modelMap.put("submitButton", "Add");
//            return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
//        }
//        try {
//            studentsService.insert(student.toDTO());
//        } catch (ValidationException e) {
//            modelMap.put("errorMessage", "Validation Error: " + e.getMessage());
//            modelMap.put("submitButton", "Add");
//            return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
//        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
//            log.atError().log("addStudent failed: " + e.getMessage());
//            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
//            modelMap.put("submitButton", "Add");
//            return AppConfig.ApplicationPages.STUDENT_PAGE.getPage();
//        }
//        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
//    }
//
//    @RequestMapping("cancelStudent")
//    public String cancelStudent() {
//        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
//    }
//
//    @RequestMapping(value = "deleteStudent", method = RequestMethod.GET)
//    public String deleteStudent(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
//        try {
//            studentsService.delete(id);
//        } catch (SQLException e) {
//            log.atError().log("deleteStudent failed: " + e.getMessage());
//            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
//            return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getPage();
//        }
//        return AppConfig.ApplicationPages.STUDENTS_LIST_PAGE.getRedirect();
//    }
}