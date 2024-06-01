package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.TeacherDTO;
import cf5.dto.UserDTO;
import cf5.model.Teacher;
import cf5.model.Teachers;
import cf5.model.User;
import cf5.model.UserForCombo;
import cf5.services.dao.TeachersService;
import cf5.services.dao.UsersService;
import com.google.common.collect.Lists;
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
    private @Autowired TeachersService teachersService;
    private @Autowired UsersService usersService;

    @RequestMapping(value = "listTeachers", method = RequestMethod.GET)
    public String listTeachers (ModelMap modelMap) {
        try {
            List<TeacherDTO> teacherDTOs = teachersService.getAll();
            if (CollectionUtils.isEmpty(teacherDTOs)) return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
            List<Teachers> teachersList = teacherDTOs.stream().map(Teachers::convertFrom).toList();
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
        List<UserForCombo> usersList = Lists.newArrayList();
        try {
            List<UserDTO> userDTOs = usersService.getAll();
            if (CollectionUtils.isNotEmpty(userDTOs)) usersList = userDTOs.stream().map(UserForCombo::convertFrom).toList();
        } catch (SQLException e) {
            log.atError().log("GET addTeacher failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
        }
        modelMap.put("teacher", Teacher.getEmpty());
        modelMap.put("usersList", usersList);
        modelMap.put("submitButton", "Add");
        return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
    }
    @RequestMapping(value = "addTeacher", method = RequestMethod.POST)
    public String addTeacher(ModelMap modelMap, @Valid Teacher teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelMap.put("submitButton", "Add");
            return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
        }
        try {
            teachersService.insert(teacher.toDTO());
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("POST addTeacher failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getRedirect();
    }

    @RequestMapping("cancelTeacher")
    public String cancelTeacher() {
        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "deleteTeacher", method = RequestMethod.GET)
    public String deleteTeacher(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            teachersService.delete(id);
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
            Optional<TeacherDTO> teacherDTO = teachersService.findByKeys(id);
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
            teachersService.update(teacher.toDTO());
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("POST updateTeacher failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getRedirect();
    }
}
