package com.khanghn.careerspark_api.exception.handler;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.exception.black.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseObject<Void> handleGeneralException(Exception ex) {
        return ResponseObject.error(
                "INTERNAL_SERVER_ERROR",
                ex.getMessage()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseObject<Void> handleNotFound(EntityNotFoundException ex) {
        return ResponseObject.error(
                "ENTITY_NOT_FOUND",
                ex.getMessage()
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseObject<Void> handleUnauthorized(UnauthorizedException ex) {
        return ResponseObject.error(
                "AUTH_UNAUTHORIZED",
                ex.getMessage()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject<Void> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseObject.error(
                "DATA_INTEGRITY_VIOLATION",
                ex.getMessage()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject<Void> handleBadRequest(BadRequestException ex) {
        return ResponseObject.error(
                "BAD_REQUEST",
                ex.getMessage()
        );
    }
}
