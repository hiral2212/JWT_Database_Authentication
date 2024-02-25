package com.jwtauthentication.jwt.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.jwtauthentication.jwt.dto.ResponseDto;
import com.jwtauthentication.jwt.exception.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto EntityNotFoundExceptionHandler(EntityNotFoundException exception)
    {
        return new ResponseDto(404,"error", exception);
    }
}
