package com.khanghn.careerspark_api.exception.black;

import com.khanghn.careerspark_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message) {
        super("401", message, HttpStatus.UNAUTHORIZED);
    }
}
