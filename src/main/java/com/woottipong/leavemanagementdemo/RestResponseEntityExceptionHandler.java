package com.woottipong.leavemanagementdemo;

import com.woottipong.leavemanagementdemo.exception.DataNotFoundException;
import com.woottipong.leavemanagementdemo.exception.LoginException;
import com.woottipong.leavemanagementdemo.exception.UserInputException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.TreeMap;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { DataNotFoundException.class })
    protected ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
        return handleBasic(ex, request, 404);
    }

    @ExceptionHandler(value = { UserInputException.class })
    protected ResponseEntity<Object> handleUserInputException(UserInputException ex, WebRequest request) {
        return handleBasic(ex, request, 403);
    }

    @ExceptionHandler(value = { LoginException.class })
    protected ResponseEntity<Object> handleLoginException(LoginException ex, WebRequest request) {
        return handleBasic(ex, request, 401);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        return handleBasic(ex, request, 500);
    }

    private ResponseEntity<Object> handleBasic(Exception ex, WebRequest request, int code) {
        ex.printStackTrace();
        Map<String, Object> map = new TreeMap<>();
        map.put("status", HttpStatus.valueOf(code).name());
        map.put("message", ex.getMessage());
        return handleExceptionInternal(ex, map, new HttpHeaders(), HttpStatus.valueOf(code), request);
    }
}
