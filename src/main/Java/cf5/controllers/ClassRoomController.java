package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.ClassRoomDTO;
import cf5.dto.LessonDTO;
import cf5.dto.UserDTO;
import cf5.model.ClassRoom;
import cf5.model.Lesson;
import cf5.model.UserForCombo;
import cf5.services.dao.ClassRoomService;
import cf5.services.dao.LessonsService;
import cf5.services.dao.UsersService;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@Slf4j
public class ClassRoomController extends AbstractController {
    private @Autowired ClassRoomService classRoomService;
    private @Autowired UsersService usersService;
    private @Autowired LessonsService lessonsService;

    @RequestMapping(value = "listClassRooms", method = RequestMethod.GET)
    public String listClassRooms (HttpSession httpSession, ModelMap modelMap) {
        try {
            putValueToModelFromSession(httpSession, modelMap, "username");
            String username = (String) modelMap.getAttribute ("username");
            if (StringUtils.isBlank(username)) throw new NoSuchElementException("username not detected");
            UserDTO userDto = usersService.getByUserName(username).orElseThrow() ;
            List<ClassRoomDTO> classRoomDTOs = userDto.roleId() == 0 ? classRoomService.getAll() : classRoomService.getClassesForTeacher(userDto) ;
            if (CollectionUtils.isEmpty(classRoomDTOs)) return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getPage();
            List<ClassRoom> classRoomsList = classRoomDTOs.stream().map(ClassRoom::convertFrom).toList();
            modelMap.put("classRoomsList", classRoomsList);
        } catch (SQLException e) {
            log.atError().log("listClassRooms failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.WELCOME_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getPage();
    }

    @RequestMapping(value = "addClassRoom", method = RequestMethod.GET)
    public String addClassRoom(ModelMap modelMap) {
        List<UserForCombo> teachersList;
        List<Lesson> lessonsList;
        try {
            teachersList = usersService.getAllTeachers();
            lessonsList = lessonsService.getAll().stream().map(Lesson::convertFrom).toList();
        } catch (SQLException e) {
            log.atError().log("GET addClassRoom failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getPage();
        }
        modelMap.put("classRoom", ClassRoom.getEmpty());
        modelMap.put("teachersList", teachersList);
        modelMap.put("lessonsList", lessonsList);
        modelMap.put("submitButton", "Add");
        return AppConfig.ApplicationPages.CLASS_ROOM_PAGE.getPage();
    }
    @RequestMapping(value = "addClassRoom", method = RequestMethod.POST)
    public String addClassRoom(ModelMap modelMap, @Valid ClassRoom classRoom, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<UserForCombo> teachersList;
            List<Lesson> lessonsList;
            try {
                teachersList = usersService.getAllTeachers();
                lessonsList = lessonsService.getAll().stream().map(Lesson::convertFrom).toList();
            } catch (SQLException e) {
                log.atError().log("POST addClassRoom failed: " + e.getMessage());
                modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
                return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getPage();
            }
            modelMap.put("teachersList", teachersList);
            modelMap.put("lessonsList", lessonsList);
            modelMap.put("submitButton", "Add");
            return AppConfig.ApplicationPages.CLASSROOM_PAGE.getPage();
        }
        try {
            classRoomService.insert(classRoom.toDTO());
        } catch (ValidationException e) {
            modelMap.put("errorMessage", "Validation Error: " + e.getMessage());
            modelMap.put("submitButton", "Add");
            return AppConfig.ApplicationPages.CLASSROOM_PAGE.getPage();
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("POST addClassRoom failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            modelMap.put("submitButton", "Add");
            return AppConfig.ApplicationPages.CLASSROOM_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getRedirect();
    }

    @RequestMapping("cancelClassRoom")
    public String cancelClassRoom() {
        return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "deleteClassRoom", method = RequestMethod.GET)
    public String deleteClassRoom(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        try {
            classRoomService.delete(id);
        } catch (Exception e) {
            log.atError().log("deleteClassRoom failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getRedirect();
    }

    @RequestMapping(value = "updateClassRoom", method = RequestMethod.GET)
    public String updateClassRoom(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
        List<UserForCombo> usersList = Lists.newArrayList();
        try {
            Optional<ClassRoomDTO> classRoomDTO = classRoomService.findByKeys(id);
            if (classRoomDTO.isEmpty()) return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getPage();
            ClassRoom classRoom = ClassRoom.convertFrom(classRoomDTO.orElseThrow());

            modelMap.put("classRoom", classRoom);
            modelMap.put("teachersList", usersService.getAllTeachers());
            modelMap.put("lessonsList", lessonsService.getAll().stream().map(Lesson::convertFrom).toList());
            modelMap.put("submitButton", "Update");
        } catch (SQLException e) {
            log.atError().log("GET updateClassRoom failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            modelMap.put("submitButton", "Update");
            return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.CLASSROOM_PAGE.getPage();
    }
    @RequestMapping(value = "updateClassRoom", method = RequestMethod.POST)
    public String updateClassRoom(ModelMap modelMap, @Valid ClassRoom classRoom, BindingResult result) {
        if (result.hasErrors()) {
            try {
                modelMap.put("teachersList", usersService.getAllTeachers());
                modelMap.put("lessonsList", lessonsService.getAll().stream().map(Lesson::convertFrom).toList());
            } catch (SQLException e) {
                log.atError().log("GET updateClassRoom failed: " + e.getMessage());
                modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
                modelMap.put("submitButton", "Update");
            }
            modelMap.put("submitButton", "Update");
            return AppConfig.ApplicationPages.CLASSROOM_PAGE.getPage();
        }
        try {
            classRoomService.update(classRoom.toDTO());
        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            log.atError().log("POST updateClassRoom failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getPage();
        }
        return AppConfig.ApplicationPages.CLASSROOMS_LIST_PAGE.getRedirect();
    }
}
