package com.khanghn.careerspark_api.exception.black;

import com.khanghn.careerspark_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super("400", message, HttpStatus.BAD_REQUEST);
    }
}
