package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.StudentLessonDTO;
import cf5.dto.UserDTO;
import cf5.model.StudentLesson;
import cf5.services.dao.StudentsLessonsService;
import cf5.services.dao.UsersService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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

    @RequestMapping(value = "submitStudentLessons", method = RequestMethod.POST)
    public String submitStudentLessons(ModelMap modelMap,
            @RequestParam("studentId[]") List<Integer> studentIds,
            @RequestParam("lessonId[]") List<Integer> lessonIds,
            @RequestParam("selected[]") List<Integer> selected) {
        if (CollectionUtils.isEmpty(studentIds) || CollectionUtils.isEmpty(lessonIds) || CollectionUtils.isEmpty(selected)) return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
        Preconditions.checkArgument(studentIds.size() == lessonIds.size() && lessonIds.size() == selected.size());
        int listsSize = studentIds.size();

        List<StudentLessonDTO> studentLessonDTOs = Lists.newArrayList();
        for (int i = 0; i < listsSize; i++) {
            studentLessonDTOs.add(new StudentLessonDTO(studentIds.get(i), lessonIds.get(i), null, String.valueOf(selected.get(i))));
        }
        try {
            studentsLessonsService.replaceStudentLessons(studentLessonDTOs);
        } catch (SQLException e) {
            log.atError().log("submitStudentLessons failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.STUDENT_LESSONS_LIST_PAGE.getPage();
        }

        return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
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
