package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.ClassRoomDTO;
import cf5.model.ClassRoom;
import cf5.services.dao.ClassRoomService;
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

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ClassRoomController extends AbstractController {
    private @Autowired ClassRoomService classRoomService;

    @RequestMapping(value = "listClassRooms", method = RequestMethod.GET)
    public String listClassRooms (ModelMap modelMap) {
        try {
            List<ClassRoomDTO> classRoomDTOs = classRoomService.getAll();
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
        modelMap.put("classRoom", ClassRoom.getEmpty());
        return AppConfig.ApplicationPages.ADD_CLASS_ROOM_PAGE.getPage();
    }
    @RequestMapping(value = "addClassRoom", method = RequestMethod.POST)
    public String addClassRoom(ModelMap modelMap, @Valid ClassRoom classRoom, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
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

//    @RequestMapping(value = "updateClassRoom", method = RequestMethod.GET)
//    public String updateClassRoom(ModelMap modelMap, @RequestParam @NotNull @NonNegative int id) {
//        List<UserForCombo> usersList = Lists.newArrayList();
//        try {
//            Optional<TeacherDTO> teacherDTO = teachersService.findByKeys(id);
//            if (teacherDTO.isEmpty()) return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
//            Teacher teacher = Teacher.convertFrom(teacherDTO.orElseThrow());
//            List<UserDTO> userDTOs = usersService.getAll();
//            if (CollectionUtils.isNotEmpty(userDTOs)) usersList = userDTOs.stream().map(UserForCombo::convertFrom).toList();
//            modelMap.put("teacher", teacher);
//            modelMap.put("usersList", usersList);
//            modelMap.put("submitButton", "Update");
//        } catch (SQLException e) {
//            log.atError().log("GET updateTeacher failed: " + e.getMessage());
//            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
//            modelMap.put("submitButton", "Update");
//            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
//        }
//        return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
//    }
//
//    @RequestMapping(value = "updateTeacher", method = RequestMethod.POST)
//    public String updateTeacher(ModelMap modelMap, @Valid Teacher teacher, BindingResult result) {
//        if (result.hasErrors()) {
//            modelMap.put("submitButton", "Update");
//            return AppConfig.ApplicationPages.TEACHER_PAGE.getPage();
//        }
//        try {
//            teachersService.update(teacher.toDTO());
//        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
//            log.atError().log("POST updateTeacher failed: " + e.getMessage());
//            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
//            return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getPage();
//        }
//        return AppConfig.ApplicationPages.TEACHERS_LIST_PAGE.getRedirect();
//    }
}
