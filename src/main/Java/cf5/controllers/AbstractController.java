package cf5.controllers;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

public abstract class AbstractController {
    protected static void putValueToModelFromSession(HttpSession httpSession, ModelMap modelMap, String attribute) {
        if (StringUtils.isNotBlank(attribute)) {
            String attributeValue = (String) httpSession.getAttribute(attribute);
            if (StringUtils.isNotBlank(attributeValue)) modelMap.put(attribute, attributeValue);
        }
    }
    protected static void putValueToModel(HttpSession httpSession, ModelMap modelMap, String attribute, String attributeValue) {
        if (StringUtils.isNotBlank(attribute)) {
            modelMap.put(attribute, StringUtils.trimToEmpty(attributeValue));
            httpSession.setAttribute(attribute, StringUtils.trimToEmpty(attributeValue));
        }
    }
    protected static void putValueToModel(HttpSession httpSession, ModelMap modelMap, String attribute, Integer attributeValue) {
        modelMap.put(attribute, attributeValue);
        httpSession.setAttribute(attribute, attributeValue);
    }

    protected static void clearAttributes(HttpSession httpSession, ModelMap modelMap) {
        modelMap.clear();

        java.util.Enumeration<String> attributeNames = httpSession.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            httpSession.removeAttribute(attributeName);
        }
    }
}
