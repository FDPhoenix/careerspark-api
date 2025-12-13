package com.khanghn.careerspark_api.dto.response.exception;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExceptionDTO {
    private String code;
    private List<String> message;

    public ExceptionDTO(String code, String messages) {
        this.code = code;
        this.message = new ArrayList<>();
        this.message.add(messages);
    }
}
