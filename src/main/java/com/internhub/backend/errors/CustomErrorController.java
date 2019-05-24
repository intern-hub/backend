package com.internhub.backend.errors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    public void setErrorAttributes(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = "error")
    @ResponseBody
    public CustomErrorResponse error(WebRequest webRequest, HttpServletResponse response) {
        CustomErrorResponse errors = new CustomErrorResponse();

        // Extract error message from the raw error attributes
        Map<String, Object> attributes = getErrorAttributes(webRequest);
        String message = "No message available";
        if (attributes.containsKey("error"))
            message = (String) attributes.get("error");

        errors.setTimestamp(LocalDateTime.now());
        errors.setError(message);
        errors.setStatus(response.getStatus());
        return errors;
    }

    @Override
    public String getErrorPath() {
        return "error";
    }

    private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.putAll(errorAttributes.getErrorAttributes(webRequest, false));
        return errorMap;
    }
}
