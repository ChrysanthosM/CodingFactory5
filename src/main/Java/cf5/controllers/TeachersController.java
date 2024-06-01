package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.TeacherDTO;
import cf5.model.Teacher;
import cf5.model.Teacher;
import cf5.services.dao.TeachersService;
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
public class TeachersController extends AbstractController {
    private @Autowired TeachersService TeachersService;

    @RequestMapping(value = "listTeachers", method = RequestMethod.GET)
    public String listTeachers (ModelMap modelMap) {
        try {
            List<TeacherDTO> teacherDTOs = TeachersService.getAll();
            if (CollectionUtils.isEmpty(teacherDTOs)) return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
            List<Teacher> teachersList = teacherDTOs.stream().map(Teacher::convertFrom).toList();
            modelMap.put("teachersList", teachersList);
        } catch (SQLException e) {
            log.atError().log("listTeachers failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
    }

    @RequestMapping(value = "addTeacher", method = RequestMethod.GET)
    public String addTeacher(ModelMap modelMap) {
        Teacher teacher = Teacher.getEmpty();
        modelMap.put("teacher", teacher);
        modelMap.put("submitButton", "Add");
        return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
    }
    @RequestMapping(value = "addTeacher", method = RequestMethod.POST)
    public String addTeacher(ModelMap modelMap, @Valid Teacher Teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return AppConfig.ApplicationPages.TEACHER_PAGE.getPage(); }
        try {
            TeachersService.insert(Teacher.toDTO());
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("addTeacher failed: " + e.getMessage());
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
            TeachersService.delete(id);
        } catch (SQLException e) {
            log.atError().log("deleteTeacher failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "updateTeacher", method = RequestMethod.GET)
    public String updateTeacher(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            Optional<TeacherDTO> teacherDTO = TeachersService.findByKeys(id);
            if (teacherDTO.isEmpty()) return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
            Teacher teacher = Teacher.convertFrom(teacherDTO.orElseThrow());
            modelMap.put("teacher", teacher);
            modelMap.put("submitButton", "Update");
        } catch (SQLException e) {
            log.atError().log("GET updateTeacher failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
    }

    @RequestMapping(value = "updateTeacher", method = RequestMethod.POST)
    public String updateTeacher(ModelMap modelMap, @Valid Teacher teacher, BindingResult result) {
        if (result.hasErrors()) {
            modelMap.put("submitButton", "Update");
            return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
        }
        try {
            TeachersService.update(teacher.toDTO());
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("POST updateTeacher failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getRedirect();
    }
}
