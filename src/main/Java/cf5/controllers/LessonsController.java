package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.LessonDTO;
import cf5.model.Lesson;
import cf5.services.dao.LessonsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class LessonsController extends AbstractController {
    private @Autowired LessonsService lessonsService;

    @RequestMapping(value = "listLessons", method = RequestMethod.GET)
    public String listLessons (ModelMap modelMap) {
        try {
            List<LessonDTO> lessonDTOs = lessonsService.getAll();
            if (CollectionUtils.isEmpty(lessonDTOs)) return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getPage();
            List<Lesson> lessonsList = lessonDTOs.stream().map(Lesson::convertFrom).toList();
            modelMap.put("lessonsList", lessonsList);
        } catch (SQLException e) {
            log.atError().log("listLessons failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getPage();
    }

    @RequestMapping(value = "addLesson", method = RequestMethod.GET)
    public String addLesson(ModelMap modelMap) {
        Lesson lesson = Lesson.getEmpty();
        modelMap.put("lesson", lesson);
        modelMap.put("submitButton", "Add");
        return AppConfig.ApplicationPages.LESSON_PAGE.getPage();
    }
    @RequestMapping(value = "addLesson", method = RequestMethod.POST)
    public String addLesson(ModelMap modelMap, @Valid Lesson lesson, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return AppConfig.ApplicationPages.LESSON_PAGE.getPage(); }
        try {
            lessonsService.insert(lesson.toDTO());
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("addLesson failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.LESSON_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getRedirect();
    }

    @RequestMapping("cancelLesson")
    public String cancelLesson() {
        return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "deleteLesson", method = RequestMethod.GET)
    public String deleteLesson(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            lessonsService.delete(id);
        } catch (SQLException e) {
            log.atError().log("deleteLesson failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "updateLesson", method = RequestMethod.GET)
    public String updateLesson(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            Optional<LessonDTO> lessonDTO = lessonsService.findByKeys(id);
            if (lessonDTO.isEmpty()) return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getPage();
            Lesson lesson = Lesson.convertFrom(lessonDTO.orElseThrow());
            modelMap.put("lesson", lesson);
            modelMap.put("submitButton", "Update");
        } catch (SQLException e) {
            log.atError().log("GET updateLesson failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.LESSON_PAGE.getPage();
    }

    @RequestMapping(value = "updateLesson", method = RequestMethod.POST)
    public String updateLesson(ModelMap modelMap, @Valid Lesson lesson, BindingResult result) {
        if (result.hasErrors()) {
            modelMap.put("submitButton", "Update");
            return AppConfig.ApplicationPages.LESSON_PAGE.getPage();
        }
        try {
            lessonsService.update(lesson.toDTO());
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("POST updateLesson failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.LESSONS_LIST_PAGE.getRedirect();
    }
}
