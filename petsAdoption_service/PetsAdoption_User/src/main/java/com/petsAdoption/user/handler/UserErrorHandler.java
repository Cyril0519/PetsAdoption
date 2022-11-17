package com.petsAdoption.user.handler;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.petsAdoption.user.controller")
public class UserErrorHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> errorhandler(RuntimeException e) {
        e.printStackTrace();
        String message = e.getMessage();
        return new Result<>(false,ResultCode.ERROR_OCCUR, message);
    }
}
