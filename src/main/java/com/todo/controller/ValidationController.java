package com.todo.controller;

import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ValidationController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericRestResponse<String> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        
        GenericRestResponse<String> errorResponse = new GenericRestResponse<String>("failed");
        for(FieldError error:fieldErrors) {
        	String field = StringUtils.capitalize(error.getField());
        	String message = StringUtils.capitalize(error.getDefaultMessage());
        	errorResponse.addErrorMessage(field + " " + message);
        }
        return errorResponse;
    }
}
