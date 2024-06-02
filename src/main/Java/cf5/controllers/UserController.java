package cf5.controllers;

import cf5.AppConfig;
import cf5.dto.RoleDTO;
import cf5.model.Role;
import cf5.model.User;
import cf5.services.dao.RolesService;
import cf5.services.generic.AuthenticationService;
import cf5.services.dao.UsersService;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.List;

@Controller
@Slf4j
public class UserController extends AbstractController {
    private @Autowired AuthenticationService authenticationService;
    private @Autowired UsersService usersService;
    private @Autowired RolesService rolesService;

    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    public String userInfo(ModelMap modelMap) {
        return AppConfig.ApplicationPages.USER_INFO_PAGE.getPage();
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String signup(ModelMap modelMap) {
        try {
            modelMap.put("rolesList", getRoles());
        } catch (SQLException e) {
            log.atError().log("GET signup failed: " + e.getMessage());
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
        }
        modelMap.put("user", User.getEmpty());
        return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signup(ModelMap modelMap, HttpSession httpSession, @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            try {
                modelMap.put("rolesList", getRoles());
            } catch (SQLException e) {
                log.atError().log("POST signup failed: " + e.getMessage());
                modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
                return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
            }
            modelMap.put("user", user);
            return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
        }
        try {
            String authMessage = authenticationService.authenticateSignUp(user.username(), user.password(), user.confirmPassword());
            if (StringUtils.isNotBlank(authMessage)) {
                modelMap.put("errorMessage", "Error: " + authMessage);
                try {
                    modelMap.put("rolesList", getRoles());
                } catch (SQLException e) {
                    log.atError().log("authenticationService.authenticateSignUp failed: " + e.getMessage());
                    modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
                    return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
                }
                modelMap.put("user", user);
                return AppConfig.ApplicationPages.SIGN_UP_PAGE.getPage();
            }
        } catch (SQLException e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
        }

        try {
            authenticationService.insertNewUser(user.username(), user.password(), user.firstName(), user.lastName(), user.email(), user.phone(), user.roleId());
        } catch (Exception e) {
            modelMap.put("errorMessage", "Oops... Something went wrong. (" + e.getMessage() + ")");
            return AppConfig.ApplicationPages.LOGIN_PAGE.getPage();
        }

        putValueToModel(httpSession, modelMap, "username", user.username());
        putValueToModel(httpSession, modelMap, "firstname", user.firstName());
        putValueToModel(httpSession, modelMap, "lastname", user.lastName());
        putValueToModel(httpSession, modelMap, "email", user.email());
        putValueToModel(httpSession, modelMap, "roleId", user.roleId());
        return AppConfig.ApplicationPages.WELCOME_PAGE.getRedirect();
    }

    private List<Role> getRoles() throws SQLException {
        List<RoleDTO> roleDTOs =  rolesService.getForSignUp();
        if (CollectionUtils.isEmpty(roleDTOs)) return Lists.newArrayList();
        return roleDTOs.stream().map(Role::convertFrom).toList();
    }
}
