package com.droppler.notification.exception;

import com.droppler.notification.base.ApiResponse.ApiResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestException(Exception exception){
        var apiResponse =
                ApiResponseBuilder.builder()
                .setMessage(exception.getMessage())
                .setStatusCode(400)
                .setSuccess(false)
                .build();
        return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception exception){
        var apiResponse =
                ApiResponseBuilder.builder()
                        .setMessage(exception.getMessage())
                        .setStatusCode(500)
                        .setSuccess(false)
                        .build();
        return new ResponseEntity(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
