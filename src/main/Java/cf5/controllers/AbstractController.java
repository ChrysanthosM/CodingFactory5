package cf5.controllers;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

public abstract class AbstractController {
    protected static void keepValueToModel(HttpSession httpSession, ModelMap modelMap, String attribute) {
        String attributeValue = (String) httpSession.getAttribute(attribute);
        if (StringUtils.isNotBlank(attributeValue)) modelMap.put(attribute, attributeValue);
    }
    protected static void putValueToModel(HttpSession httpSession, ModelMap modelMap, String attribute, String attributeValue) {
        modelMap.put(attribute, StringUtils.trimToEmpty(attribute));
        httpSession.setAttribute(attribute, StringUtils.trimToEmpty(attribute));
    }

}
