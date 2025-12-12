package com.khanghn.careerspark_api.exception.handler;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.exception.black.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
    public ResponseObject<ExceptionDTO> handleGeneralException(Exception ex) {
        return ResponseObject.error(new ExceptionDTO("500", ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseObject<ExceptionDTO> handleNotFound(EntityNotFoundException ex) {
        return ResponseObject.error(new ExceptionDTO("404", ex.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseObject<ExceptionDTO> handleJwtExpired(ExpiredJwtException ex) {
        return ResponseObject.error(new ExceptionDTO("401", ex.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseObject<ExceptionDTO> handleJwtException(JwtException ex) {
        return ResponseObject.error(new ExceptionDTO("401", ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseObject<ExceptionDTO> handleUnauthorized(UnauthorizedException ex) {
        return ResponseObject.error(new ExceptionDTO("401", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject<ExceptionDTO> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseObject.error(new ExceptionDTO("400", ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject<ExceptionDTO> handleBadRequest(BadRequestException ex) {
        return ResponseObject.error(new ExceptionDTO("400", ex.getMessage()));
    }
}
