package com.khanghn.careerspark_api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khanghn.careerspark_api.dto.ResponseObject;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseWriter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void write(
            HttpServletResponse response,
            int status,
            String code,
            String message
    ) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        objectMapper.writeValue(
                response.getOutputStream(),
                ResponseObject.error(code, message)
        );
    }
}
