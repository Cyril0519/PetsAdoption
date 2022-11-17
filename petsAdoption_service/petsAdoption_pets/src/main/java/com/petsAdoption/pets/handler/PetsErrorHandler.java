package com.petsAdoption.pets.handler;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.petsAdoption.pets.controller")
public class PetsErrorHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result errorhandler(RuntimeException e) {
        e.printStackTrace();
        return new Result(false, ResultCode.ERROR_OCCUR, e.getMessage());
    }
}
