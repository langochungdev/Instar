package com.instar.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Component
public class CustomErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public ResponseEntity<ApiError> error(HttpServletRequest request) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> attrs = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        int status = (int) attrs.getOrDefault("status", 500);
        String message = (String) attrs.getOrDefault("message", "Có lỗi xảy ra trong hệ thống");
        String path = (String) attrs.getOrDefault("path", request.getRequestURI());

        ApiError error = new ApiError(
                status,
                "INTERNAL_SERVER_ERROR",
                message,
                path
        );

        return ResponseEntity.status(status).body(error);
    }
}
