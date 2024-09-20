package com.myonlineshopping.config;

import com.myonlineshopping.exceptions.AccountNotfoundException;
import com.myonlineshopping.exceptions.CustomerNotfoundException;
import com.myonlineshopping.exceptions.ExceptionMessage;
import com.myonlineshopping.exceptions.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionMessage> globalExceptionHandler(GlobalException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(e.getId(), e.getMessage()));
    }

    @ExceptionHandler(AccountNotfoundException.class)
    public ResponseEntity<ExceptionMessage> accountNotfoundExceptionHandler(AccountNotfoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(e.getId(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(CustomerNotfoundException.class)
    public ResponseEntity<ExceptionMessage> customerNotfoundExceptionHandler(AccountNotfoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(e.getId(), e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not valid due to validation error: " + e.getMessage());
    }



}
