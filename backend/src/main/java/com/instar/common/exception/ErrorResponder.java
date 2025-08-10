package com.instar.common.exception;

import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponder {

    public static void sendError(HttpServletResponse response, String message) {
        System.out.println("ERROR: " + message);
        try {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json;charset=UTF-8");

            Map<String, String> body = new HashMap<>();
            body.put("error", message);

            String json = new ObjectMapper().writeValueAsString(body);
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
